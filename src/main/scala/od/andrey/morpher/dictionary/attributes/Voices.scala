package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
object Voices extends Enumeration {
  class VoicesAttribute extends Val with Attribute
  val Active, Passive = new VoicesAttribute
}

