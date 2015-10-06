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
    a*v.toDouble
  }
  def step(dt:Double):Unit = {
    
  }
  def advance(a:Vect3D,dt:Double):Unit = {
    
  }
}