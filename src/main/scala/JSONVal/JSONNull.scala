package JSONVal

case class JSONNull() extends JSONVal {
  override def toString: String = "null"

  override def toYaml: String = ""
}
