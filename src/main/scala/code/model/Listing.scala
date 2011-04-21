



package code.model

import net.liftweb._
import http.SHtml
import mapper._
import sitemap.Loc.LocGroup
import sitemap.Loc.LocGroup._
import util._
import common._
import xml.Text._
import xml.{NodeSeq, Text}

class Listing extends LongKeyedMapper[Listing] with IdPK {

  def getSingleton = Listing

  object typeOfListing extends Enumeration {
    type Status = Value
    val Job, News = Value
  }

  object poster extends LongMappedMapper(this, User) {
    override def dbIncludeInForm_? = false

    override def asHtml = {
      <span>
        {User.find(By(User.id, this)).map(u => (u.firstName + " " + u.lastName)).openOr(Text("PM"))}
      </span>
    }

    override def _toForm = {

      Full(SHtml.text("", (s: String) => this.set(User.find(By(User.userName, s)).open_!.id.is)))
    }
  }

  object details extends MappedTextarea(this, 2048) {
    override def textareaRows = 10

    override def textareaCols = 50

    override def displayName = "Details"
  }

  object when_? extends MappedDate(this) {
    override def dbColumnName = "timeoflisting"
  }

  object contact extends MappedString(this, 50)

  object is_? extends MappedEnum(this, typeOfListing) {
    override def dbColumnName = "typeoflisting"
  }
}

object Listing extends Listing with LongKeyedMetaMapper[Listing] with CRUDify[Long, Listing] {
    override def dbTableName = "listing"

  override def pageWrapper(body: NodeSeq) = {
    <lift:surround with="admin" at="content">
      {body}
    </lift:surround>
  }

  override def calcPrefix = List("admin", _dbTableNameLC)

  override def showAllMenuLocParams = LocGroup("control") :: Nil

  override def createMenuLocParams = LocGroup("control") :: Nil
}