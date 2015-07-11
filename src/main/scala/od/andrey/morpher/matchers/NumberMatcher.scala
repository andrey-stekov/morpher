package od.andrey.morpher.matchers

/**
 * Created by andrey on 11.07.2015.
 */
class NumberMatcher extends Matcher {
  override def apply(token: String): Set[MatchingResult] = {
    val arabicRex = """(\d)+(.\d+)?""".r
    token match {
      case arabicRex(intPart, floatPart) => Set(new Number(token, this, NumberNotation.Arabic, token.toDouble))
      case _ => Set.empty
    }
  }
}
