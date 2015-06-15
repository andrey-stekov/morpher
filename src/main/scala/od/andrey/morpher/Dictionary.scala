package od.andrey.morpher

import scala.io.Source
import java.io.{InputStreamReader, BufferedReader}
import scala.collection.mutable
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by andrey on 13.06.2015.
 */

class Dictionary {
  val tabDescriptors = Source.fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("tab"))
                      .getLines()
                      .filter((l) => !l.startsWith("//") && l.trim.nonEmpty)
                      .map((l) => l.split(" ", 2))
                      .foldLeft(Map[String, String]())((m, t) => {
                          m + ((t(0), t(1)))
                        })
  val mrdReader = new BufferedReader(
                    new InputStreamReader(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream("mrd")))
  val allFlexions = readSection(mrdReader, (l) => {
    l.split("%").map((item) => {
      item.split("\\*").toList match {
        case List(affix, ancode) => buildFlexion(affix, ancode, "")
        case List(affix, ancode, prefix) => buildFlexion(affix, ancode, prefix)
        case List(s) => if (s.nonEmpty) throw new IllegalStateException("Illegal MRD item: \"%s\"".format(item)) else null
      }
    }).filter(_ != null).toList
  })

  skipSection(mrdReader) // accentual models
  skipSection(mrdReader) // user sessions
  skipSection(mrdReader) // prefix sets

  val lemmas = new mutable.MutableList[Lemma]
  val postfixTree: Trie[List[Int]] = new Trie[List[Int]]
  val prefixTree: Trie[List[Int]] = new Trie[List[Int]]
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
          }
          if (f.prefix.nonEmpty && !visitedPrefixes.contains(f.prefix)) {
            visitedPrefixes += f.prefix
            addToTree(prefixTree, f.prefix + fixedBase, id)
          }
          true
        })
    }
  })

  private def addToTree(trie: Trie[List[Int]], word: String, id: Int) = {
    val list = trie(word) match {
      case Some(list: List[Int]) => list
      case None => List[Int]()
      case null => List[Int]()
    }
    trie += (word, id::list)
  }

  private def readSection[O](reader: BufferedReader, mapper: String => O): mutable.MutableList[O] = {
    val result = new mutable.MutableList[O]
    val number = reader.readLine().toInt
    for (i <- 1 to number) {
      val line = reader.readLine()
      result += mapper(line)
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
    s.replace("ё", "е").replace("#", "")
  }

  private def buildFlexion(affix: String, ancode: String, prefix: String) = {
    new Flexion(
      fixChars(affix.toLowerCase()),
      tabDescriptors(ancode.substring(0, 2)),
      fixChars(prefix))
  }

  def lookupWord(word: String) = {
    val lower = fixChars(word.toLowerCase)
    val prefixLookup = prefixTree.findVariants(lower)
    val postfixLookup = postfixTree.findVariants(lower.reverse)

    prefixLookup.foldLeft(Set[Lemma]())((set, entry) => {
      val affix = lower.substring(entry._1.length).reverse
      if (postfixLookup.contains(affix)) {
        (entry._2 intersect postfixLookup(affix))
          .foldLeft(set)((set, id) => set + lemmas(id))
      } else {
        set
      }
    })
  }

  def getByEnding(word:String) ={
    postfixTree.findVariants(fixChars(word.toLowerCase).reverse)
  }
}