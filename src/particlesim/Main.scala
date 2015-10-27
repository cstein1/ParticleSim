package particlesim

import scala.collection.mutable
import scala.swing._
import scala.swing.event.MouseClicked
import java.io.FileInputStream
import java.io.BufferedInputStream

/**
 * @author Charles
 */
object Main {
  val pa = new Particle(
    new Point3D(0, 0, 0),
    new Vect3D(0, 0, 0), 1, 10)
  val pb = new Particle(
    new Point3D(10, 5, 0),
    new Vect3D(0, .05, 0), 1e-10, 10)
  var partiList = mutable.Buffer(pa, pb)
  var plot = new SimPlot(partiList)
  val gForce = new GravityForce
  val sim = new Simulation(partiList, plot.dt)

  val mainFrame = new MainFrame {
    contents = new BoxPanel(Orientation.NoOrientation) {
      contents += plot
      preferredSize = new Dimension(1000, 1000)
    }
    centerOnScreen()
  }

  var boolSwitch = false

  var buttonFrame: MainFrame = new MainFrame {
    contents = new GridPanel(2, 2) {
      contents += new Label("Click me to Stop Repaint")
      contents += new Button {
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked =>
            boolSwitch ^= true
            buttonFrame.repaint()
        }
      }
      contents += new Label("Click me to add a particle")
      contents += new Button {
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked =>
            sim.addRandParticle()
            buttonFrame.repaint()
        }
      }
      preferredSize = new Dimension(400, 200)
    }
  }
  //def readP(path: String): mutable.Buffer[Particle] = {
    /*val paList = new BufferedInputStream(new FileInputStream(path))
    if (paList.available > 0) {
      val buf = new Array[Byte](paList.available)
      println(paList.read(buf))
    }*/
  //}
  def main(args: Array[String]): Unit = {
    mainFrame.open
    buttonFrame.open

    while (true) {
      sim.applyForce(gForce, plot.dt)
      sim.advance(plot.dt)
      if (boolSwitch == false) mainFrame.repaint()
    }
  }
}