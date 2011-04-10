


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import http.{SHtml, S, DispatchSnippet}
import util.Helpers._
import code.model.{Message, User}

class MessagesTab extends DispatchSnippet{
  def dispatch = {
    case "show" if(User.loggedIn_?) => show 
  }

  def show = {
    val my_messages : List[Message] = User.currentUser.map((u : User) => u.received_messages.all) openOr Nil
    "li *" #> my_messages.map((m : Message) =>
                                  "STRONG" #> m.subject & "i" #> m.text)
  }

}