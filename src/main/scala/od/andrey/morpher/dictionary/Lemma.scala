package od.andrey.morpher.dictionary

import java.lang.IllegalArgumentException

/**
 * Created by andrey on 12.06.2015.
 */
@SerialVersionUID(1L)
case class Flexion (ending: String,
                    ancode: String,
                    prefix: String) extends Serializable

object PartOfSpeech extends Enumeration {
  val Noun, Adjective, Numeral, Pronoun, Verb,
      Adverb, Preposition, Conjunction, Interjection,
      Participle, Unknown = Value
}

@SerialVersionUID(1L)
class Lemma (val base: String,
             val flexions: List[Flexion],
             val posTag: PartOfSpeech.Value) extends Serializable {

  def this(base: String, flexions: List[Flexion]) = {
    this(base, flexions,
         flexions.head.ancode.split(" ", 3)(1) match {
           case "Г" => PartOfSpeech.Verb
           case "Н" => PartOfSpeech.Adverb
           case "П" => PartOfSpeech.Adjective
           case "С" => PartOfSpeech.Noun
           case "МС" => PartOfSpeech.Pronoun
           case "МС-П" => PartOfSpeech.Pronoun
           case "МС-ПРЕДК" => PartOfSpeech.Pronoun
           case "КР_ПРИЛ" => PartOfSpeech.Adjective
           case "КР_ПРИЧАСТИЕ" => PartOfSpeech.Participle
           case "МЕЖД" => PartOfSpeech.Interjection
           case "СОЮЗ" => PartOfSpeech.Preposition
           case "ФРАЗ" => PartOfSpeech.Preposition
           case "ЧАСТ" => PartOfSpeech.Preposition
           case "ЧИСЛ" => PartOfSpeech.Numeral
           case "ЧИСЛ-П" => PartOfSpeech.Numeral
           case "ВВОДН" => PartOfSpeech.Preposition
           case "ПРЕДК" => PartOfSpeech.Preposition
           case "ПРЕДЛ" => PartOfSpeech.Preposition
           case "ИНФИНИТИВ" => PartOfSpeech.Verb
           case "ПРИЧАСТИЕ" => PartOfSpeech.Participle
           case "ДЕЕПРИЧАСТИЕ" => PartOfSpeech.Participle
           case e: String => throw new IllegalArgumentException("Illegal part of speech tag: %s".format(e))
         })
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
