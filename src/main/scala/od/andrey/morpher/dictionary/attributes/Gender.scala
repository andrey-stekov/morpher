package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
object Gender extends Enumeration {
  class GenderAttribute extends Val with Attribute
  val Male, Female, Middle = new GenderAttribute
}

