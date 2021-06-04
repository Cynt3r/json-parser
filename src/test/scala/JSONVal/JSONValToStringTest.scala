package JSONVal

import org.scalatest.{FunSuite, Matchers}

class JSONValToStringTest extends FunSuite with Matchers {
  test("JSONNull toString") {
    JSONNull().toString shouldEqual "null"
  }

  test("JSONBool toString") {
    JSONBool(true).toString shouldEqual "true"
    JSONBool(false).toString shouldEqual "false"
  }

  test("JSONInt toString") {
    JSONInt(5).toString shouldEqual "5"
  }

  test("JSONDouble toString") {
    JSONDouble(-1.5).toString shouldEqual "-1.5"
  }

  test("JSONString toString") {
    JSONString("sample text").toString shouldEqual "\"sample text\""
  }

  test("JSONArray toString") {
    JSONArray(List(JSONNull(), JSONBool(true), JSONInt(5))).toString shouldEqual "[null,true,5]"
  }

  test("JSONObj toString") {
    JSONObj(Map("1" -> JSONNull(), "2" -> JSONBool(true), "3" -> JSONInt(5))).toString shouldEqual "{\"1\":null,\"2\":true,\"3\":5}"
  }

  test("Complex toString") {
    val in = JSONObj(Map(
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
      )),
      "some" -> JSONObj(Map(
        "super" -> JSONObj(Map(
          "nested" -> JSONObj(Map("attribute" -> JSONArray(List())))
        ))
      ))
    ))
    val out =
      """{
        |"name" : {
        | "first" : "Václav",
        | "last" : "Král"
        |},
        |"age" : 23,
        |"pets" : [
        | {
        |   "specie" : "dog",
        |   "name" : "Bára",
        |   "age" : 11
        | },
        | {
        |   "specie" : "cat",
        |   "name" : "Felix",
        |   "age" : 2
        | }
        |],
        |"some" : {
        | "super" : {
        |  "nested" : {
        |   "attribute" : []
        |  }
        | }
        |}
        |}
        |""".stripMargin.replaceAll("\\s", "")
    in.toString shouldEqual out
  }
}
