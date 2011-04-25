package bootstrap.liftweb

import net.liftweb._
import http.{LiftRules, NotFoundAsTemplate, ParsePath}
import sitemap.Menu.Menuable._
import sitemap.{SiteMap, Menu}
import net.liftweb.sitemap.Loc._
import net.liftweb.mapper.{DB,Schemifier,DefaultConnectionIdentifier,StandardDBVendor,MapperRules}
import util.{Props, NamedPF}
import widgets.autocomplete.AutoComplete
import code.model.{Listing, Message, User}

class Boot {
  def boot {

    AutoComplete.init


    object DBVendor extends StandardDBVendor(
      Props.get("db.class").openOr("org.h2.Driver"),
      Props.get("db.url").openOr("jdbc:h2:database/our_database"),
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
    Schemifier.schemify(true, Schemifier.infoF _, User, Message, Listing)

    val MustBeLoggedIn = If(() => User.loggedIn_?, "")
    val MustBeAdmin = If(() => User.superUser_?, "")
    // build sitemap
    val entries = List(Menu("Home") / "index" >> LocGroup("public"),
                       Menu("News and Jobs") / "NewsJobs" >> LocGroup("public") >> MustBeLoggedIn,
                       Menu("Messages and People") / "MessagesPeople" >> LocGroup("public") >> MustBeLoggedIn,
                       Menu("Profile temp link") / "profile" / "profile" >> LocGroup("public") >> Hidden,
                       Menu("Event or Job Listing") / "Listing" >> LocGroup("public") >> Hidden,
                       Menu("Admin") / "admin" / "index" >> MustBeLoggedIn >> MustBeAdmin >> LocGroup("public"),
                       Menu("Message Control") / "admin" / "Message" >> MustBeLoggedIn >> LocGroup("control") submenus(Message.menus : _*),
                       Menu("Listing Control") / "admin" / "Listing" >> MustBeLoggedIn >> LocGroup("control") submenus(Listing.menus : _*)) :::
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