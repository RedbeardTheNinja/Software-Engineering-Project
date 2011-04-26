


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import util.Helpers._
import code.model.{Listing, User}

class PeopleTab {
  def render = {
    val userList = User.findAll()
    "li *" #> userList.map((person : User) => "h6 *" #> <a href={"/Profile/" + person.userName}>{(person.firstName + " " + person.lastName)}</a>)
  }



}