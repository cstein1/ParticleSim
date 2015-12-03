package particlesim

import scala.collection.mutable

class Simulation(val mParticles: mutable.Buffer[Particle], dt: Double) {
  def applyForce(frc: TimeStepForce, dt: Double) = this.synchronized {
    val to = System.nanoTime()
    val acc = frc.calcAccelerations(mParticles.toIndexedSeq)
    assert(mParticles.length == acc.length)
    for (i <- 0 until mParticles.length) {
      mParticles(i).accelerate(acc(i) * dt)
    }
    collisions
    val t1 = System.nanoTime()
    var milli = (t1 - to) / 1000000
    //println("Calculated in " + milli + " milliseconds")
  }
  def p(index: Int) = mParticles(index)

		  var switch = -1
  def addRandParticle(): Unit = this.synchronized {
    var posneg = {
      switch *= -1
      util.Random.nextDouble * switch
    }
    var rad = util.Random.nextDouble() * 500
    mParticles += new Particle(
      new Point3D(
        util.Random.nextDouble() * posneg * 500,
        util.Random.nextDouble() * posneg * 500,
        0),
      new Vect3D(util.Random.nextDouble*10, util.Random.nextDouble*10, 0), rad, rad / 10)

    println("Particle Added!")
  }

  def numParticles: Int = { mParticles.length }
  def advance(dt: Double): Unit = {
    mParticles.foreach(_.step(dt))
  }

  private val queue = new PartiPriorityQueue(_ < _)
  def collisions() {
    for (i <- 0 until mParticles.length) {
      for (j <- i+1 until mParticles.length) {
        val p = mParticles(i)
        val p2 = mParticles(j)
        val dx = p.pos - p2.pos
        val dv = p.v - p2.v
        val a = dv dot dv
        val b = 2*(dx dot dv)
        val c = (dx dot dx)-math.pow((p.radius+p2.radius),2)
        val d2t = (-b-math.sqrt(math.pow(b, 2) - 4*a*c))/(2*a)
        if (d2t <= dt && d2t > 0) {
          println("enqueued! d2t = " + d2t)
          queue.enqueue((p, p2, d2t))
          println(queue.length)
        }
      }
    }
    while (!queue.isEmpty) {
      val collision = queue.dequeue
      val p = collision._1
      val p2 = collision._2
      val dx = p.pos - p2.pos
      val dv = p.v - p2.v
      val dnorm = dx / (dx.mag)
      println("dnorm = "+dnorm)
      val cmx = (p.pos * p.mass + p2.pos * p2.mass) / (p.mass + p2.mass)
      val cmv = (p.v * p.mass + p2.v * p2.mass) / (p.mass + p2.mass)
      val cmv1 = p.v - cmv
      println("cmv1 = " + cmv1)
      val cmv2 = p2.v - cmv
      val cmv1perp = dnorm * (cmv1.dot(dnorm))
      p.v -=(cmv1perp * 2)
      println("p accelerated by " + cmv1perp*(-2))
      val cmv2perp = dnorm * (cmv2.dot(dnorm))
      p2.v -=(cmv2perp * 2)
    }
  }
}