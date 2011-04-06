

package bootstrap.liftweb

import net.liftweb._
import http.{LiftRules, NotFoundAsTemplate, ParsePath}
import sitemap.{SiteMap, Menu}
import net.liftweb.sitemap.Loc._
import net.liftweb.mapper.{DB,Schemifier,DefaultConnectionIdentifier,StandardDBVendor,MapperRules}
import util.{Props, NamedPF}
import User.User

class Boot {
  def boot {


    object DBVendor extends StandardDBVendor(
      Props.get("db.class").openOr("org.h2.Driver"),
      Props.get("db.url").openOr("jdbc:h2:database/chapter_eleven"),
      Props.get("db.user"),
      Props.get("db.pass"))

    // where to search snippet
    LiftRules.addToPackages("code")

    if (!DB.jndiJdbcConnAvailable_?) {
      DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)
      LiftRules.unloadHooks.append(() =>
        DBVendor.closeAllConnections_!())
    }

    if (Props.devMode)
    Schemifier.schemify(true, Schemifier.infoF _, User)

    // build sitemap
    val entries = List(Menu("Home") / "index",
                       Menu("News and Jobs") / "NewsJobs",
                       Menu("Messages and People") / "MessagesPeople",
                       Menu("Profile temp link") / "profile" / "profile" >> Hidden,
                       Menu("Even or Job Listing") / "Listing" >> Hidden) :::
                       User.menus
    
    LiftRules.uriNotFound.prepend(NamedPF("404handler"){
      case (req,failure) => NotFoundAsTemplate(
        ParsePath(List("exceptions","404"),"html",false,false))
    })
    
    LiftRules.setSiteMap(SiteMap(entries:_*))
    
    // set character encoding
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    
  }
}