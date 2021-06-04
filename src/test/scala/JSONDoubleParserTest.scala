import JSONVal._
import org.scalatest.{FunSuite, Matchers}

class JSONDoubleParserTest extends FunSuite with Matchers {
  val jsonParser = new JSONParser()

  test("Correct parse positive double") {
    val json = "   4.2    "
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(4.2)
  }

  test("Correct parse negative double") {
    val json = "-4.2"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(-4.2)
  }

  test("Correct parse large double") {
    val json = "12345.6789"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(12345.6789)
  }

  test("Correct parse positive exponent small e") {
    val json = "1e8"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(1e8)
  }

  test("Correct parse positive exponent big E") {
    val json = "1e8"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(1E8)
  }

  test("Correct parse negative exponent small E") {
    val json = "-1e-8"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(-1e-8)
  }

  test("Correct parse negative exponent big E") {
    val json = "-1E-8"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(-1E-8)
  }

  test("Correct parse explicit positive exponent big E") {
    val json = "-1E+8"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(-1E+8)
  }

  test("Correct parse final boss of decimals") {
      val json = "-0.123E-4"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONDouble(-0.123E-4)
  }

  test("Incorrect parse double starting with zero") {
    val json = "01.23"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse double negative") {
    val json = "--1.0"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse decimal exponent") {
    val json = "1e1.1"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse string double") {
    val json = "\"1.0\""
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse incomplete double") {
    val json = "0."
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse incomplete exponent") {
    val json = "1e"
    val parsed = jsonParser.parseAll(jsonParser.double, json)
    parsed.successful shouldEqual false
  }
}
