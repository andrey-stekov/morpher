package od.andrey.morpher.dictionary

@SerialVersionUID(1L)
class Lemma (val base: String,
             val flexions: List[Flexion],
             val posTag: PartOfSpeech.Value) extends Serializable {

  def this(base: String, flexions: List[Flexion]) = {
    this(base, flexions,
         PartOfSpeech.valueOf(flexions.head.ancode.split(" ", 3)(1)))
  }

  def word(f: Flexion): String = f.prefix + base + f.ending
  lazy val word: String = word(flexions.head)

  def findForm(tags: String*) = {
    flexions.filter((f: Flexion) => tags.forall(f.ancode.contains(_)))
            .foldLeft(Set[String]())((result, f) => result + (base + f.ending))
  }

  override def toString: String = {
    "Lemma {base = %s, posTag = %s, flexions = %s}".format(base, posTag, flexions)
  }
}
