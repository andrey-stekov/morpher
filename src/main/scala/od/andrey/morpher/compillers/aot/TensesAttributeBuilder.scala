package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Attribute, Tenses, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object TensesAttributeBuilder extends AttributeBuilder {
  val presentSet = Set[Attribute](Tenses.Present)
  val pastSet = Set[Attribute](Tenses.Past)
  val futureSet = Set[Attribute](Tenses.Future)

   override def build(s: String): Set[Attribute] = s match {
     case "нст" => presentSet
     case "прш" => pastSet
     case "буд" => futureSet
     case _ => Set.empty
   }
 }
