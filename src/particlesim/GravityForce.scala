package particlesim

class GravityForce(val dt: Double) extends TimeStepForce {
  def calcAccelerations(parts: IndexedSeq[Particle]): IndexedSeq[Vect3D] = {
    (for (i <- (0 until parts.length).par) yield {
      var acc = new Vect3D(0,0,0)
      for (j <- 0 until parts.length) {
        if (i != j) {
          val dvect = parts(i).x - parts(j).x
          val dist = dvect.mag
          val mag = 1 / (dist * dist * dist)
          acc -= dvect * parts(j).mass * mag
        }
      }
      acc
    }).toIndexedSeq
  }
}