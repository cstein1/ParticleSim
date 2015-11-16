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
import javafx.scene.control.TextField.TextFieldContent
import scala.swing.Slider
import scala.swing.event.EditDone
import scala.swing.event.ValueChanged
import scala.swing.event.FocusLost
import java.awt.Dimension

class SimPlot(mParticles: mutable.Buffer[Particle]) extends BorderPanel {
  var dt: Double = 0.01
  var xMin = -500
  var xMax = 500
  var yMin = -500
  var yMax = 500
  var xBox = List("Plot: x")
  var yBox = List("Plot: y")
  var zBox = List("Plot: z")
  var numBox = List("dt")
  var partBox = List("Radius", "Mass", "x", "y", "z", "Velocity")
  val xComboBox = new ComboBox(xBox)
  val yComboBox = new ComboBox(yBox)
  val zComboBox = new ComboBox(zBox)
  val numComboBox = new ComboBox(numBox)
  val newPartBox = new ComboBox(partBox)

  val xMinField: TextField = {
    new TextField {
      if (xComboBox.selection.index == 0) {
        text = xMin.toString()
      }
      listenTo(this)
      reactions += {
        case e: EditDone =>
          xMin = text.toInt
      }
    }
  }

  val xMaxField: TextField = {
    new TextField {
      if (xComboBox.selection.index == 0) {
        text = xMax.toString()
      }
      listenTo(this)
      reactions += {
        case e: EditDone =>
          xMax = text.toInt
      }
    }
  }
  val yMinField: TextField = {
    new TextField {
      if (xComboBox.selection.index == 0) {
        text = yMin.toString()
      }
      listenTo(this)
      reactions += {
        case e: EditDone =>
          yMin = text.toInt
      }
    }
  }
  val yMaxField: TextField = {
    new TextField {
      if (xComboBox.selection.index == 0) {
        text = yMax.toString()
      }
      listenTo(this)
      reactions += {
        case e: EditDone =>
          yMax = text.toInt
      }
    }
  }

  val numField: TextField = {
    new TextField {
      if (numComboBox.selection.index == 0) {
        text = dt.toString()
      }
      listenTo(this)
      reactions += {
        case e: EditDone =>
          dt = text.toDouble
      }
    }
  }

  val xMinSlide: Slider = {
    new Slider() {
      min = -1000
      max = 0
      //xMin = value.toDouble
      reactions += {
        case e: ValueChanged =>
          xMin = value
      }
      preferredSize = new Dimension(xMin, xMax)
    }
  }
  val xMaxSlide: Slider = {
    new Slider() {
      min = 0
      max = 1000
      xMax = value
      reactions += {
        case e: ValueChanged =>
          xMax = value
      }
      preferredSize = new Dimension(xMin, xMax)
    }
  }
  val yMinSlide: Slider = {
    new Slider() {
      min = -1000
      max = 0
      //yMin = value.toDouble
      snapToTicks = true
      paintTicks = true
      reactions += {
        case e: ValueChanged =>
          yMin = value
      }
    }
  }
  val yMaxSlide: Slider = {
    new Slider() {
      min = 0
      max = 1000
      yMax = value
      snapToTicks = true
      paintTicks = true
      reactions += {
        case e: ValueChanged =>
          yMax = value
      }
      preferredSize = new Dimension(yMin, yMax)
    }
  }
  val numSlide: Slider = {
    new Slider() {
      min = 0
      max = 3
      value = 1000 * dt.toInt
      snapToTicks = true
      paintTicks = true
      reactions += {
        case e: ValueChanged =>
          dt = value.toDouble / 1000
      }
      preferredSize = new Dimension(yMin, yMax)
    }
  }

  //def sim(mParti: mutable.Buffer[Particle]): Simulation = {
  //val accel = (new GravityForce).calcAccelerations(mParti.toIndexedSeq)
  //}

  val drawPanel = new Panel {
    override def paint(g: Graphics2D): Unit = {
      g.setPaint(Color.BLACK)
      g.fillRect(xMin.toInt, yMin.toInt, size.width*(xMax.toInt - xMin.toInt), size.width*(yMax.toInt - yMin.toInt))
      for (i <- 0 until mParticles.length) {
        g.setColor(new Color(
          ((mParticles(i).pos.x / 10.0) % 1).toFloat.abs,
          ((mParticles(i).pos.y / 10.0) % 1).toFloat.abs,
          ((mParticles(i).pos.y / 10.0) % 1).toFloat.abs))
        g.fillOval(
          ((mParticles(i).pos.x - mParticles(i).radius - xMin) * (size.width / (xMax - xMin))).toInt,
          ((mParticles(i).pos.y - mParticles(i).radius - yMin) * (size.height / (yMax - yMin))).toInt,
          (mParticles(i).radius * (size.width/(xMax - xMin))).toInt,
          (mParticles(i).radius * (size.height/(yMax - yMin))).toInt)
        //((mParticles(i).pos.x - mParticles(i).radius + (xMax - xMin)) / 2).toInt,
        //((mParticles(i).pos.y - mParticles(i).radius + (yMax - yMin)) / 2).toInt,

      }
    }
  }

  val boxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += xComboBox
      contents += xMinField
      contents += xMaxField
    }
    contents += new BoxPanel(Orientation.Vertical) {
      contents += yComboBox
      contents += yMinField
      contents += yMaxField
    }
    contents += new BoxPanel(Orientation.Vertical) {
      contents += numComboBox
      contents += numField
    }
  }

  add(drawPanel, BorderPanel.Position.Center)
  add(boxPanel, BorderPanel.Position.North)
}