package od.andrey.morpher.compillers.aot

import od.andrey.morpher.dictionary.attributes.{Attribute, Number, AttributeBuilder}

/**
 * Created by andrey on 20.06.2015.
 */
object NumberAttributeBuilder extends AttributeBuilder {
  val singularSet = Set[Attribute](Number.Singular)
  val pluralSet = Set[Attribute](Number.Plural)

  override def build(s: String): Set[Attribute] = {
    s match {
      case "ед" => singularSet
      case "мн" => pluralSet
      case _ => Set.empty
    }
  }
}
