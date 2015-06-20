package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{PartOfSpeech, Attribute, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object PartOfSpeechAttributeBuilder extends AttributeBuilder {
  val verbSet = Set[Attribute](PartOfSpeech.Verb)
  val adverbSet = Set[Attribute](PartOfSpeech.Adverb)
  val adjectiveSet = Set[Attribute](PartOfSpeech.Adjective)
  val nounSet = Set[Attribute](PartOfSpeech.Noun)
  val pronounSet = Set[Attribute](PartOfSpeech.Pronoun)
  val participleSet = Set[Attribute](PartOfSpeech.Participle)
  val interjectionSet = Set[Attribute](PartOfSpeech.Interjection)
  val prepositionSet = Set[Attribute](PartOfSpeech.Preposition)
  val numeralSet = Set[Attribute](PartOfSpeech.Numeral)

  override def build(s: String): Set[Attribute] = s match {
    case "Г" => verbSet
    case "Н" => adverbSet
    case "П" => adjectiveSet
    case "С" => nounSet
    case "МС" => pronounSet
    case "МС-П" => pronounSet
    case "МС-ПРЕДК" => pronounSet
    case "КР_ПРИЛ" => adjectiveSet
    case "КР_ПРИЧАСТИЕ" => participleSet
    case "МЕЖД" => interjectionSet
    case "СОЮЗ" => prepositionSet
    case "ФРАЗ" => prepositionSet
    case "ЧАСТ" => prepositionSet
    case "ЧИСЛ" => numeralSet
    case "ЧИСЛ-П" => numeralSet
    case "ВВОДН" => prepositionSet
    case "ПРЕДК" =>  prepositionSet
    case "ПРЕДЛ" =>  prepositionSet
    case "ИНФИНИТИВ" =>  verbSet
    case "ПРИЧАСТИЕ" =>  participleSet
    case "ДЕЕПРИЧАСТИЕ" =>  participleSet
    case _: String => Set.empty
  }
}
