package particlesim

/**
 * @author Charles
 */
class PartiPriorityQueue(lt:(Double,Double) => Boolean) {
  private class Node(val coll:(Particle,Particle,Double), var prev:Node, var next: Node)
  private val end = new Node(new Array[(Particle,Particle,Double)](1)(0), null, null)
  end.next = end
  end.prev = end
  def enqueue(o:(Particle,Particle,Double)) {
    var rover = end.prev
    while (rover != end && lt(rover.coll._3,o._3)) rover = rover.prev
    val n = new Node(o,rover,rover.next)
    rover.next.prev = n
    rover.next = n
  }
  def dequeue():(Particle,Particle,Double) = {
    val ret = end.next.coll
    end.next = end.next.next
    end.next.prev = end
    ret
  }
  
  def isEmpty: Boolean = end.next == end
  def peek:(Particle,Particle,Double) = end.next.coll
  def length: Int = {
    var rover = end.prev
    var counter = 0
    while (rover != end) {
      rover = rover.prev; 
      counter += 1
    }
    counter
  }
}