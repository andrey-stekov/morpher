import od.andrey.morpher.compillers.aot.AOTDictionaryCompiler
import od.andrey.morpher.matchers.{PrefixUtils, DictionaryMatcher}
import od.andrey.morpher.RuMorpher
import od.andrey.tokenizer.Tokenizer
import scala.io.Source

/**
 * Created by andrey on 14.06.2015.
 */
object Test1 {
  def main(args: Array[String]) {
    println(PrefixUtils.decompose("наипредопределенно"))
    println(PrefixUtils.compose(List("наи", "пре", "до", "пре"), "деленно"))

    val tokens1 = Tokenizer.split(
                    Thread
                      .currentThread()
                      .getContextClassLoader
                      .getResourceAsStream("lyrics1.txt"))
    println(tokens1)

    val tokens2 = Tokenizer.splitLine("\"Англо - саксы\"")

    val res1 = RuMorpher.all("наипредопределено")
    println(res1)
    println(res1.head.normalForm)

    val res2 = RuMorpher.all("травку")
    println(res2)
    println(res2.head.normalForm)
  }
}
