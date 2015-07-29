package od.andrey.tokenizer

import scala.collection.mutable
import java.io.InputStream
import scala.io.Source
import od.andrey.morpher.matchers.DictionaryMatcher
import od.andrey.morpher.RuMorpher

/**
 * Created by andrey on 12.07.2015.
 */
object Tokenizer {
  private val dictionaryMatcher = new DictionaryMatcher(RuMorpher.aotDictionary)

  def split(is: InputStream): List[Token] = Source
    .fromInputStream(is)
    .getLines()
    .map[List[Token]]((s: String) => splitLine(s))
    .reduce(_:::_)

  def splitLine(s: String): List[Token] = {
    postProcess(split(s))
  }

  private def split(s: String): List[Token] = {
    var temp = mutable.MutableList[Token]()
    var accumulator = mutable.MutableList[Char]()
    var prevType: TokenType.TokenTypeVal = null

    for (ch <- s.trim.toCharArray) {
      if (ch.isWhitespace) {
        if (accumulator.nonEmpty) {
          temp += Token(accumulator.mkString, prevType)
          accumulator.clear()
          prevType = null
        }
      } else {
        val currentType = if (ch.isDigit) {
          TokenType.Number
        } else if (isRuLetter(ch)) {
          TokenType.RuWord
        } else {
          TokenType.Unknown
        }

        if (prevType != null) {
          if (prevType != currentType) {
            temp += Token(accumulator.mkString, prevType)
            accumulator.clear()
          }
        }

        prevType = currentType
        accumulator += ch
      }
    }

    if (accumulator.nonEmpty) {
      temp += Token(accumulator.mkString, prevType)
    }

    temp.toList
  }

  val ruLetters = Set('ё', 'й', 'ф', 'я', 'ц', 'ы',
                      'ч', 'у', 'в', 'с', 'к', 'а',
                      'м', 'е', 'п', 'и', 'н', 'р',
                      'т', 'г', 'о', 'ь', 'ш', 'л',
                      'б', 'щ', 'д', 'ю', 'з', 'ж',
                      'х', 'э', 'ъ')

  def isRuLetter(char: Char) = ruLetters.contains(char.toLower)

  private def postProcess(tokens: List[Token]): List[Token] = postProcess(List.empty, tokens)

  private def postProcess(accumulator: List[Token],
                          tokens: List[Token]): List[Token] = {
    tokens match {
      case Token(a, TokenType.RuWord)::Token("-", TokenType.Unknown)::Token(b, TokenType.RuWord)::rest => {
        val merged = a + "-" + b

        if (dictionaryMatcher(merged).nonEmpty) {
          postProcess(accumulator:+Token(merged, TokenType.RuWord), rest)
        } else {
          postProcess(
            accumulator:+Token(a, TokenType.RuWord):+Token("-", TokenType.Unknown):+Token(b, TokenType.RuWord),
            rest)
        }
      }
      case a::rest => postProcess(accumulator :+ a, rest)
      case a::Nil => accumulator :+ a
      case Nil => accumulator
    }
  }
}
