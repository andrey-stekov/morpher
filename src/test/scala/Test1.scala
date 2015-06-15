import od.andrey.morpher.{Lemma, Dictionary}

/**
 * Created by andrey on 14.06.2015.
 */
object Test1 {
  def main(args: Array[String]) {
    val dictionary = new Dictionary
    dictionary.lookupWord("бутявка").foreach((lemma) => {
      lemma.flexions.foreach((flexion) => println(lemma.word(flexion)))
    })
    val l = dictionary.getByEnding("бутявка")
    val l1 = new Lemma("бутяв", dictionary.lemmas(l.toList.tail.head._2.head).flexions, dictionary.lemmas(l.toList.tail.head._2.head).posTag)
    l1.flexions.foreach((flexion) => println(l1.word(flexion)))
  }
}
