package particlesim

import java.io.File
import java.io.IOException
import scala.collection.mutable
import scala.swing._
import scala.swing.event.MouseClicked
import scala.swing.event.KeyPressed
import scala.swing.event.EditDone

/**
 * @author Charles
 */
object Main {
  def impParts(s: String): mutable.Buffer[Particle] = {
    println(s)
    var arrP = s.split("\\s+").map(_.toDouble)
    if (arrP.length % 8 == 0) {
      var listOfP = mutable.Buffer[Particle]()
      while (arrP.length != 0) {
        val nPart = new Particle(
          new Point3D((arrP(0).toDouble), (arrP(1).toDouble), (arrP(2)).toDouble),
          new Vect3D((arrP(3).toDouble), (arrP(4).toDouble), (arrP(5)).toDouble),
          arrP(6).toDouble, arrP(7).toDouble)
        listOfP += nPart
        arrP = arrP.toList.drop(8).toArray
      }
      listOfP
    } else {
      println("Your text file is faulty.")
      throw new IOException("Get help from Dr. Lewis at mlewis@trinity.edu")
    }
  }

  def chooseFile: String = {
    val chooser = new FileChooser(new File("."))
    chooser.title = "Import list of particles"
    val result = chooser.showOpenDialog(null)
    if (result == FileChooser.Result.Approve) {
      println("Approve --" + chooser.selectedFile)
      val src = scala.io.Source.fromFile(chooser.selectedFile)
      val partIns = try (src.mkString) finally src.close()
      partIns
    } else {
      throw new IOException("Get help from Dr. Lewis at mlewis@trinity.edu")
    }
  }

  val initP = impParts(chooseFile)
  
  val plot = new SimPlot(initP)
  val sim = new Simulation(initP, plot.dt)

  val mainFrame = new MainFrame {
    contents = new BoxPanel(Orientation.NoOrientation) {
      contents += plot
      preferredSize = new Dimension(1000, 1000)
    }
    centerOnScreen()
  }

  val funField: TextField = new TextField {
    listenTo(this)
    reactions += {
      case e: EditDone =>
        funField.text_=(funField.text)
        usrForce = new TreeUserForce(funField.text)                 // Assignment 10
        //usrForce = new UserForce(funField.text)                       // Assignment 9
    }
  }
  funField.text_=("1/(r*r*r)") // Gravity Force
  var usrForce = new TreeUserForce(funField.text)                 // Assignment 10
  //var usrForce = new UserForce(funField.text)                        // Assignment 9

  var boolSwitch = false
  var buttonFrame: MainFrame = new MainFrame {
    contents = new GridPanel(3, 2) {
      contents += new Label("Input Custom Force Equation")
      contents += funField
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
            sim.addRandParticle(5.0)
            buttonFrame.repaint()
        }
      }

      preferredSize = new Dimension(400, 200)
    }
  }

  def main(args: Array[String]): Unit = {
    mainFrame.open
    buttonFrame.open
    while (true) {
      sim.applyForce(usrForce, plot.dt)
      sim.advance(plot.dt)
      if (boolSwitch == false) mainFrame.repaint()
    }
  }
}