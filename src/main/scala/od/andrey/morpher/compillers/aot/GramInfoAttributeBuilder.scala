package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Attribute, GramInfo, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object GramInfoAttributeBuilder extends AttributeBuilder {
  val compSet = Set[Attribute](GramInfo.Comp)
  val superSet = Set[Attribute](GramInfo.Super)
  val imperSet = Set[Attribute](GramInfo.Imper)

  override def build(s: String): Set[Attribute] = {
    s match {
      case "сравн" => compSet
      case "прев" => superSet
      case "пвл" => imperSet
      case _ => Set.empty
    }
  }
}
