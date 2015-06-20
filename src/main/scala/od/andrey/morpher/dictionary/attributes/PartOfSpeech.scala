package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
object PartOfSpeech extends Enumeration {
  class PartOfSpeechAttribute extends Val with Attribute
  val Noun, Adjective, Numeral, Pronoun, Verb,
      Adverb, Preposition, Conjunction, Interjection,
      Participle, Unknown = new PartOfSpeechAttribute
}
