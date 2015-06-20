package od.andrey.morpher.dictionary

/**
 * Created by andrey on 17.06.2015.
 */
class Word(word: String, lemma: Lemma, flexion: Flexion) {
  lazy val posTag = lemma.posTag
  lazy val ending = flexion.ending
  lazy val attrs = flexion.attrs
  lazy val prefix = flexion.prefix

  override def toString: String =
    "%s, %s %s".format(word, posTag, attrs)
}
