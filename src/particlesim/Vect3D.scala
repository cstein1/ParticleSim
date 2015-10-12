package particlesim

case class Vect3D(x:Double, y:Double, z:Double) {
  def +(o:Vect3D) = new Vect3D(x+o.x, y+o.y, z+o.z)
  def -(o:Vect3D) = new Vect3D(x-o.x, y-o.y, z-o.z)
  def *(c:Double) = new Vect3D(x*c, y*c, z*c)
  def /(c:Double) = new Vect3D(x/c, y/c, z/c)
  def mag = Math.sqrt(x*x+y*y+z*z)
  def dot(o:Vect3D) = x*o.x + y*o.y + z*o.z
  def cross(o:Vect3D) = new Vect3D(y*o.z-z*o.y, z*o.x-x*o.z, x*o.y-y*o.x)
}