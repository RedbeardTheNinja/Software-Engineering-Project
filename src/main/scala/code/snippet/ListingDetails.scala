


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import http.S
import mapper.By
import util.Helpers._
import code.model.Listing

class ListingDetails {
	def render = {
    val listing = Listing.find(By(Listing.id, S.param("id").open_!.toLong)).open_!
    "#title *" #> listing.title &
      "#contact *" #> listing.contact &
      "#dateTime *" #> listing.when_? &
      "#summary *" #> listing.details &
      "#poster *" #> listing.poster
  }
}