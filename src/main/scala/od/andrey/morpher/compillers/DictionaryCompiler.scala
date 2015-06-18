package od.andrey.morpher.compillers

import od.andrey.morpher.dictionary.Dictionary

/**
 * Created by andrey on 17.06.2015.
 */
trait DictionaryCompiler {
  def compile(): Dictionary
}
