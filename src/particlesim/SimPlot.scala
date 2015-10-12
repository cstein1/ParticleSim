package particlesim

import java.awt.Color
import java.awt.geom._
import scala.swing._
import scala.collection.mutable

class SimPlot(mParticles:mutable.Buffer[Particle]) extends BorderPanel {
  val xaxis: Int = 800
  val yaxis: Int = 600
  val xMin = -10.0
  val xMax = 10.0
  val yMin = -10.0
  val yMax = 10.0
  val xComboBox = new ComboBox(List("x", "y", "z", "vx", "vy", "vz", "time", "energy"))
  val yComboBox = new ComboBox(List("x", "y", "z", "vx", "vy", "vz", "time", "energy"))

  def xMinField: TextField = { ??? }
  def xMaxField: TextField = { ??? }
  def yMinField: TextField = { ??? }
  def yMaxField: TextField = { ??? }

  val textFieldDim = {

  }

  def sim(mParti:mutable.Buffer[Particle]): Simulation = { 
    val accel=(new GravityForce).calcAccelerations(mParti.toIndexedSeq)
    
  }
  
  val parti = new Particle((1,1,1),(1,1,1),1,10,10)

  val drawPanel = new Panel {
    override def paint(g: Graphics2D): Unit = {
      g.setBackground(Color.white)
      g.setColor(Color.BLACK)
      for(i<-mParticles.indices) {
        /*
        math.random*100 match {
          case x if(x>0&&x<25) g.setColor(Color.green)
        }
        */
        g.fillOval((mParticles(i).x.x*100).toDouble, (mParticles(i).x.y*100).toDouble, (mParticles(i).radius*100).toDouble, (mParticles(i).radius*100).toDouble)
        mParticles(i).draw(g)
      }
      
    }
    preferredSize = new Dimension(600,800)
  }
  val boxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += new BoxPanel(Orientation.Vertical) {
      xComboBox
      new TextArea
      new TextArea
    }
    contents += new BoxPanel(Orientation.Vertical)
      yComboBox
      new TextArea
      new TextArea
  }
}