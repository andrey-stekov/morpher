package od.andrey.morpher.dictionary

import od.andrey.morpher.dictionary.attributes.PartOfSpeech.PartOfSpeechAttribute
import od.andrey.morpher.dictionary.attributes.Attribute

@SerialVersionUID(1L)
class Lemma (val base: String,
             val flexions: List[Flexion],
             val posTag: PartOfSpeechAttribute) extends Serializable {
  def this(base: String, flexions: List[Flexion]) = {
    this(base,
      flexions,
      flexions
        .head
        .attrs
        .filter(_.isInstanceOf[PartOfSpeechAttribute])
        .head match {
          case attr: PartOfSpeechAttribute => attr})
  }

  def word(f: Flexion): String = f.prefix + base + f.ending
  lazy val word: String = word(flexions.head)

  def findForm(tags: Attribute*) = {
    flexions.filter((f: Flexion) => tags.forall(f.attrs.contains))
            .foldLeft(Set[String]())((result, f) => result + (base + f.ending))
  }

  override def toString: String = {
    "Lemma {base = %s, posTag = %s, flexions = %s}".format(base, posTag, flexions)
  }
}
