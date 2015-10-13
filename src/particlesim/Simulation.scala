package particlesim
import scala.collection.mutable

class Simulation(mParticles: mutable.Buffer[Particle], dt: Double) {
  def applyForce(frc: TimeStepForce, dt: Double) = {
    val acc = frc.calcAccelerations(mParticles.toIndexedSeq)
    for (i <- 0 until mParticles.length) {
      mParticles(i).accelerate(acc(i)*dt)
      println("Force applied")
    }
    //(new GravityForce(dt)).calcAccelerations(mParticles.toIndexedSeq))
    //gforce.calcAccelerations(mParticles.toIndexedSeq)
  }
  def p(index: Int) = mParticles(index)
  def addParticle(p: Particle): Unit = {
    mParticles += p
  }
  def numParticles: Int = { mParticles.length }
  def advance(dt:Double): Unit = {
    mParticles.foreach(_.step(dt))
  }
}