package JSONVal

import org.scalatest.{FunSuite, Matchers}

class JSONValToYamlTest extends FunSuite with Matchers {
  test("JSONNull toYaml") {
    JSONNull().toYaml shouldEqual ""
  }

  test("JSONBool toYaml") {
    JSONBool(true).toYaml shouldEqual "true"
    JSONBool(false).toYaml shouldEqual "false"
  }

  test("JSONInt toYaml") {
    JSONInt(5).toYaml shouldEqual "5"
  }

  test("JSONDouble toYaml") {
    JSONDouble(-1.5).toYaml shouldEqual "-1.5"
  }

  test("JSONString toYaml") {
    JSONString("sample text").toYaml shouldEqual "sample text"
  }

  test("JSONArray toYaml") {
    val ref =
      """- 5.5
        |- true
        |- 5
        |- sample text
        |""".stripMargin
    JSONArray(List(
      JSONDouble(5.5),
      JSONBool(true),
      JSONInt(5),
      JSONString("sample text")
    )).toYaml shouldEqual ref
  }

  test("JSONObj toYaml") {
    val ref =
      """1: 5.5
        |2: true
        |3: 5
        |4: sample text
        |""".stripMargin
    JSONObj(Map(
      "1" -> JSONDouble(5.5),
      "2" -> JSONBool(true),
      "3" -> JSONInt(5),
      "4" -> JSONString("sample text")
    )).toYaml shouldEqual ref
  }

  test("Complex toYaml") {
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
      """name:
        |  first: Václav
        |  last: Král
        |age: 23
        |pets:
        |  -
        |    specie: dog
        |    name: Bára
        |    age: 11
        |  -
        |    specie: cat
        |    name: Felix
        |    age: 2
        |some:
        |  super:
        |    nested:
        |      attribute:
        |""".stripMargin
    in.toYaml shouldEqual out
  }
}
