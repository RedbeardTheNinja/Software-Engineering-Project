package code.snippet

/**
 * Created by IntelliJ IDEA.
 * User: Antonio
 * Date: 4/5/11
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */

import xml.{NodeSeq}
import net.liftweb._
import util.Helpers._

class Profile {
  def show = render
	def render = "*" #> <strong>this is the profile page</strong>
}