


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import util.Helpers._
import code.model.{Listing, User}

class PeopleTab {
  def render = {
    val userList = User.findAll()
    "li *" #> userList.map((person : User) => "h6 *" #> <a href={"/profile?id=" + person.id}>{(person.firstName + " " + person.lastName)}</a>)
  }



}