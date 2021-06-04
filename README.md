[![pipeline status](https://gitlab.fit.cvut.cz/kralva10/json-parser/badges/master/pipeline.svg)](https://gitlab.fit.cvut.cz/kralva10/json-parser/commits/master)
[![coverage report](https://gitlab.fit.cvut.cz/kralva10/json-parser/badges/master/coverage.svg)](https://gitlab.fit.cvut.cz/kralva10/json-parser/commits/master)

# JSON parser
The main part of project is a parser of JSON files that serializes files into ```JSONVal``` structure represented by these types:
 - ```JSONNull```
 - ```JSONBool```
 - ```JSONInt```
 - ```JSONDouble```
 - ```JSONString```
 - ```JSONArray```
 - ```JSONObj```

Each of these types extends trait ```JSONVal``` which provides following methods:
```scala
def toString: String //serializes value into raw JSON
```
```scala
def toYaml: String //serializes value into YAML
```
```scala
def findByKey(key: String): Option[JSONVal] //looks up object by key
```
```scala
def findByKeyAll(key: String): List[JSONVal] //looks up all objects by key
```
```scala
def findByKeyAbs(keys: String*): Option[JSONVal] //looks up object by specified absolute path
```
```scala
def contains(target: JSONVal): Boolean //checks existence of value
```
