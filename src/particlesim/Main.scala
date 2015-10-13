package particlesim

import scala.collection.mutable
import scala.swing._

/**
 * @author Charles
 */
object Main {
  val pa = new Particle(
    new Point3D(150, 150, 150),
    new Vect3D(0, 0, 0), 10, 20)
  val pb = new Particle(
    new Point3D(50, 50, 50),
    new Vect3D(0, 0, 0), 5, 10)
  val mid = new Particle(
      new Point3D(500,500,500),
      new Vect3D(0,0,0),
      100000000, 0)
  val partiList = mutable.Buffer(pa, pb,mid)
  for (i <- 0 to 5) {
    partiList += new Particle(
      new Point3D(util.Random.nextDouble()*500, util.Random.nextDouble()*500, util.Random.nextDouble()*500),
      new Vect3D(0, 0, 0), util.Random.nextDouble()*100, util.Random.nextDouble*100)
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
    val dt = .0001
    val gForce = new GravityForce
    val sim = new Simulation(partiList, dt)
    while (true) {
      sim.applyForce(gForce, dt)
      sim.advance(dt)
      mainFrame.repaint()
    }
  }
}