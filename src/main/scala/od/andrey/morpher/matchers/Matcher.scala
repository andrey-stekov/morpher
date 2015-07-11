package od.andrey.morpher.matchers

/**
 * Created by andrey on 27.06.2015.
 */
trait Matcher {
  def apply(token: String): Set[MatchingResult]
}
