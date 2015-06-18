package od.andrey.morpher.common

import scala.collection.mutable

/**
 * Created by andrey on 15.06.2015.
 */
@SerialVersionUID(1L)
class Weights[T] extends Serializable {
  val groups = new mutable.HashMap[T, Double]
  var total = 0.0

  def += (group: T) = {
    total += 1.0
    val weight = if (groups contains group) groups(group) + 1.0 else 1.0
    groups += ((group, weight))
  }

  def apply(group: T): Option[Double] = {
    if (groups contains group) Option.apply(groups(group) / total) else Option.empty
  }

  def weights = groups.map {case (group, count) => (group, count / total)}

  def rank = weights.toList.sortBy(_._2).reverse

  def max = rank.head._1

  override def toString = "Weights( " + weights.toString() + " )"
}