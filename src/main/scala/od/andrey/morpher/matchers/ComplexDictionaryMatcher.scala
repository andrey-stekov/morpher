package od.andrey.morpher.matchers

import od.andrey.morpher.dictionary.Dictionary

/**
 * Created by andrey on 11.07.2015.
 */
class ComplexDictionaryMatcher(dictionary: Dictionary) extends DictionaryMatcher(dictionary) {
  override def apply(token: String): Set[MatchingResult] = {
    val word = dictionary.fixChars(token.toLowerCase)
    val (prefixes, base) = PrefixUtils.decompose(word)
    val matchResult = super.apply(base)
    matchResult.map {
      case w: Word => new ComplexWord(word, this, w.lemma, w.flexion, prefixes)
    }
  }
}
