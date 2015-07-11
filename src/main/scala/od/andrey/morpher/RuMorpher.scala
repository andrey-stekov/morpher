package od.andrey.morpher

import od.andrey.morpher.compillers.aot.AOTDictionaryCompiler
import od.andrey.morpher.matchers._

/**
 * Created by andrey on 27.06.2015.
 */
object RuMorpher extends Matcher {
  val aotDictionary = new AOTDictionaryCompiler().compile()
  val matchers = List[Matcher](new DictionaryMatcher(aotDictionary),
                               new SuggestsDictionaryMatcher(aotDictionary),
                               new ComplexDictionaryMatcher(aotDictionary),
                               new NumberMatcher)

  def apply(token: String) = {
    val res = matchers.foldLeft[Set[MatchingResult]](Set.empty)((s, matcher) =>
      if (s.isEmpty) s ++ matcher(token) else s)

    if (res.isEmpty) Set(new Unknown(token, this)) else res
  }

  def all(token: String) = {
    val res = matchers.foldLeft[Set[MatchingResult]](Set.empty)((s, matcher) => s ++ matcher(token))

    if (res.isEmpty) Set(new Unknown(token, this)) else res
  }
}
