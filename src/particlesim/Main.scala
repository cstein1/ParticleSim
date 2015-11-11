package particlesim

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import scala.collection.mutable
import scala.swing._
import scala.swing.event.MouseClicked
import java.io.IOException

/**
 * @author Charles
 */
object Main {
  def impParts(s: String): mutable.Buffer[Particle] = {
    var arrP = s.split(",")
    if (arrP.length % 8 == 0) {
      var listOfP = mutable.Buffer[Particle]()
      for (i <- arrP.indices) {
        val nPart = new Particle(
          new Point3D((arrP(0).toDouble), (arrP(1).toDouble), (arrP(2)).toDouble),
          new Vect3D((arrP(3).toDouble), (arrP(4).toDouble), (arrP(5)).toDouble),
          arrP(6).toDouble, arrP(7).toDouble)
        listOfP += nPart
      }
      listOfP
    } else {
    	println("You're text file is faulty.")
      mutable.Buffer[Particle]()
    }
  }

  def chooseFile:String = {
    val chooser = new FileChooser(new File("."))
    chooser.title = "Import list of particles"
    val result = chooser.showOpenDialog(null)
    if (result == FileChooser.Result.Approve) {
      println("Approve --" + chooser.selectedFile)
          val ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(chooser.selectedFile)))
          var partiList = ois.readObject.toString
          ois.close()
          partiList
    } else {
     throw new IOException("Get help from Dr. Lewis.")
    }
  }
  
  /*
  val pa = new Particle(
    new Point3D(0, 0, 0),
    new Vect3D(0, 0, 0), 1, 10)
  val pb = new Particle(
    new Point3D(10, 5, 0),
    new Vect3D(0, .05, 0), 1e-10, 10)
<<<<<<< HEAD
  
  * var plot = new SimPlot(partiList)
  */
  val initP = impParts(chooseFile)
  var plot = new SimPlot(initP)
  //var partiList = mutable.Buffer(pa, pb)
  val gForce = new GravityForce
  val sim = new Simulation(initP, plot.dt)

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
  /*def readP(path: String): mutable.Buffer[Particle] = {
    val paList = new BufferedInputStream(new FileInputStream(path))
    if (paList.available > 0) {
      val buf = new Array[Byte](paList.available)
      println(paList.read(buf))
    }
  }*/

  def main(args: Array[String]): Unit = {
    chooseFile
    mainFrame.open
    buttonFrame.open

    while (true) {
      sim.applyForce(gForce, plot.dt)
      sim.advance(plot.dt)
      if (boolSwitch == false) mainFrame.repaint()
    }
  }
}