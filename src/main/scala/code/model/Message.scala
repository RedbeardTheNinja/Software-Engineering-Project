



package code.model

import net.liftweb._
import http.SHtml
import mapper._
import sitemap.Loc.LocGroup
import util._
import common._
import xml.Text._
import xml.{Text, NodeSeq}

class Message extends LongKeyedMapper[Message] with IdPK {

  def getSingleton = Message

  object sent_date extends MappedDate(this)
  object body extends MappedText(this)
  object subject extends MappedString(this,100)
  object sender extends LongMappedMapper(this, User)
  object receiver extends LongMappedMapper(this, User) {

    override def asHtml = {
      <span>
        {User.find(By(User.id, this)).map(u => (u.firstName + " " + u.lastName)).openOr(Text("PM"))}
      </span>
    }

    override def validSelectValues: Box[List[(Long, String)]] =
      Full(User.findAll(OrderBy(User.userName, Ascending)).map(c => (c.id.is, c.niceName)))

    override def _toForm = {
      Full(SHtml.text("",(s : String) => this.set(User.find(By(User.userName,s)).open_!.id.is)))
    }

  }
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
