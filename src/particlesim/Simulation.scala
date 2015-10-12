package particlesim
import scala.collection.mutable

class Simulation(mParticles:mutable.Buffer[Particle],dt:Int) {
  def force:TimeStepForce = {
    (new GravityForce(dt)).calcAccelerations(mParticles.toIndexedSeq)
    //gforce.calcAccelerations(mParticles.toIndexedSeq)
  }
  def p(index:Int) = mParticles(index) 
  def addParticle(p:Particle):Unit = {
    mParticles += p
  }
  def numParticles:Int = {mParticles.length}
  def advance():Unit = {
    mParticles.foreach(_.update)
  }
}