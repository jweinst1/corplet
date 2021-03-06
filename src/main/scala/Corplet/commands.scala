package Corplet

import java.io.{RandomAccessFile, File}

/** Namespace of static methods and commands to create, insert, check
  * and more with Corplet. Top level abstraction
  */
object Commands {
	/** Creates a new .corp file
	  * @throws CorpExistsError if file already exists
	  */
	def create(name:String):Unit = {
		if(new File(name + ".corp").exists()) throw CorpExistsError(s"Corp of name: $name already exists.")
		val file = new RandomAccessFile(name + ".corp", "rwd");
		val header:Array[Byte] = Array(33, 39, 83)
		file.write(header)
		file.close()
	}
	/** Creates a new corplet and inserts an array of phrases into it.
	  * @throws CorpExistsError if file already exists
	  */
	def createFrom(name:String, strs:Array[String]):Unit = {
		if(new File(name + ".corp").exists()) throw CorpExistsError(s"Corp of name: $name already exists.")
		val file = new RandomAccessFile(name + ".corp", "rwd");
		val header:Array[Byte] = Array(33, 39, 83)
		file.write(header)
		file.close()
		val corp:Corp = new Corp(name + ".corp");
		for(str <- strs) insert(corp, str);
	}
	/** Opens a Corplet and returns an instance of the Corp class connecting to it.
	  *
	  */
	def open(name:String):Corp = {
		if(!new File(name + ".corp").exists()) throw CorpExistsError(s"Corp of name: $name does not exist.")
		else new Corp(name + ".corp")
	}
	/** Deletes a Corplet
	  *
	  */
	def delete(name:String):Unit = {
		new File(name + ".corp").delete()
	}
	/** Checks if a Corplet exists
	  *
	  */
	def exists(name:String):Boolean = {
		new File(name + ".corp").exists()
	}
	/** Saves and closes a Corplet
	  * @note New instance of Corplet needed for insertion after this
	  */
	def saveAndClose(corp:Corp):Unit = {
		corp.save()
		corp.close()
	}

	/** Function that inserts a new phrase into the Corplet
	  * @param phrase a string containng only the characters a-z and space
	  * @param corp an instance of the Corp class
	  */
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
				case 2 => {
					curChunk.setGate(phrase(i), (1).toByte)
					curChunk = curChunk.makeNewChunkAtIndex(phrase(i))
				}
			}
		}
		curChunk.setGate(phrase(phrase.length-1), (2).toByte)
	}
	/** Quickly traverses the Corplet and checks if a phrase is contained in it.
	  * @note Takes O(l) complexity where l is the length of the string
	  */
	def contains(corp:Corp, phrase:String):Boolean = {
		var curChunk:BodyChunk = new BodyChunk(corp.getFirstChunk(), corp)
		var has:Boolean = false;
		for(i <- 0 until phrase.length-1){
			curChunk.getGate(phrase(i)) match {
				case 0 => has = false;
				case 1 => curChunk = curChunk.getChunkAtIndex(phrase(i));
				case 2 => curChunk = curChunk.getChunkAtIndex(phrase(i));
			}
		}
		curChunk.getGate(phrase(phrase.length-1)) match {
			case 0 => has = false;
			case 1 => has = false;
			case 2 => has = true;
		}
		return has
	}
	/** Function to validate the header of a .corp file
	  *
	  */
	def validateHeader(corp:Corp):Unit = {
		if(!new HeaderChunk(corp.getHeader()).validate()) throw HeaderError(s"Corplet: ${corp.path} has invalid header")
	}
	/** Takes an array of Strings and returns a zipped tuple of tagged strings
	  * @param tag a string to categorize whether or not a string is in a corp
	  * @param corp instance of Corp class
	  * @return A tuple of tagged strings
	  */
	def tagStrings(corp:Corp, tag:String, strs:Array[String]):Array[(String, String)] = {
		for(str <- strs) yield if(contains(corp, str)) {(str, tag)} else {(str, "NULL")}
	}
	/** Retags a pair of string-string tuples with a Corp
	  *
	  */
	def reTagStrings(corp:Corp, tag:String, pairs:Array[(String, String)]):Array[(String, String)] = {
		for(pair <- pairs) yield if(contains(corp, pair._1)) {(pair._1, tag)} else {pair}
	}
	/** Checks if all the strings in the array are contained in the Corpus
	  *
	  */
	def forAllStrings(corp:Corp, strs:Array[String]):Boolean = {
		strs.forall((s:String) => contains(corp, s))
	}
}