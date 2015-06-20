package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Attribute, Person, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object PersonAttributeBuilder extends AttributeBuilder {
  val firstSet = Set[Attribute](Person.FirstPerson)
  val secondSet = Set[Attribute](Person.SecondPerson)
  val thirdSet = Set[Attribute](Person.ThirdPerson)

  override def build(s: String): Set[Attribute] = s match {
    case "1л" => firstSet
    case "2л" => secondSet
    case "3л" => thirdSet
    case _ => Set.empty
  }
}
