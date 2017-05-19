package Corplet

import java.io.RandomAccessFile

/** Namespace of static methods and commands to create, insert, check
  * and more with Corplet. Top level abstraction
  */
object Commands {
	def createCorplet(name:String):Unit = {
		val file = new RandomAccessFile(name + ".corp", "rwd");
		val header:Array[Byte] = Array(33, 39, 83)
		file.write(header)
		file.close()
	}

	def open(name:String):Corp = {
		new Corp(name + ".corp")
	}

	def saveAndClose(corp:Corp):Unit = {
		corp.save()
		corp.close()
	}

	def insert(corp:Corp, phrase:String):Unit = {
		validateHeader(corp)
		var curChunk:BodyChunk = new BodyChunk(corp.getFirstChunk(), corp)
		for(i <- 0 until phrase.length-1){
			curChunk.getGate(phrase(i)) match {
				case 0 => {
					curChunk.setGate(phrase(i), (1).toByte)
					curChunk = curChunk.makeNewChunkAtIndex(phrase(i))
				}
				case 1 => {
					curChunk = curChunk.getChunkAtIndex(phrase(i))
				}
			}
		}
	}

	def validateHeader(corp:Corp):Unit = {
		if(!corp.getHeader().validate()) throw HeaderError(s"Corplet: ${corp.path} has invalid header")
	}
}