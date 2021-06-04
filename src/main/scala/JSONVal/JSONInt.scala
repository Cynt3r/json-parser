package JSONVal

case class JSONInt(value: Int) extends JSONVal {
  override def toString: String = value.toString
}
