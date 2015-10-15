package particlesim

import java.awt.Color
import scala.collection.mutable
import scala.swing.BorderPanel
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.ComboBox
import scala.swing.Graphics2D
import scala.swing.Orientation
import scala.swing.Panel
import scala.swing.TextField
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased
import scala.swing.event.MouseClicked

class SimPlot(mParticles: mutable.Buffer[Particle]) extends BorderPanel {
  val xaxis: Int = 800
  val yaxis: Int = 600
  val xMin = -10.0
  val xMax = 10.0
  val yMin = -10.0
  val yMax = 10.0
  val xComboBox = new ComboBox(List("x", "y", "z", "vx", "vy", "vz", "time", "energy"))
  val yComboBox = new ComboBox(List("x", "y", "z", "vx", "vy", "vz", "time", "energy"))
  val x2ComboBox = new ComboBox(List("x", "y", "z", "vx", "vy", "vz", "time", "energy"))
  val y2ComboBox = new ComboBox(List("x", "y", "z", "vx", "vy", "vz", "time", "energy"))

  def xMinField: TextField = {
    ???
    /*val choiceMap = Map[String => Unit](
        "x" -> xMin,
        "y" -> yMin
        )*/
  }
  def xMaxField: TextField = {
    ???
  }
  def yMinField: TextField = {
    ???
  }
  def yMaxField: TextField = {
    ???
  }

  val textFieldDim = {

  }

  //def sim(mParti: mutable.Buffer[Particle]): Simulation = {
  //val accel = (new GravityForce).calcAccelerations(mParti.toIndexedSeq)
  //}
  var i = 2;
  val button = new Button {
    listenTo(mouse.clicks)
    reactions += {
      case e: MouseClicked =>
        if (i % 2 == 1) {
          while (true) drawPanel.ignoreRepaint
          i += 1
        } else {
          while (true) drawPanel.repaint()
          i += 1
        }
    }
  }

  val drawPanel = new Panel {
    override def paint(g: Graphics2D): Unit = {
      g.setPaint(Color.BLACK)
      g.fillRect(0, 0, 1000, 1000)
      for (i <- 0 until mParticles.length) {
        g.setColor(new Color(
          (mParticles(i).pos.x / 100.0 % 1).toFloat.abs,
          (mParticles(i).pos.y / 100.0 % 1).toFloat.abs,
          (mParticles(i).pos.z / 100.0 % 1).toFloat.abs))
        g.fillOval(
          (mParticles(i).pos.x).toInt,
          (mParticles(i).pos.y).toInt,
          (mParticles(i).radius * 100).toInt,
          (mParticles(i).radius * 100).toInt)
      }
    }
  }
  val boxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += xComboBox
      contents += new TextField
      contents += new TextField
    }
    contents += new BoxPanel(Orientation.Vertical) {
      contents += yComboBox
      contents += new TextField
      contents += new TextField
    }
    contents += new BoxPanel(Orientation.Vertical) {
      contents += x2ComboBox
      contents += new TextField
      contents += new TextField
    }
    contents += new BoxPanel(Orientation.Vertical) {
      contents += y2ComboBox
      contents += new TextField
      contents += new TextField
    }
    contents += new BoxPanel(Orientation.Vertical) {
      contents += button
    }
  }

  add(drawPanel, BorderPanel.Position.Center)
  add(boxPanel, BorderPanel.Position.North)
}