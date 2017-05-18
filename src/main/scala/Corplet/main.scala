package Corplet



object main extends App {
	println("Corplet");
	val g = new Corp("build.sbt");
	println(new BodyChunk(g.getFirstChunk(), g).getIndex('t'));
}


