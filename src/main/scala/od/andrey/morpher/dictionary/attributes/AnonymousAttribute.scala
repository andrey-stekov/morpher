package od.andrey.morpher.dictionary.attributes

import java.util.concurrent.ConcurrentSkipListMap

/**
 * Created by andrey on 18.06.2015.
 */
case class AnonymousAttribute(attribute: String) extends Attribute

object AnonymousAttributeBuilder extends AttributeBuilder {
  val cache = new ConcurrentSkipListMap[String, Set[Attribute]]()

  override def build(s: String): Set[Attribute] = {
    var attribute = cache.get(s)

    if (attribute == null) {
      attribute = Set[Attribute](AnonymousAttribute(s))
      cache.put(s, attribute)
    }

    attribute
  }
}
