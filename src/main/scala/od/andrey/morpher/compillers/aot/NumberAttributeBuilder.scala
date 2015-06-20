package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Number, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object NumberAttributeBuilder extends AttributeBuilder[Number.Value] {
  override def build(attributes: Set[Number.Value], s: String): Set[Number.Value] = {
    s match {
      case "ед" => attributes + Number.Singular
      case "мн" => attributes + Number.Plural
      case _ => attributes
    }
  }
}
