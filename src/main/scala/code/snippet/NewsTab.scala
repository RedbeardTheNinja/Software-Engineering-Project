


package code.snippet

import xml.{NodeSeq}
import net.liftweb._
import mapper.By
import util.Helpers._
import code.model.{Message, Listing}

class NewsTab {
	def render = {
    val newsList = Listing.findAll(By(Listing.is_?, Listing.typeOfListing.News))
    "li *" #> newsList.map((l : Listing) => "h6 *" #> <a href={"/Listing/" + l.title}>{l.title}</a> &
                                            "#contact *" #> l.contact &
                                            "#dateTime *" #> l.when_? &
                                            "#summary *" #> l.details &
                                            "#poster *" #> l.poster)
  }
}