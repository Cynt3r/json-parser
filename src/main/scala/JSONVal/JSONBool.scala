package JSONVal

case class JSONBool(value: Boolean) extends JSONVal {
  override def toString: String = if (value) "true" else "false"
}
