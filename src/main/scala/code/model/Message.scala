



package code.model

import net.liftweb._
import mapper._
import sitemap.Loc.LocGroup
import util._
import common._
import xml.NodeSeq

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

object Message extends Message with LongKeyedMetaMapper[Message] with CRUDify[Long,Message] {
  override def dbTableName = "message"
  override def pageWrapper(body: NodeSeq) =
        <lift:surround with="default" at="content">{body}</lift:surround>
  override def calcPrefix = List("admin",_dbTableNameLC)
  override def showAllMenuLocParams = LocGroup("admin") :: Nil
  override def createMenuLocParams = LocGroup("admin") :: Nil
}
