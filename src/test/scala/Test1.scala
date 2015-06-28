import od.andrey.morpher.compillers.aot.AOTDictionaryCompiler

/**
 * Created by andrey on 14.06.2015.
 */
object Test1 {
  def main(args: Array[String]) {
    val dictionary = (new AOTDictionaryCompiler).compile

    dictionary.wordInfo("кудлачит").foreach(println)

    dictionary.lookupOrSuggests("суки").foreach((lemma) => {
      lemma.flexions.foreach((flexion) => println(lemma.word(flexion) + ", " + flexion.attrs))
    })
//    dictionary.lookupOrSuggests("бутявка").foreach((lemma) => {
//      lemma.flexions.foreach((flexion) => println(lemma.word(flexion)))
//    })
//    dictionary.lookupOrSuggests("123").foreach((lemma) => {
//      lemma.flexions.foreach((flexion) => println(lemma.word(flexion)))
//    })
  }
}
