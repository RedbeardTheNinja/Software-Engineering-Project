


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import common.Full
import http.js.JsCmds.SetHtml
import http.{SHtml, S, DispatchSnippet}
import mapper.By
import util.Helpers._
import code.model.{Message, User}

class MessagesTab extends DispatchSnippet{
  def dispatch = {
    case "showReceived" => showReceived
    case "showSent" => showSent
    case "ShowSend" => sendForm _
  }

  def sendForm(xhtml: NodeSeq): NodeSeq = {
    var new_message = Message.create
    new_message.toForm(Full("Submit"), {
                                        (m: Message) => {
                                          m.sender.set(User.currentUser.open_!.id)
                                          m.sent_date.set(new java.util.Date)
                                          m.save
                                        }
                                       })
  }

  def showReceived = {
    val my_messages : List[Message] = User.currentUser.map((u : User) => u.received_messages.all) openOr Nil
    "li *" #> my_messages.map((m : Message) => "h6 *" #> m.subject & "p *" #> m.body.is)
  }

  def showSent = {
    val my_messages : List[Message] = User.currentUser.map((u : User) => u.sent_messages.all) openOr Nil
    "li *" #> my_messages.map((m : Message) => "h6 *" #> m.subject & "p *" #> m.body.is)
  }
}