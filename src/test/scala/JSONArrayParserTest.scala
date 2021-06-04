import JSONVal._
import org.scalatest.{FunSuite, Matchers}

class JSONArrayParserTest extends FunSuite with Matchers {
  val jsonParser = new JSONParser()

  test("Correct parse simple array") {
    val json = "[5, 4.0, \"oof\", [], {\"key\":\"val\"}]"
    val parsed = jsonParser.parseAll(jsonParser.arr, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONArray(List(
      JSONInt(5),
      JSONDouble(4.0),
      JSONString("oof"),
      JSONArray(List()),
      JSONObj(Map("key" -> JSONString("val")))
    ))
  }

  test("Correct parse empty array") {
    val json = " [    ]  "
    val parsed = jsonParser.parseAll(jsonParser.arr, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONArray(List())
  }

  test("Correct parse nested array") {
    val json = " [ [[0], 1], 2   ]  "
    val parsed = jsonParser.parseAll(jsonParser.arr, json)
    parsed.successful shouldEqual true
    parsed.get shouldEqual JSONArray(List(
      JSONArray(List(
        JSONArray(List(JSONInt(0))),
        JSONInt(1)
      )),
      JSONInt(2)
    ))
  }

  test("Incorrect parse array without coma") {
    val json = "[\"one\" 2]"
    val parsed = jsonParser.parseAll(jsonParser.arr, json)
    parsed.successful shouldEqual false
  }

  test("Incorrect parse array with extra coma") {
    val json = "[4, 5,]"
    val parsed = jsonParser.parseAll(jsonParser.arr, json)
    parsed.successful shouldEqual false
  }
}
