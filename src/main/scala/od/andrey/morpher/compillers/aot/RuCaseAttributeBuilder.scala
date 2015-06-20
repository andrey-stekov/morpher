package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Attribute, RuCase, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object RuCaseAttributeBuilder extends AttributeBuilder {
  val nomSet = Set[Attribute](RuCase.Nom)
  val genSet = Set[Attribute](RuCase.Gen)
  val datSet = Set[Attribute](RuCase.Dat)
  val accSet = Set[Attribute](RuCase.Acc)
  val insSet = Set[Attribute](RuCase.Ins)
  val locSet = Set[Attribute](RuCase.Loc)
  val vocSet = Set[Attribute](RuCase.Voc)

   override def build(s: String): Set[Attribute] = s match {
     case "им" => nomSet
     case "рд" => genSet
     case "дт" => datSet
     case "вн" => accSet
     case "тв" => insSet
     case "пр" => locSet
     case "зв" => vocSet
     case _ => Set.empty
   }
 }
