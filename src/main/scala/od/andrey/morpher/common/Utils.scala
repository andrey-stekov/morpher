package od.andrey.morpher.common

import od.andrey.morpher.dictionary.Dictionary
import java.io._

/**
 * Created by andrey on 17.06.2015.
 */
object Utils {
  def fixRussianChars(s: String) = s.replace("ё", "е")

  private def levenshteinDistance(a: String, aLength: Int, b: String, bLength: Int): Int = {
    if (aLength == 0) return bLength
    if (bLength == 0) return aLength

    val cost = if (a.charAt(aLength - 1) == b.charAt(bLength - 1)) 0 else 1

    List(levenshteinDistance(a, aLength - 1, b, bLength) + 1,
         levenshteinDistance(a, aLength    , b, bLength - 1) + 1,
         levenshteinDistance(a, aLength - 1, b, bLength - 1) + cost).min
  }

  def levenshteinDistance(a: String, b: String): Int = {
    levenshteinDistance(a, a.length, b, b.length)
  }

  def storeDictionary(stream: OutputStream, dict: Dictionary): Unit = {
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(dict)
    oos.close()
  }

  def storeDictionary(path: String, dict: Dictionary): Unit = storeDictionary(new FileOutputStream(new File(path)), dict)

  def loadDictionary(stream: InputStream): Dictionary = {
    val ois = new ObjectInputStream(stream)

    ois.readObject() match {
      case dict: Dictionary => dict
      case _ => throw new IllegalArgumentException("Illegal dictionary format")
    }
  }

  def loadDictionary(path: String): Dictionary = loadDictionary(new FileInputStream(new File(path)))
}
