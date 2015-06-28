package od.andrey.morpher.matchers

import od.andrey.morpher.dictionary.Dictionary

/**
 * Created by andrey on 27.06.2015.
 */
class WordMatcher(dictionary: Dictionary) extends Matcher {
  override def apply(token: String): Option[MatchingResult] = {
    Option.empty
  }
}
