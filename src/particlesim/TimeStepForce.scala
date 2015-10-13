package particlesim

trait TimeStepForce {
  def calcAccelerations(sim:IndexedSeq[Particle]):IndexedSeq[Vect3D]
}