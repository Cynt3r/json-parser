package JSONVal

import org.scalatest.{FunSuite, Matchers}

class JSONValFindByKeyAbs extends FunSuite with Matchers {
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

  test("Simple find") {
    json.findByKeyAbs("age") shouldEqual Some(JSONInt(23))
  }

  test("Advanced find") {
    json.findByKeyAbs("name", "last") shouldEqual Some(JSONString("Král"))
  }

  test("Incorrect absolute path") {
    json.findByKeyAbs("last") shouldEqual None
  }

  test("Almost correct path") {
    json.findByKeyAbs("name", "laast") shouldEqual None
  }

  test("Incorrect path") {
    json.findByKeyAbs("name", "laast", "last") shouldEqual None
  }

  test("Incorrect absolute path disrupted by array") {
    json.findByKeyAbs("pets", "specie") shouldEqual None
  }

  test("Incorrect partly matching absolute path") {
    json.findByKeyAbs("name", "last", "second") shouldEqual None
  }
}
