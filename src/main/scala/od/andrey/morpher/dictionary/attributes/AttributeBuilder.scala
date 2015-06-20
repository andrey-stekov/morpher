package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
trait AttributeBuilder {
  def build(s: String): Set[Attribute]
}

trait Attribute