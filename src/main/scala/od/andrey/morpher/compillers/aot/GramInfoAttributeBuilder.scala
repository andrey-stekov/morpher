package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{GramInfo, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object GramInfoAttributeBuilder extends AttributeBuilder[GramInfo.Value] {
  override def build(attributes: Set[GramInfo.Value], s: String): Set[GramInfo.Value] = {
    s match {
      case "сравн" => attributes + GramInfo.Comp
      case "прев" => attributes + GramInfo.Super
      case "пвл" => attributes + GramInfo.Imper
      case _ => attributes
    }
  }
}
