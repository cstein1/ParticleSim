package particlesim

/**
 * @author Charles
 */
case class Point3D(x:Double,y:Double,z:Double) {
  def +(o:Vect3D) = new Point3D(x+o.x, y+o.y, z+o.z)
  def -(o:Vect3D) = new Point3D(x-o.x, y-o.y, z-o.z)
  def -(o:Point3D) = new Vect3D(x-o.x,y-o.y,z-o.z)
  def *(c:Double) = new Vect3D(x*c, y*c, z*c)
  def /(c:Double) = new Vect3D(x/c, y/c, z/c)
}