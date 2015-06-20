package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
object Person extends Enumeration {
  class PersonAttribute extends Val with Attribute
  val FirstPerson, SecondPerson, ThirdPerson = new PersonAttribute
}

