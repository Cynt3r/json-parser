import JSONVal._
import org.scalatest.{FunSuite, Matchers}

class JSONIntParserTest extends FunSuite with Matchers {
  val jsonParser = new JSONParser()

  test("Correct parse positive num") {
    val json = "   4    "
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONInt(4)
  }

  test("Correct parse large positive num") {
    val json = "123456789"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONInt(123456789)
  }

  test("Correct parse negative num") {
    val json = "-42"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONInt(-42)
  }

  test("Correct parse zero") {
    val json = "0"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONInt(0)
  }

  test("Correct parse negative zero") {
    val json = "-0"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONInt(-0)
  }

  test("Incorrect parse decimal") {
    val json = "1.0"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse exponent") {
    val json = "1e8"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse string") {
    val json = "\"1\""
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse starting with zero") {
    val json = "05"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse two zeros") {
    val json = "00"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse double minus sign") {
    val json = "--1"
    val parsed = jsonParser.parseAll(jsonParser.int, json)
    parsed.successful shouldEqual false
  }
}
