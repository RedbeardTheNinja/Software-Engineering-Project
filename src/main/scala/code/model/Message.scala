



package code.model

import net.liftweb._
import mapper._
import util._
import common._

class Message extends LongKeyedMapper[Message] with IdPK {

  def getSingleton = Message



}
object Message extends Message with LongKeyedMetaMapper[Message]