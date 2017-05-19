package Corplet

/** Error for header validation
  *
  */
case class HeaderError(val message:String) extends Throwable;

/** Error for invalid character for range a-z and space
  *
  */
case class CharError(val message:String = "Invalid Char, only a-z and space are accepted") extends Throwable;

/** Error for trying to create a Corp with name that already exists
  *
  */
case class CorpExistsError(val message:String) extends Throwable;
