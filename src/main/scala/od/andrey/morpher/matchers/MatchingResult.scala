package od.andrey.morpher.matchers

import od.andrey.morpher.dictionary.{Flexion, Lemma}
import od.andrey.morpher.dictionary.attributes.{Gender, RuCase, Number}

/**
 * Created by andrey on 27.06.2015.
 */
abstract class MatchingResult(val word: String, val matcher: Matcher) {
  def normalForm = word

  override def toString = word + ", " + matcher.getClass.getSimpleName
}

class Word(word: String, matcher: Matcher, val lemma: Lemma, val flexion: Flexion)
  extends MatchingResult(word, matcher) {
  override def normalForm = lemma.findClose(RuCase.Nom, Number.Singular, Gender.Male) match {
    case Some(form) => form
    case None => lemma.word(flexion)
  }
}

class ComplexWord(word: String, matcher: Matcher, lemma: Lemma,
                  flexion: Flexion, val additionalPrefixes: List[String])
  extends Word(word, matcher, lemma, flexion) {
  override def normalForm = PrefixUtils.compose(additionalPrefixes, super.normalForm)
}

object NumberNotation extends Enumeration {
  class NumberNotationValue extends Val
  val Arabic = new NumberNotationValue
}

class Number(word: String, matcher: Matcher, notation: NumberNotation.NumberNotationValue, value: Double)
  extends MatchingResult(word, matcher)

class Unknown(word: String, matcher: Matcher) extends MatchingResult(word, matcher)