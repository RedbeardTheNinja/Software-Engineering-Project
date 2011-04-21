


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import util.Helpers._
import code.model.{User}

class PeopleTab {
  def render = {
    val userList = User.findAll()
    "li *" #> userList.map((person : User) => "h6 *" #> (person.firstName + " " + person.lastName))
  }



}