package Corplet







/** Utility functions and variables to assist with binary IO
  *
  */
object Util {

	/** Maps chars from the input string to integer indexes
	  *
	  */
	def inWord(input:Char):Int = {
		input match {
			case 'a' => 0;
			case 'b' => 1;
			case 'c' => 2;
			case 'd' => 3;
			case 'e' => 4;
			case 'f' => 5;
			case 'g' => 6;
			case 'h' => 7;
			case 'i' => 8;
			case 'j' => 9;
			case 'k' => 10;
			case 'l' => 11;
			case 'm' => 12;
			case 'n' => 13;
			case 'o' => 14;
			case 'p' => 15;
			case 'q' => 16;
			case 'r' => 17;
			case 's' => 18;
			case 't' => 19;
			case 'u' => 20;
			case 'v' => 21;
			case 'w' => 22;
			case 'x' => 23;
			case 'y' => 24;
			case 'z' => 25;
			case ' ' => 26;
			case other:Char => throw CharError(s"Invalid char: $input Must be a-z or space")
		}
	}
}