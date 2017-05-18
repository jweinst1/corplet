package Corplet

import java.nio.{MappedByteBuffer, ByteBuffer, LongBuffer}

/** Class to validate or read header of .corp file
  *
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

class BodyChunk(buf:MappedByteBuffer, val ref:Corp){
	buf.position(27)
	val indexes:LongBuffer = buf.asLongBuffer()
	buf.position(0)
	//sets limit so that index is never read as gate
	buf.limit(27)

	def getIndex(key:Char):Long = {
		indexes.get(Util.inWord(key))
	}

	def setIndex(key:Char, ind:Long):Unit = {
		indexes.put(Util.inWord(key), ind)
		buf.force()
	}

	def getChunkAtIndex(key:Char):BodyChunk = {
		new BodyChunk(ref.getChunk(getIndex(key)), ref)
	}

	def makeNewChunkAtIndex(key:Char):BodyChunk = {
		val pair:(Long, MappedByteBuffer) = ref.getAndAppendBodyChunk()
		setIndex(key, pair._1)
		return new BodyChunk(pair._2, ref)
	}

	def getGate(ind:Int):Byte = {
		buf.get(ind)
	}

	def setGate(ind:Int, code:Byte):Unit = {
		buf.put(ind, code)
		buf.force()
	}
}

/** Contains static members for use in gate operations
  *
  */
object Gates {
	val modes:Set[Byte] = Set(0, 1, 2)
}