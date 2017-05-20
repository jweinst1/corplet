package Corplet

import java.nio.{MappedByteBuffer, ByteBuffer, LongBuffer}

/** Class to validate or read header of .corp file
  * @constructor Takes one bytebuffer as an argument
  */
class HeaderChunk(buf:ByteBuffer) {
	/** Checks the byte sequence of file header
	  *
	  */
	def validate():Boolean = {
		buf.get(0) == (33.toByte) && 
		buf.get(1) == (39).toByte &&
		buf.get(2) == (83).toByte
	}
}
/** Class to serve as a binary trie nod that travereses the Corplet
  * @note A Gate is a byte slot that can have the values 0, 1 or 2
  * @note Uses memory mapped buffer for performance and boost and thread safety
  */
class BodyChunk(buf:MappedByteBuffer, val ref:Corp){
	buf.position(27)
	val indexes:LongBuffer = buf.asLongBuffer()
	buf.position(0)
	//sets limit so that index is never read as gate
	buf.limit(27)

	/** Gets the index/pointer at some position in the index portion of the chunk
	  * @note indexes are all identical to Java's Long type
	  *
	  */
	def getIndex(key:Char):Long = {
		indexes.get(Util.inWord(key))
	}
	/** Sets some index in the chunk for a pointer to some position in the file
	  * @param ind A 64 bit number that corresponds to some position in the file
	  */
	def setIndex(key:Char, ind:Long):Unit = {
		indexes.put(Util.inWord(key), ind)
		buf.force()
	}
	/** Gets the next bodychunk that is present at some index in the file
	  *
	  */
	def getChunkAtIndex(key:Char):BodyChunk = {
		new BodyChunk(ref.getChunk(getIndex(key)), ref)
	}
	/** Makes a new chunk at the end of the file and sets it in the specified index
	  * @note used for inserting new phrases into the corplet.
	  */
	def makeNewChunkAtIndex(key:Char):BodyChunk = {
		val pair:(Long, MappedByteBuffer) = ref.getAndAppendBodyChunk()
		setIndex(key, pair._1)
		return new BodyChunk(pair._2, ref)
	}

	def getGate(ind:Char):Byte = {
		buf.get(Util.inWord(ind))
	}

	def setGate(ind:Char, code:Byte):Unit = {
		buf.put(Util.inWord(ind), code)
		buf.force()
	}
}

/** Contains static members for use in gate operations
  *
  */
object Gates {
	val modes:Set[Byte] = Set(0, 1, 2)
}