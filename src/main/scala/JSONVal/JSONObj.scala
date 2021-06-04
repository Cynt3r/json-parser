package JSONVal

case class JSONObj(content: Map[String, JSONVal]) extends JSONVal {
  override def toString: String = {
    val sb = new StringBuilder()
    sb.append("{")
    content.foreach(x => {
      sb.append("\"" + x._1 + "\":" + x._2)
      if (x != content.last) sb.append(",")
    })
    sb.append("}")
    sb.toString
  }

  override def findByKey(key: String): Option[JSONVal] = {
    content.get(key) match {
      case Some(x) => Some(x)
      //if key wasn't found we'll recursively call find on each member of the object
      case None => {
        content.foreach(x => x._2.findByKey(key) match {
          case Some(y) => return Some(y)
          case _ =>
        })
        None
      }
    }
  }

  override def findByKeyAll(key: String): List[JSONVal] = {
    content.foldLeft(List[JSONVal]())((acc, x) => {
      //if key matches, add JSONVal to acc together with the results of call on said JSONVal
      if (x._1 == key) x._2 :: acc ++ x._2.findByKeyAll(key)
      else acc ++ x._2.findByKeyAll(key)
    })
  }

  override def findByKeyAbs(keys: String*): Option[JSONVal] = keys.length match {
    case 1 => content.get(keys.head)
    case _ => content.get(keys.head) match {
      case Some(x) => x.findByKeyAbs(keys.tail: _*)
      case None => None
    }
  }

  override def contains(target: JSONVal): Boolean = {
    if (content.exists(_._2 == target)) true
    else {
      content.foreach(x => if (x._2.contains(target)) return true)
      false
    }
  }

  override def toYaml: String = toYamlWithIndent(0)

  def toYamlWithIndent(indent: Int): String = {
    content.foldLeft("")((acc, x) => acc + " " * indent + x._1 + {x._2 match {
      //this is duplicating of code but it's necessary - method toYamlWithIndent() is available only to JSONObj and JSONArray
      case y: JSONObj => ":" + System.lineSeparator() + y.toYamlWithIndent(indent+2)
      case y: JSONArray => ":" + System.lineSeparator() + y.toYamlWithIndent(indent+2)
      case _ => ": " + x._2.toYaml + System.lineSeparator()
    }})
  }
}
