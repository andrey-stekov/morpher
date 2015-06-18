package od.andrey.morpher.dictionary

/**
 * Created by andrey on 18.06.2015.
 */
object PartOfSpeech extends Enumeration {
  val Noun, Adjective, Numeral, Pronoun, Verb,
      Adverb, Preposition, Conjunction, Interjection,
      Participle, Unknown = Value

  def valueOf(s: String) = s match {
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
  }
}
