package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
case class AnonymousAttribute(attribute: String)

object AnonymousAttributeBuilder extends AttributeBuilder[AnonymousAttribute] {
  override def build(attributes: Set[AnonymousAttribute], s: String): Set[AnonymousAttribute] = attributes + AnonymousAttribute(s)
}
