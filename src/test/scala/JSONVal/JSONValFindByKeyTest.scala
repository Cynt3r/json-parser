package JSONVal

import org.scalatest.{FunSuite, Matchers}

class JSONValFindByKeyTest extends FunSuite with Matchers {
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
    json.findByKey("age") shouldEqual Some(JSONInt(23))
  }

  test("Larger object find") {
    json.findByKey("name") shouldEqual Some(JSONObj(Map(
      "first" -> JSONString("Václav"),
      "last" -> JSONString("Král")
    )))
  }

  test("Nested find") {
    json.findByKey("last") shouldEqual Some(JSONString("Král"))
  }

  test("Even more nested find") {
    json.findByKey("specie") shouldEqual Some(JSONString("dog"))
  }

  test("Unsuccessful find") {
    json.findByKey("free time") shouldEqual None
  }
}
