package JSONVal

trait JSONVal {
  /** Serializes JSON object into a string
   * @return raw text representation of JSON object
   */
  def toString: String

  /** Finds first JSONVal (DFS approach) that corresponds to the given key.
   * @param key   the key.
   * @return      an option value containing JSONVal corresponding to the given key
   *              otherwise None if it key doesn't exist
   */
  def findByKey(key: String): Option[JSONVal] = None

  /** Finds all JSONVal that corresponds to the given key.
   * @param key   the key.
   * @return      list of JSONVal corresponding to the given key
   */
  def findByKeyAll(key: String): List[JSONVal] = List()

  /** Finds first JSONVal (DFS approach) using the absolute path of keys defined by variadic argument
   * Example:
   *  - {"1" => {"2" => "foo"}}.findByKeyAbs("1", "2) returns Some("foo")
   *  - {"1" => [{"2" => "foo"}, {"3" => "bar"}]}.findByKeyAbs("1", "2) returns None
   *
   * @param keys  the keys representing the absolute path
   * @return      an option value containing JSONVal corresponding to the given absolute path
   *              otherwise None if path doesn't exist
   */
  def findByKeyAbs(keys: String*): Option[JSONVal] = None

  /** Finds if given JSONVal exists in this JSONVal
   * @param target  the needle.
   * @return        true if given JSONVal exists
   *                otherwise false
   */
  def contains(target: JSONVal): Boolean = false

  /** Serializes JSON object into a YAML string
   * @return      raw text representation of YAML object
   */
  def toYaml: String = toString
}
