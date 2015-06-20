package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
trait AttributeBuilder[T] {
  def build(attributes: Set[T], s: String): Set[T]
}
