package Corplet

import scala.collection.mutable.HashMap

/** Object to facilitate a REPL to interact with Corplet and test
  *
  */
object Repl {

	/** Runs the Repl on the command line to interact with Corplets
	  *
	  */
	def run():Unit = {
		var corps:HashMap[String, Corp] = HashMap()
		var running:Boolean = true
		var input:String = ""
		var args:Array[String] = Array()
		printIntro()
		while(running){
			input = readLine("corp> ")
			args = input.split(" ")
			if(args.length == 0) {println("Error: No Repl Input")}
			else args(0) match {
				case "create" => {
					Commands.create(args(1))
					println(s":[Created Corplet ${args(1)}]")
				}
				case "open" => {
					corps(args(1)) = Commands.open(args(1))
					println(s":[Opened Corplet ${args(1)}]")
				}
				case "close" => {
					Commands.saveAndClose(corps(args(1)))
					corps.remove(args(1))
					println(s":[Closed Corplet ${args(1)}]")
				}
				case "quit" => {
					for((_, c) <- corps) Commands.saveAndClose(c);
					println(":[Quit Corplet]")
					running = false
				}
				case "insert" => {
					for(i <- 2 until args.length) Commands.insert(corps(args(1)), args(i));
					println(s":[Inserted ${args.slice(2, args.length).mkString(",")} into Corp: ${args(1)}]")
				}
				case "contains" => for(i <- 2 until args.length) {
					println(s":[${args(1)} = ${args(i)} -> ${Commands.contains(corps(args(1)), args(i))}]")
				}
			}
		}
	}

	/** Prints the introduction at the start of the Repl
	  *
	  */
	def printIntro():Unit = {
		println("----- Corplet Repl -----")
		println("------************------")
		println("|--By: Josh Weinstein--|")
	}
}