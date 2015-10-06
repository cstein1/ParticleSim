import swing._
import scala.swing.Panel
import scala.swing.Panel
import scala.swing.Panel
import java.awt.Panel
import java.awt.Graphics2D
import java.awt.TextField
/**
 * @author Charles
 */
class SimPlot(g:Graphics2D) extends BorderPanel {
  val xaxis: Int = ???
  val yaxis: Int = ???
  val xMin = -10.0
  val xMax = 10.0
  val yMin = -10.0
  val yMax = 10.0
  val comboBoxArea = {
    val xComboBox = new ComboBox(List("x", "y", "z", "vx", "vy", "vz", "time", "energy"))
    val yComboBox = new ComboBox(List("x", "y", "z", "vx", "vy", "vz", "time", "energy"))
  }
  
  def xMinField:TextField = {???}
  def xMaxField:TextField = {???}
  def yMinField:TextField = {???}
  def yMaxField:TextField = {???}
    
  val textFieldDim = {
    
  }
  
  
  def sim: Simulation = {???}
  
  val drawPanel = new Panel {
     override def paint(g:Graphics2D):Unit = {
   }
     layout += new ComboPanel {
       
     }
 }
}