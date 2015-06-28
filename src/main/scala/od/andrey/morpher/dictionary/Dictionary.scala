package od.andrey.morpher.dictionary

import scala.collection.mutable
import od.andrey.morpher.common.{Utils, Trie}

/**
 * Created by andrey on 17.06.2015.
 */
@SerialVersionUID(1L)
class Dictionary(val allFlexions: mutable.MutableList[List[Flexion]],
                 val lemmas: mutable.MutableList[Lemma],
                 val postfixTree: Trie[List[Int]],
                 val prefixTree: Trie[List[Int]],
                 val postfixInfoTrie: Trie[EndsInfo],
                 val plainPrefixes: Trie[Boolean],
                 val fixChars: String => String) extends Serializable {

//  protected def decomposition(word: String): Tuple2[Set[String], String] = {
//
//  }

  def lookup(word: String): Set[Lemma] = {
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

  def suggests(word:String): Set[Lemma] = {
    val lower = fixChars(word.toLowerCase)
    val endings = postfixInfoTrie.findVariants(lower.reverse)
    endings.map((entry: Tuple2[String, EndsInfo]) => (entry._1.length, entry._2))
      .toList
      .sortBy(_._1)
      .last match {
      case (affixLength, info) =>
        val base = lower.substring(0, lower.length - affixLength)
        info(base) match {
          case Some(flexNumb: Int) => Set(new Lemma(base, allFlexions(flexNumb)))
          case None => Set.empty
        }
    }
  }

  def lookupOrSuggests(word:String): Set[Lemma] = {
    val set = lookup(word)
    if (set.nonEmpty) { set } else { suggests(word) }
  }

  def wordInfo(word: String) = {
    lookupOrSuggests(word).map((lemma) => {
      val list = lemma.flexions
                      .foldLeft(List[Tuple2[Int, Flexion]]())((list, flexion) =>
                          (Utils.levenshteinDistance(word, lemma.word(flexion)), flexion) :: list)
                      .sortBy(_._1)
      new Word(word, lemma, list.head._2)
    })
  }
}
