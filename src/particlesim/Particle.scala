package particlesim

/**
 * @author Charles
 */
class Particle(
    private var px:Vect3D, 
    private var pv:Vect3D, 
    val mass:Double,
    val radius:Double) {
  val x = px
  val v = pv
  def accelerate(a:Vect3D,dt:Double):Unit = {
    val force = new GravityForce(1)
    
  }
  def step(dt:Double):Unit = {
    px = x + v*dt
    }
  
  def advance(a:Vect3D,dt:Double):Unit = {
    val force = new GravityForce(dt)
          
  }
  def update() = {
    this.accelerate(pv, 1.0)
    this.step(1.0)
    this.advance(pv,1)
  }
}