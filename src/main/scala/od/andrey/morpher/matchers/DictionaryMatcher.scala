package od.andrey.morpher.matchers

import od.andrey.morpher.dictionary.{Flexion, Lemma, Dictionary}
import od.andrey.morpher.common.Utils

/**
 * Created by andrey on 27.06.2015.
 */
class DictionaryMatcher(dictionary: Dictionary) extends Matcher {
  protected def findForm(word:String, lemma: Lemma) = lemma.flexions.find((flexion) => {
    word == lemma.word(flexion)
  })

  override def apply(token: String): Set[MatchingResult] = {
    val lower = dictionary.fixChars(token.toLowerCase)
    val prefixLookup = dictionary.prefixTree.findVariants(lower)
    val postfixLookup = dictionary.postfixTree.findVariants(lower.reverse)

    prefixLookup
      .foldLeft(Set[Lemma]())((set, entry) => {
        val affix = lower.substring(entry._1.length).reverse
        if (postfixLookup.contains(affix)) {
          (entry._2 intersect postfixLookup(affix))
            .foldLeft(set)((set, id) => set + dictionary.lemmas(id))
        } else {
          set
        }
      })
      .map((lemma) => findForm(lower, lemma) match {
        case Some(flexion) => new Word(lower, this, lemma, flexion)
        case None => null
      })
      .filter(_ != null)
      .map {
          case w: MatchingResult => w
      }
  }
}
