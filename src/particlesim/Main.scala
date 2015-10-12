package particlesim

import scala.swing._

/**
 * @author Charles
 */
object Main {
  val pa = new Particle(
    new Vect3D(1, 1, 1),
    new Vect3D(1, 0, 0), 10, 2)
  val pb = new Particle(
    new Vect3D(-1, -1, -1),
    new Vect3D(1, 0, 0), 5, 1)
  // def sim:Simulation = {???}
  val mainFrame = new MainFrame {
    contents = new BoxPanel(Orientation.NoOrientation) {
      contents += new SimPlot
    }
  }
  
  def main(args: Array[String]): Unit = {
    mainFrame.open  
  }
}