import od.andrey.morpher.compillers.aot.AOTDictionaryCompiler
import od.andrey.morpher.matchers.{PrefixUtils, DictionaryMatcher}
import od.andrey.morpher.RuMorpher

/**
 * Created by andrey on 14.06.2015.
 */
object Test1 {
  def main(args: Array[String]) {
    println(PrefixUtils.decompose("наипредопределенно"))
    println(PrefixUtils.compose(List("наи", "пре", "до", "пре"), "деленно"))

    val res = RuMorpher.all("наипредопределенное")
    println(res)
  }
}
