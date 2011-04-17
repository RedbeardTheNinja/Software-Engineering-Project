



package code.model

import net.liftweb._
import mapper._
import util._
import common._

class NewsListing extends LongKeyedMapper[NewsListing] with IdPK {

  def getSingleton = NewsListing

}

object NewsListing extends NewsListing with LongKeyedMetaMapper[NewsListing]