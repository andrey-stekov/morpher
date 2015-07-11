package od.andrey.morpher.matchers

import od.andrey.morpher.dictionary.{EndsInfo, Lemma, Dictionary}

/**
 * Created by andrey on 11.07.2015.
 */
class SuggestsDictionaryMatcher(dictionary: Dictionary) extends Matcher {
  override def apply(token: String): Set[MatchingResult] = {
    val lower = dictionary.fixChars(token.toLowerCase)
    val endings = dictionary.postfixInfoTrie.findVariants(lower.reverse)
    endings.map((entry: Tuple2[String, EndsInfo]) => (entry._1.length, entry._2))
      .toList
      .sortBy(_._1)
      .last match {
        case (affixLength, info) =>
          val base = lower.substring(0, lower.length - affixLength)
          info(base) match {
            case Some(flexNumb: Int) => {
              val lemma = new Lemma(base, dictionary.allFlexions(flexNumb))
              lemma.flexions.find(lemma.word(_) == lower) match {
                case Some(flexion) => Set(new Word(lower, this, lemma, flexion))
                case None => Set.empty
              }
            }
            case None => Set.empty
          }
        case _ => Set.empty
    }
  }
}
