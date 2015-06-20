package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Person, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object PersonAttributeBuilder extends AttributeBuilder[Person.Value] {
  override def build(attributes: Set[Person.Value], s: String): Set[Person.Value] = s match {
    case "1л" => attributes + Person.FirstPerson
    case "2л" => attributes + Person.SecondPerson
    case "3л" => attributes + Person.ThirdPerson
    case _ => attributes
  }
}
