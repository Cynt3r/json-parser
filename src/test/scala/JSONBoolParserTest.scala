import JSONVal._
import org.scalatest.{FunSuite, Matchers}

class JSONBoolParserTest extends FunSuite with Matchers {
  val jsonParser = new JSONParser()

  test("Correct parse true") {
    val json = "    true   "
    val parsed = jsonParser.parseAll(jsonParser.bool, json)

    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONBool(true)
  }

  test("Correct parse false") {
    val json = "false"
    val parsed = jsonParser.parseAll(jsonParser.bool, json)

    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONBool(false)
  }

  test("Incorrect parse true") {
    val json = "\"true\""
    val parsed = jsonParser.parseAll(jsonParser.bool, json)

    parsed.successful shouldEqual false
  }

  test("Incorrect parse false") {
    val json = "False"
    val parsed = jsonParser.parseAll(jsonParser.bool, json)

    parsed.successful shouldEqual false
  }
}
