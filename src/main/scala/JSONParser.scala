import JSONVal._
import scala.util.parsing.combinator.{JavaTokenParsers, RegexParsers}

class JSONParser extends JavaTokenParsers {
  lazy val value: Parser[JSONVal] = obj | arr | str | double | int | bool | nul

  lazy val obj: Parser[JSONObj] = "{" ~> repsep(row, ",") <~ "}" ^^ (x => JSONObj(x.toMap))

  lazy val arr: Parser[JSONArray] = "[" ~> repsep(value, ",") <~ "]" ^^ (x => JSONArray(x))

  lazy val row: Parser[(String, JSONVal)] = str ~ ":" ~ value ^^ (x => (x._1._1.toString.stripPrefix("\"").stripSuffix("\""), x._2))

  //can't use stringLiteral since it doesn't allow special character sequences like \/ or \t
  //source of the regex: https://regex101.com/library/tA9pM8
  lazy val str: Parser[JSONString] = """("(((?=\\)\\(["\\\/bfnrt]|u[0-9a-fA-F]{4}))|[^"\\\x00-\x1F\x7F]+)*")""".r ^^ {
    x => JSONString(x.stripPrefix("\"").stripSuffix("\""))
  }

  //can't use floatingPointNumber since it allows numbers like: 01.23
  lazy val double: Parser[JSONDouble] = optStr("-") ~ (zero | positiveInt) ~ (fracExp | frac | exp) ^^ {
    x => JSONDouble((x._1._1 + x._1._2 + x._2).toDouble)
  }

  lazy val fracExp: Parser[String] = frac ~ exp ^^ (x => x._1 + x._2)

  lazy val frac: Parser[String] = "." ~ rep1(digit) ^^ (x => x._1 + x._2.foldLeft("")(_ + _))

  lazy val exp: Parser[String] = e ~ optStr(sign) ~ rep1(digit) ^^ (x => x._1._1 + x._1._2 + x._2.foldLeft("")(_ + _))

  lazy val e: Parser[String] = "e" | "E"

  lazy val sign: Parser[String] = "+" | "-"

  //can't use wholeNumber since it allows integers like: 0123
  lazy val int: Parser[JSONInt] = optStr("-") ~ (zero | positiveInt) ^^ (x => JSONInt((x._1 + x._2).toInt))

  lazy val zero: Parser[String] = "0"

  lazy val positiveInt: Parser[String] = posDigit ~ rep(digit) ^^ (x => x._1 +  x._2.foldLeft("")(_ + _))

  lazy val digit: Parser[String] = zero | posDigit

  lazy val posDigit: Parser[String] = """[1-9]""".r ^^ (_.toString)

  lazy val bool: Parser[JSONVal] = boolT | boolF

  lazy val boolT: Parser[JSONVal] = "true" ^^ (_ => JSONBool(true))

  lazy val boolF: Parser[JSONVal] = "false" ^^ (_ => JSONBool(false))

  lazy val nul: Parser[JSONVal] = "null" ^^ (_ => JSONNull())

  def optStr(str: Parser[String]): Parser[String] = opt(str) ^^ {
    case Some(s) => s
    case None => ""
  }
}
