package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
object Tenses extends Enumeration {
  class TensesAttribute extends Val with Attribute
  val Present, Past, Future = new TensesAttribute
}

