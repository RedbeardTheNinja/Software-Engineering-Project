



package code.model

import net.liftweb._
import mapper._
import util._
import common._

class Message extends LongKeyedMapper[Message] with IdPK {

  def getSingleton = Message

  object sent_date extends MappedDate(this)
  object text extends MappedText(this)
  object subject extends MappedString(this,100)
  object sender extends LongMappedMapper(this, User)
  object receiver extends LongMappedMapper(this, User)
  object sender_deleted extends MappedBoolean(this) {
    override def defaultValue = false
  }
  object receiver_deleted extends MappedBoolean(this) {
      override def defaultValue = false
  }

}

object Message extends Message with LongKeyedMetaMapper[Message] {
  override def dbTableName = "message"
}
