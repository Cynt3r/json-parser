import JSONVal._
import org.scalatest.{FunSuite, Matchers}

class JSONStringParserTest extends FunSuite with Matchers {
  val jsonParser = new JSONParser()

  test("Correct parse simple string") {
    val json = "  \"sample text\""
    val parsed = jsonParser.parseAll(jsonParser.str, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONString("sample text")
  }

  test("Correct parse empty string") {
    val json = "\"\""
    val parsed = jsonParser.parseAll(jsonParser.str, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONString("")
  }

  test("Correct parse weird string") {
    val json = "\"/+-*/0.'''A#@\""
    val parsed = jsonParser.parseAll(jsonParser.str, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONString("/+-*/0.'''A#@")
  }

  test("Correct parse special chars string") {
    val json = "\" \\\" \\\\ \\/ \\b \\f \\n \\r \\t \u0123 \""
    val parsed = jsonParser.parseAll(jsonParser.str, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONString(" \\\" \\\\ \\/ \\b \\f \\n \\r \\t \u0123 ")
  }

  test("Incorrect parse \\ symbol") {
    val json = "\\"
    val parsed = jsonParser.parseAll(jsonParser.str, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse \" symbol") {
    val json = "\""
    val parsed = jsonParser.parseAll(jsonParser.str, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse unescaped newlines") {
    val json =
      """
        |"
        |I'm
        |really
        |long
        |and
        |fat
        |string
        |"
        |""".stripMargin
    val parsed = jsonParser.parseAll(jsonParser.str, json)
    parsed.successful shouldEqual false
  }
}
