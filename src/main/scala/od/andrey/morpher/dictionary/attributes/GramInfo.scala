package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
object GramInfo extends Enumeration {
  class GramInfoAttribute extends Val with Attribute
  val Comp, Super, Imper = new GramInfoAttribute
}

