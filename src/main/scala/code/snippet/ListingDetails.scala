


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import http.S
import mapper.By
import util.Helpers._
import code.model.Listing

case class ListingTitle(title : String)

class ListingDetails(details : ListingTitle) {
	def showListing = {
    val listing = Listing.find(By(Listing.title, details.title)).open_!
      "#title *" #> listing.title &
      "#contact *" #> listing.contact &
      "#dateTime *" #> listing.when_? &
      "#summary *" #> listing.details &
      "#poster *" #> listing.poster
  }
}