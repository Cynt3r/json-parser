package JSONVal

case class JSONDouble(value: Double) extends JSONVal {
  override def toString: String = value.toString
}
