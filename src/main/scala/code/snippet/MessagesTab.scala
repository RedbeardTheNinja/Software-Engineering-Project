


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import http.DispatchSnippet
import util.Helpers._
import code.model.{Message, User}

class MessagesTab extends DispatchSnippet{
  def dispatch = {
    case "show" => if(User.loggedIn_?) show _
  }

	def render = "*" #> <strong>this is the messages tab</strong>

  def show = {
    val my_messages : List[Message] = User.currentUser.map((u : User) => u.received_messages.all) openOr Nil
    my_messages.flatMap((m : Message) => "STRONG *" #> m.subject &
                                         "li *" #> m.text)
  }

}