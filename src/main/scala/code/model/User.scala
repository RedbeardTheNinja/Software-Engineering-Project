package code.model {

import net.liftweb._
import mapper._
import sitemap.Loc.LocGroup
import sitemap.Loc.LocGroup._
import util._
import common._

/**
 * An O-R mapped "User" class that includes first name, last name, password and we add a "Personal Essay" to it
 */
class User extends MegaProtoUser[User] with OneToMany[Long, User] {
  def getSingleton = User

  // what's the "meta" server

  object Status extends Enumeration {
    type Status = Value
    val FirstYear, SecondYear, ThirdYear, FourthYear, FifthYear, Alumni, Professor = Value
  }

  object userName extends MappedString(this, 40) {
    override def validations = {
      valUnique("Username already taken") _ ::
        super.validations
    }
  }

  // define an additional field for a personal essay
  object summary extends MappedTextarea(this, 2048) {
    override def textareaRows = 10

    override def textareaCols = 50

    override def displayName = "Personal Essay"
  }

  object currentStatus extends MappedEnum(this, Status)

  object sent_messages extends MappedOneToMany(Message, Message.sender, OrderBy(Message.sent_date, Ascending))

  object received_messages extends MappedOneToMany(Message, Message.receiver, OrderBy(Message.sent_date, Ascending))

}

/**
 * The singleton that has methods for accessing the database
 */
object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users"

  // define the DB table name
  override def screenWrap = Full(<lift:surround with="default" at="content">
      <lift:bind/>
  </lift:surround>)

  // define the order fields will appear in forms and output
  override def fieldOrder = userName :: firstName :: lastName :: email :: locale :: timezone :: password :: summary :: Nil

  override def signupFields = firstName :: lastName :: userName :: email :: password :: superUser :: Nil

  override def editFields = firstName :: lastName :: password :: summary :: Nil


  override def loginMenuLocParams = (if(superUser_?) { LocGroup("admin") } else { LocGroup("public") }) :: super.loginMenuLocParams

  override def createUserMenuLocParams = (if(superUser_?) { LocGroup("admin") } else { LocGroup("public") }) :: super.createUserMenuLocParams

  // comment this line out to require email validations
  override def skipEmailValidation = true
}

}