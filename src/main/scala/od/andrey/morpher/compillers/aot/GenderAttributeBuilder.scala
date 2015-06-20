package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Attribute, Gender, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object GenderAttributeBuilder extends AttributeBuilder {
  val maleSet = Set[Attribute](Gender.Male)
  val femaleSet = Set[Attribute](Gender.Female)
  val middleSet = Set[Attribute](Gender.Middle)
  val maleAndFemaleSet = Set[Attribute](Gender.Male, Gender.Female)

  override def build(s: String): Set[Attribute] = s match {
    case "мр" => maleSet
    case "жр" => femaleSet
    case "ср" => middleSet
    case "мр-жр" => maleAndFemaleSet
    case _ => Set.empty
  }
}
