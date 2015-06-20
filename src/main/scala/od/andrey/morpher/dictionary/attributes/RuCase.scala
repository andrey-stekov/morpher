package od.andrey.morpher.dictionary.attributes

/**
 * Created by andrey on 18.06.2015.
 */
object RuCase extends Enumeration {
  class RuCaseAttribute extends Val with Attribute
  val Nom, Gen, Dat, Acc, Ins, Loc, Voc = new RuCaseAttribute
}


