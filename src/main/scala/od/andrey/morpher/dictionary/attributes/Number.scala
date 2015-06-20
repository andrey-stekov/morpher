package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
object Number extends Enumeration {
  class NumberAttribute extends Val with Attribute
  val Singular, Plural = new NumberAttribute
}

