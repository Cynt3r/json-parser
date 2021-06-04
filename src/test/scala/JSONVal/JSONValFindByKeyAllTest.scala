package JSONVal

import org.scalatest.{FunSuite, Matchers}

class JSONValFindByKeyAllTest extends FunSuite with Matchers {
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
    json.findByKeyAll("first") shouldEqual List(JSONString("Václav"))
  }

  test("Advanced find") {
    json.findByKeyAll("specie") shouldEqual List(JSONString("dog"), JSONString("cat"))
  }

  test("Complex find") {
    json.findByKeyAll("name") shouldEqual List(
      JSONObj(Map(
        "first" -> JSONString("Václav"),
        "last" -> JSONString("Král")
      )),
      JSONString("Bára"),
      JSONString("Felix"),
    )
  }

  test("Find with recursive behaviour") {
    val target = JSONObj(Map("target" -> JSONInt(42)))
    val x = JSONObj(Map(
      "target" -> target,
      "thrash" -> JSONNull()
    ))
    x.findByKeyAll("target") shouldEqual List(target, JSONInt(42))
  }

  test("Unsuccessful find") {
    json.findByKeyAll("free time") shouldEqual List()
  }
}
