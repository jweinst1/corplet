package Corplet

import scala.collection.mutable.HashMap

/** Object to facilitate a REPL to interact with Corplet and test
  *
  */
object Repl {

	def run():Unit = {
		val corps:HashMap[Corp] = HashMap()
		var running:Boolean = true
		var input:String = ""
		var args:Array[String] = Array()
		printIntro()
		while(running){
			input = readLine("corp> ")
			args = input.split(" ")
			if(args.length == 0) {println("Error: No Repl Input")}
			else args(0) match {
				case "create" => {}
				
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