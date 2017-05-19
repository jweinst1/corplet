package Corplet



object main extends App {
	println("Corplet");
	Commands.create("foo")
	val cur:Corp = Commands.open("foo")
	Commands.insert(cur, "hello")
	println("done")
	Commands.saveAndClose(cur)
}


