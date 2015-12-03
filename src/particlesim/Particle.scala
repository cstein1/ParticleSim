package particlesim

/**
 * @author Charles
 */
class Particle(
    private var px: Point3D,
    private var pv: Vect3D,
    val mass: Double,
    val radius: Double) {
  def pos = px
  def v = pv
  def accelerate(dv: Vect3D): Unit = {
    this.pv += dv
  }
  def step(dt: Double): Unit = {
    px = pos + v * dt
  }
  def v_=(inp:Vect3D) = pv = inp
}