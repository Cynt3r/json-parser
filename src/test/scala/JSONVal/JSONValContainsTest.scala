package JSONVal

import org.scalatest.{FunSuite, Matchers}

class JSONValContainsTest extends FunSuite with Matchers {
  val json = JSONObj(Map(
    "name" -> JSONObj(Map(
      "first" -> JSONString("Václav"),
      "last" -> JSONString("Král")
    )),
    "age" -> JSONInt(23),
    "pets" -> JSONArray(List(
      JSONObj(Map(
        "specie" -> JSONString("dog"),
        "name" -> JSONString("Bára"),
        "age" -> JSONInt(11)
      )),
      JSONObj(Map(
        "specie" -> JSONString("cat"),
        "name" -> JSONString("Felix"),
        "age" -> JSONInt(2)
      ))
    ))
  ))

  test("Simple contains") {
    json.contains(JSONInt(23)) shouldEqual true
  }

  test("Simple contains inside array") {
    val json2 = JSONObj(Map(
      "specie" -> JSONString("dog"),
      "name" -> JSONString("Bára"),
      "age" -> JSONInt(11)
    ))
    json.contains(json2) shouldEqual true
  }

  test("Simple contains with object") {
    val json2 = JSONArray(List(
      JSONObj(Map(
        "name" -> JSONString("Bára"),
        "specie" -> JSONString("dog"),
        "age" -> JSONInt(11)
      )),
      JSONObj(Map(
        "name" -> JSONString("Felix"),
        "specie" -> JSONString("cat"),
        "age" -> JSONInt(2)
      ))
    ))
    json.contains(json2) shouldEqual true
  }

  test("Nested contains") {
    json.contains(JSONString("Král")) shouldEqual true
  }

  test("Even more nested contains") {
    json.contains(JSONString("Bára")) shouldEqual true
  }

  test("Simple not contains") {
    json.contains(JSONString("Bruh")) shouldEqual false
  }

  test("Doesn't contain - order of array matters") {
    val json2 = JSONArray(List(
      JSONObj(Map(
        "name" -> JSONString("Felix"),
        "specie" -> JSONString("cat"),
        "age" -> JSONInt(2)
      )),
      JSONObj(Map(
        "name" -> JSONString("Bára"),
        "specie" -> JSONString("dog"),
        "age" -> JSONInt(11)
      ))
    ))
    json.contains(json2) shouldEqual false
  }
}
