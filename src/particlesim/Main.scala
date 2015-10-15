package particlesim

import scala.collection.mutable
import scala.swing._

/**
 * @author Charles
 */
object Main {
  val pa = new Particle(
    new Point3D(100, 100, 100),
    new Vect3D(0, 0, 0), 1, 5)
  val pb = new Particle(
    new Point3D(200, 200, 200),
    new Vect3D(0, 0, 1), 1e-10, 1)
  val partiList = mutable.Buffer(pa, pb)
  for (i <- 0 to 100) {
    var rad = util.Random.nextDouble() * 100
    partiList += new Particle(
      new Point3D(util.Random.nextDouble() * 500, util.Random.nextDouble() * 500, util.Random.nextDouble() * 500),
      new Vect3D(0, 0, 0), rad, rad / 100)
  }
  val mainFrame = new MainFrame {
    contents = new BoxPanel(Orientation.NoOrientation) {
      contents += new SimPlot(partiList)
      preferredSize = new Dimension(1000, 1000)
    }
    centerOnScreen()
  }

  def main(args: Array[String]): Unit = {
    mainFrame.open
    val dt = .1
    val gForce = new GravityForce
    val sim = new Simulation(partiList, dt)
    while (true) {
      sim.applyForce(gForce, dt)
      sim.advance(dt)
      mainFrame.repaint()
    }
  }
}