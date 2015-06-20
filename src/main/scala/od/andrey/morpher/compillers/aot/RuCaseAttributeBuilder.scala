package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{RuCase, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object RuCaseAttributeBuilder extends AttributeBuilder[RuCase.Value] {
   override def build(attributes: Set[RuCase.Value], s: String): Set[RuCase.Value] = s match {
     case "им" => attributes + RuCase.Nom
     case "рд" => attributes + RuCase.Gen
     case "дт" => attributes + RuCase.Dat
     case "вн" => attributes + RuCase.Acc
     case "тв" => attributes + RuCase.Ins
     case "пр" => attributes + RuCase.Loc
     case "зв" => attributes + RuCase.Voc
     case _ => attributes
   }
 }
