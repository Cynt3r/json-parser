package JSONVal

case class JSONString(value: String) extends JSONVal {
  override def toString: String = "\"" + value + "\""

  override def toYaml: String = value
}
