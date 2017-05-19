package Corplet



object main extends App {
	println("Corplet");
	val cur:Corp = Commands.open("foo")
	Commands.insert(cur, "hell")
	println("inserted")
	Commands.insert(cur, "helio")
	println("inserted")
	println(Commands.tagStrings(cur, "greeting", Array("hell", "hello", "apple")).mkString(" | "))
	Commands.saveAndClose(cur)
}


