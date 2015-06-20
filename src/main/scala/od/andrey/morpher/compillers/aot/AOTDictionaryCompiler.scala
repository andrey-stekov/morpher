package od.andrey.morpher.compillers.aot

import scala.io.Source
import java.io.{InputStream, InputStreamReader, BufferedReader}
import scala.collection.mutable
import java.util.concurrent.atomic.AtomicInteger
import od.andrey.morpher.common.{Utils, Trie}
import od.andrey.morpher.dictionary.{Dictionary, Flexion, Lemma, EndsInfo}
import od.andrey.morpher.compillers.DictionaryCompiler

/**
 * Created by andrey on 13.06.2015.
 */

class AOTDictionaryCompiler(val tabStream: InputStream,
                            val mrdStream: InputStream) extends DictionaryCompiler {
  val BATCH_SIZE = 1000

  def this() = this(Thread.currentThread().getContextClassLoader().getResourceAsStream("tab"),
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("mrd"))

  def compile(): Dictionary = {
    println("Start dictionary initializing")

    val tabDescriptors = Source.fromInputStream(tabStream)
      .getLines()
      .filter((l) => !l.startsWith("//") && l.trim.nonEmpty)
      .map((l) => l.split(" ", 2))
      .foldLeft(Map[String, String]())((m, t) => {
        m + ((t(0), t(1)))
      })
    println("Tab section read")

    val mrdReader = new BufferedReader(new InputStreamReader(mrdStream))
    val allFlexions = readSection(mrdReader, (l) => {
      l.split("%").map((item) => {
        item.split("\\*").toList match {
          case List(affix, ancode) => buildFlexion(tabDescriptors, affix, ancode, "")
          case List(affix, ancode, prefix) => buildFlexion(tabDescriptors, affix, ancode, prefix)
          case List(s) => if (s.nonEmpty) throw new IllegalStateException("Illegal MRD item: \"%s\"".format(item)) else null
        }
      }).filter(_ != null).toList
    }, false)
    println("Flexions section read")

    skipSection(mrdReader) // accentual models
    skipSection(mrdReader) // user sessions
    skipSection(mrdReader) // prefix sets
    println("Skiped section read")

    val lemmas = new mutable.MutableList[Lemma]
    val postfixTree: Trie[List[Int]] = new Trie[List[Int]]
    val prefixTree: Trie[List[Int]] = new Trie[List[Int]]
    val postfixInfoTree: Trie[EndsInfo] = new Trie[EndsInfo]
    val refId: AtomicInteger = new AtomicInteger

    readSection(mrdReader, (l) => {
      l.split(" ").toList match {
        case base::flexNumb::_ =>
          val fixedBase = fixChars(base.toLowerCase)
          val flexions = allFlexions(flexNumb.toInt)
          val id = refId.getAndIncrement

          addToTree(prefixTree, fixedBase, id)

          val lemma = new Lemma(fixedBase, flexions)
          lemmas += lemma
          assert(lemmas.size == id + 1)

          val visitedAffix = new mutable.TreeSet[String]
          val visitedPrefixes = new mutable.TreeSet[String]

          flexions.foreach((f) => {
            val affix = f.ending.reverse
            if (!visitedAffix.contains(affix)) {
              visitedAffix += affix
              addToTree(postfixTree, affix, id)
              addToTree(postfixInfoTree, base, affix, flexNumb.toInt)
            }
            if (f.prefix.nonEmpty && !visitedPrefixes.contains(f.prefix)) {
              visitedPrefixes += f.prefix
              addToTree(prefixTree, f.prefix + fixedBase, id)
            }
            true
          })
      }
    }, true)
    println("Lemmas section read")

    new Dictionary(allFlexions,
                   lemmas,
                   postfixTree,
                   prefixTree,
                   postfixInfoTree,
                   Utils.fixRussianChars)
  }

  private def addToTree(trie: Trie[List[Int]], word: String, id: Int) = {
    val list = trie(word) match {
      case Some(list: List[Int]) => list
      case None => List[Int]()
      case null => List[Int]()
    }
    trie += (word, id::list)
  }

  private def addToTree(trie: Trie[EndsInfo], base: String, affix: String, flexNumb: Int) = {
    val info = trie(affix) match {
      case Some(info: EndsInfo) => info
      case None => new EndsInfo
      case null => new EndsInfo
    }
    info += (base, flexNumb)
    trie += (affix, info)
  }

  private def readSection[O](reader: BufferedReader, mapper: String => O, logging: Boolean): mutable.MutableList[O] = {
    val result = new mutable.MutableList[O]
    val number = reader.readLine().toInt

    if (logging) println("Section size: %d".format(number))

    for (i <- 1 to number) {
      val line = reader.readLine()
      result += mapper(line)

      if (logging && i % BATCH_SIZE == 0) println("Processed %d lines".format(i))
    }
    result
  }

  private def skipSection(reader: BufferedReader) = {
    val number = reader.readLine().toInt
    for (i <- 1 to number) {
      reader.readLine()
    }
  }

  private def fixChars(s: String): String = {
    Utils.fixRussianChars(s).replace("#", "")
  }

  private def buildFlexion(tabDescriptors: Map[String, String], affix: String, ancode: String, prefix: String) = {
    new Flexion(
      fixChars(affix.toLowerCase()),
      tabDescriptors(ancode.substring(0, 2)),
      fixChars(prefix))
  }
}