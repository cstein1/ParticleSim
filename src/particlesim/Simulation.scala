package particlesim

import scala.collection.mutable

class Simulation(val mParticles: mutable.Buffer[Particle], dt: Double) {
  def applyForce(frc: TimeStepForce, dt: Double) = this.synchronized {
    val to = System.nanoTime()
    val acc = frc.calcAccelerations(mParticles.toIndexedSeq)
    assert(mParticles.length==acc.length)
    for (i <- 0 until mParticles.length) {
      mParticles(i).accelerate(acc(i) * dt)
    }
    val t1 = System.nanoTime()
    var milli = (t1 - to) / 1000000
    println("Calculated in " + milli + " milliseconds")

    //(new GravityForce(dt)).calcAccelerations(mParticles.toIndexedSeq))
    //gforce.calcAccelerations(mParticles.toIndexedSeq)
  }
  def p(index: Int) = mParticles(index)

  def addRandParticle(): Unit = this.synchronized {
    var posneg = if (util.Random.nextDouble() > 0.5) -1 else 1
    var rad = util.Random.nextDouble() * 100
    mParticles += new Particle(
      new Point3D(
        util.Random.nextDouble() * posneg * 10,
        util.Random.nextDouble() * posneg * 10,
        0),
      new Vect3D(0, 0, 0), rad, rad / 10)

    println("Particle Added!")
  }

  def numParticles: Int = { mParticles.length }
  def advance(dt: Double): Unit = {
    mParticles.foreach(_.step(dt))
  }
}