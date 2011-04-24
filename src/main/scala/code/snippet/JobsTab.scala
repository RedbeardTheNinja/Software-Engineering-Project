


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import mapper.By
import util.Helpers._
import code.model.Listing

class JobsTab {
	def render = {
    val jobList = Listing.findAll(By(Listing.is_?, Listing.typeOfListing.Job))
    "li *" #> jobList.map((l : Listing) => "h6 *" #> l.title & "#contact *" #> l.contact & "p *" #> l.details & "#poster *" #> l.poster)
  }
}