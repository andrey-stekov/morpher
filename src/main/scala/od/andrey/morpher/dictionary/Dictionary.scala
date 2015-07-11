package od.andrey.morpher.dictionary

import scala.collection.mutable
import od.andrey.morpher.common.Trie

/**
 * Created by andrey on 17.06.2015.
 */
@SerialVersionUID(1L)
class Dictionary(val allFlexions: mutable.MutableList[List[Flexion]],
                 val lemmas: mutable.MutableList[Lemma],
                 val postfixTree: Trie[List[Int]],
                 val prefixTree: Trie[List[Int]],
                 val postfixInfoTrie: Trie[EndsInfo],
                 val fixChars: String => String) extends Serializable
