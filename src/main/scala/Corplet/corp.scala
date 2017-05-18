package Corplet

import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.{MappedByteBuffer, ByteBuffer}

/** Class to handle connectton to the phrase/word data file
  *
  */
class Corp(val path:String) {
	val source:FileChannel = new RandomAccessFile(path, "rwd").getChannel()
	var isClosed = false;

	def resetPos():Unit = source.position((0).toLong);

	def save():Unit = source.force(false);

	def close():Unit = {
		source.close()
		isClosed = true
	}

	def isAtEnd():Boolean = source.position() == source.size();

	def getHeader():ByteBuffer = {
		val buf = ByteBuffer.allocate(3)
		source.read(buf, 0)
		resetPos()
		buf
	}
	/** Reads first chunk in Corp
	  *
	  */
	def getFirstChunk():MappedByteBuffer = {
		if(source.position() != (3).toLong) source.position((3).toLong);
		source.map(FileChannel.MapMode.READ_WRITE, 3, 243)
	}
	/** Read Chunk from index in document
	  *
	  */
	def getChunk(index:Long):MappedByteBuffer = source.map(FileChannel.MapMode.READ_WRITE, index, 243);

	/** Appends a new chunk to the end of the file and returns it's starting index
	  *
	  */
	def appendChunk(chunk:ByteBuffer):Long = {
		val pos:Long = source.size()
		source.write(chunk, pos)
		save()
		return pos
	}

	/** Appends a new body, 27 byte 27 long chunk to the end of the file and returns its position
	  *
	  */
	def appendBodyChunk():Long = {
		val pos:Long = source.size()
		source.write(ByteBuffer.allocate(243), pos)
		save()
		return pos		
	}
	/** Appends new body chunk, returns the mapped region and its index.
	  *
	  */
	def getAndAppendBodyChunk():(Long, MappedByteBuffer) = {
		val pos:Long = source.size()
		source.write(ByteBuffer.allocate(243), pos)
		save()
		return (pos, source.map(FileChannel.MapMode.READ_WRITE, pos, 243))		
	}
}