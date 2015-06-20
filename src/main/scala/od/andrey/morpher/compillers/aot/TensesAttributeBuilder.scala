package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Tenses, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object TensesAttributeBuilder extends AttributeBuilder[Tenses.Value] {
   override def build(attributes: Set[Tenses.Value], s: String): Set[Tenses.Value] = s match {
     case "нст" => attributes + Tenses.Present
     case "прш" => attributes + Tenses.Past
     case "буд" => attributes + Tenses.Future
     case _ => attributes
   }
 }
