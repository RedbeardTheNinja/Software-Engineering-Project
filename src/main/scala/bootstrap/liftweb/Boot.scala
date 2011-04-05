

package bootstrap.liftweb

import net.liftweb._
import http.{LiftRules, NotFoundAsTemplate, ParsePath}
import sitemap.{SiteMap, Menu}
import net.liftweb.sitemap.Loc._
import util.{ NamedPF }


class Boot {
  def boot {
  
  
    // where to search snippet
    LiftRules.addToPackages("code")

    // build sitemap
    val entries = List(Menu("Home") / "index",
                       Menu("News and Jobs") / "NewsJobs",
                       Menu("Messages and People") / "MessagesPeople",
                       Menu("Profile temp link") / "profile" / "profile" >> Hidden) :::
                       Nil
    
    LiftRules.uriNotFound.prepend(NamedPF("404handler"){
      case (req,failure) => NotFoundAsTemplate(
        ParsePath(List("exceptions","404"),"html",false,false))
    })
    
    LiftRules.setSiteMap(SiteMap(entries:_*))
    
    // set character encoding
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    
  }
}