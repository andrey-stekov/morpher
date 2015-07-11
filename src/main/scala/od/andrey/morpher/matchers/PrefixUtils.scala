package od.andrey.morpher.matchers

import scala.io.Source
import od.andrey.morpher.common.Trie

/**
 * Created by andrey on 11.07.2015.
 */
object PrefixUtils {
  val plainPrefixes = Source.fromInputStream(
    Thread.currentThread().getContextClassLoader().getResourceAsStream("prefixes"))
    .getLines()
    .map(_.trim)
    .filter((l) => !l.startsWith("//") && l.nonEmpty)
    .map(_.split(","))
    .foldLeft(new Trie[Boolean])((trie, list) => {
        list.foreach((prefix) => trie += (prefix.trim, true))
        trie
      })

  private def decomposeRec(prefixes: List[String], word: String): Tuple2[List[String], String] = {
    plainPrefixes.findVariants(word).toList.map(_._1).sortBy(_.length).reverse match {
      case List() => (prefixes, word)
      case prefix::rest => decomposeRec(prefixes :+ prefix, word.substring(prefix.length))
    }
  }

  def decompose(word: String): Tuple2[List[String], String] = decomposeRec(List.empty, word.toLowerCase)

  def compose(prefixes: List[String], base: String): String = {
    prefixes.reduce(_+_) + base
  }
}
