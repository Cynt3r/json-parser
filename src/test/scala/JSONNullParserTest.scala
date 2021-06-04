import JSONVal._
import org.scalatest.{FunSuite, Matchers}

class JSONNullParserTest extends FunSuite with Matchers {
  val jsonParser = new JSONParser()

  test("Correct parse") {
    val json = " null      "
    val parsed = jsonParser.parseAll(jsonParser.nul, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONNull()
  }

  test("Incorrect parse") {
    val json = "NULL"
    val parsed = jsonParser.parseAll(jsonParser.nul, json)
    parsed.successful shouldEqual false
  }
}
