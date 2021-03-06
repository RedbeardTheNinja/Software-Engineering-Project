package code.snippet

/**
 * Created by IntelliJ IDEA.
 * User: Antonio
 * Date: 4/5/11
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */

import xml.{NodeSeq}
import net.liftweb._
import http.S
import mapper.By
import util.Helpers._
import code.model.User


case class profileName(username : String)

class ProfileDetails(pN : profileName) {
	def showPerson = {
    val person = User.find(By(User.userName, pN.username)).open_!
    "#name *" #> (person.firstName + " " + person.lastName) &
    "#summary *" #> person.summary &
    "#uid *" #> person.id &
    "#email *" #> person.email &
    "#time *" #> person.timezone &
    "#status *" #> person.currentStatus


  }
}