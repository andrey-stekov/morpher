package od.andrey.morpher.matchers

import od.andrey.morpher.dictionary.{Flexion, Lemma}

/**
 * Created by andrey on 27.06.2015.
 */
abstract class MatchingResult(word: String, matcher: Matcher) {
  def normalForm = word
}

class Word(word: String, matcher: Matcher, lemma: Lemma, flexion: Flexion)
  extends MatchingResult(word, matcher) {
  override def normalForm = null//ToDo
}

object NumberNotation extends Enumeration {
  class NumberNotationValue extends Val
  val Male, Female, Middle = new NumberNotationValue
}

class Number(word: String, matcher: Matcher, notation: NumberNotation.NumberNotationValue, value: Double)
  extends MatchingResult(word, matcher)

class Unknown(word: String, matcher: Matcher) extends MatchingResult(word, matcher)