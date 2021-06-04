package JSONVal

case class JSONArray(content: List[JSONVal]) extends JSONVal {
  override def toString: String = {
    val sb = new StringBuilder()
    sb.append("[")
    content.foreach(x => {
      sb.append(x)
      if (x != content.last) sb.append(",")
    })
    sb.append("]")
    sb.toString
  }

  override def findByKey(key: String): Option[JSONVal] = {
    content.foreach(x => x.findByKey(key) match {
      case Some(x) => return Some(x)
      case _ =>
    })
    None
  }

  override def findByKeyAll(key: String): List[JSONVal] = {
    content.foldLeft(List[JSONVal]())(_ ++ _.findByKeyAll(key))
  }

  override def contains(target: JSONVal): Boolean = {
    if (content.contains(target)) true
    else {
      content.foreach(x => if (x.contains(target)) return true)
      false
    }
  }

  override def toYaml: String = toYamlWithIndent(0)

  def toYamlWithIndent(indent: Int): String = {
    content.foldLeft("")((acc, x) => acc + " " * indent + {x match {
      case y: JSONObj => "-" + System.lineSeparator() + y.toYamlWithIndent(indent+2)
      case _ => "- " + x.toYaml + System.lineSeparator()
    }})
  }
}
