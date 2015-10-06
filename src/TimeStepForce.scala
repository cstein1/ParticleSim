package particlesim

/**
 * @author Charles
 */
trait TimeStepForce {
  val dt:Double
  def calcAccelerations(sim:IndexedSeq[Particle]):IndexedSeq[Vect3D]
}