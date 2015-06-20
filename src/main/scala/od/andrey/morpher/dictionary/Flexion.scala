package od.andrey.morpher.dictionary

import od.andrey.morpher.dictionary.attributes.Attribute

/**
  * Created by andrey on 12.06.2015.
  */
@SerialVersionUID(1L)
case class Flexion(ending: String,
                     attrs: Set[Attribute],
                     prefix: String) extends Serializable
