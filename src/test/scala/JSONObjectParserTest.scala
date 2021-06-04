import JSONVal._
import org.scalatest.{FunSuite, Matchers}

class JSONObjectParserTest extends FunSuite with Matchers {
  val jsonParser = new JSONParser()

  test("Correct parse simple object") {
    val json = "{\"first\" :     5, \"two\":[], \"three\":  false}"
    val parsed = jsonParser.parseAll(jsonParser.obj, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONObj(Map(
      "first" -> JSONInt(5),
      "two" -> JSONArray(List()),
      "three" -> JSONBool(false)
    ))
  }

  test("Correct parse empty object") {
    val json = "          {  }"
    val parsed = jsonParser.parseAll(jsonParser.obj, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONObj(Map())
  }

  test("Correct parse nested object") {
    val json = "{\"zero\" : {} ,  \"one\" : { \"nested\" : {}, \"nested2\"  : null }  }"
    val parsed = jsonParser.parseAll(jsonParser.obj, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONObj(Map(
      "zero" -> JSONObj(Map()),
      "one" -> JSONObj(Map(
        "nested" -> JSONObj(Map()),
        "nested2" -> JSONNull()
      ))
    ))
  }

  test("Incorrect parse object without coma") {
    val json = "{\"first\" :     5 \"two\":[], \"three\":  false}"
    val parsed = jsonParser.parseAll(jsonParser.arr, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse object with extra coma") {
    val json = "{\"first\" :     5, \"two\":[], \"three\":  false,}"
    val parsed = jsonParser.parseAll(jsonParser.arr, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse using object as array") {
    val json = "{5, [], false,}"
    val parsed = jsonParser.parseAll(jsonParser.arr, json)
    parsed.successful shouldEqual false
  }
}
