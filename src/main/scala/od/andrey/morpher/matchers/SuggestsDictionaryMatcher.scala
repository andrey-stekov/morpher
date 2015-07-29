package od.andrey.morpher.matchers

import od.andrey.morpher.dictionary.{EndsInfo, Lemma, Dictionary}

/**
 * Created by andrey on 11.07.2015.
 */
class SuggestsDictionaryMatcher(dictionary: Dictionary) extends Matcher {
  def base(lower: String, affixLength: Int): String = lower.substring(0, lower.length - affixLength)

  override def apply(token: String): Set[MatchingResult] = {
    val lower = dictionary.fixChars(token.toLowerCase)
    val endings = dictionary.postfixInfoTrie.findVariants(lower.reverse)
    endings.map((entry: Tuple2[String, EndsInfo]) => (entry._1.length, entry._2))
      .toList
      .sortBy(_._1)
      .reverse
      .find {
        case (affixLength: Int, info: EndsInfo) =>
          info(base(lower, affixLength)).nonEmpty
      } match {
        case Some((affixLength: Int, info: EndsInfo)) =>
          val baseStr = base(lower, affixLength)
          info(baseStr) match {
            case Some(flexNumb: Int) =>
              val lemma = new Lemma(baseStr, dictionary.allFlexions(flexNumb))
              lemma.flexions.find(lemma.word(_) == lower) match {
                case Some(flexion) => Set(new Word(lower, this, lemma, flexion))
                case None => Set.empty
              }
            case None => Set.empty
          }
        case _ => Set.empty
      }
  }
}
