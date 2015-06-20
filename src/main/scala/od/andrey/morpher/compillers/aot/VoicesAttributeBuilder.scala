package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.AttributeBuilder

/**
 * Created by andrey on 20.06.2015.
 */
object VoicesAttributeBuilder extends AttributeBuilder[Voices.Value] {
  override def build(attributes: Set[Voices.Value], s: String): Set[Voices.Value] = s match {
    case "дст" => attributes + Voices.Active
    case "стр" => attributes + Voices.Passive
    case _ => attributes
  }
}
