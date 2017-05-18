package Corplet

import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "The Hello object" should "say hello" in {
    Corplet.greeting shouldEqual "hello"
  }
}
