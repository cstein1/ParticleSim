package particlesim

import scala.collection.mutable
import scala.swing._
import scala.swing.event.MouseClicked
import scala.swing.event.MouseEntered

/**
 * @author Charles
 */
object Main {

  val pa = new Particle(
    new Point3D(10, 10, 10),
    new Vect3D(0, 0, 0), 1, 1)
  val pb = new Particle(
    new Point3D(50, 50, 50),
    new Vect3D(0, 0, 0), 1e-10, 1)
  var partiList = mutable.Buffer(pa, pb)
  var plot = new SimPlot(partiList)
  val gForce = new GravityForce
  val sim = new Simulation(partiList, plot.dt)

  val mainFrame = new MainFrame {
    contents = new BoxPanel(Orientation.NoOrientation) {
      contents += plot
      preferredSize = new Dimension(
        plot.xMax.toInt - plot.xMin.toInt,
        plot.yMax.toInt - plot.xMin.toInt)
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
            sim.addRandParticle(partiList)
            buttonFrame.repaint()
        }
      }
      preferredSize = new Dimension(200, 200)
    }
  }

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