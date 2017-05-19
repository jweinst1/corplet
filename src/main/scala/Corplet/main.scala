package Corplet



object main extends App {
	println("Corplet");
	val cur:Corp = Commands.open("foo")
	println(Commands.forAllStrings(cur, Array("hell", "hello", "apple")))
	Commands.saveAndClose(cur)
}


