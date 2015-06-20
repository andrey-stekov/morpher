package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.AttributeBuilder

/**
 * Created by andrey on 20.06.2015.
 */
object GenderAttributeBuilder extends AttributeBuilder[Gender.Value] {
  override def build(attributes: Set[Gender.Value], s: String): Set[Gender.Value] = s match {
    case "мр" => attributes + Gender.Male
    case "жр" => attributes + Gender.Female
    case "ср" => attributes + Gender.Middle
    case "мр-жр" => attributes + Gender.Male + Gender.Female
    case _ => attributes
  }
}
