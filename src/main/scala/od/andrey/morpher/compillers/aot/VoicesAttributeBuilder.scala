package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Attribute, Voices, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object VoicesAttributeBuilder extends AttributeBuilder {
  val activeSet = Set[Attribute](Voices.Active)
  val passiveSet = Set[Attribute](Voices.Passive)

  override def build(s: String): Set[Attribute] = s match {
    case "дст" => activeSet
    case "стр" => passiveSet
    case _ => Set.empty
  }
}
