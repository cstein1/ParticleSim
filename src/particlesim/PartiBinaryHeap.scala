package particlesim

import collection.mutable

class PartiBinaryHeap(lt: (Double, Double) => Boolean) {
  private var default: (Particle,Particle,Double) = _
  private val heap = mutable.ArrayBuffer[(Particle,Particle,Double)](default)

  def enqueue(o: (Particle,Particle,Double)) {
    heap += o
    var bubble = heap.length - 1
    while (bubble > 1 && lt(o._3, heap(bubble / 2)._3)) {
      heap(bubble) = heap(bubble / 2)
      bubble /= 2
    }
    heap(bubble) = o
  }
  def dequeue(): (Particle,Particle,Double) = {
    val temp = heap(1)
    val a = heap.remove(heap.length - 1)
    if (heap.length > 1) {
      var stone = 1
      var flag = true
      while (stone * 2 < heap.length && flag) {
        var lesserChild = stone * 2
        if (stone * 2 + 1 < heap.length && lt(heap(stone * 2 + 1)._3, heap(lesserChild)._3)) lesserChild += 1
        if (lt(heap(lesserChild)._3, a._3)) {
          heap(stone) = heap(lesserChild)
          stone = lesserChild
        } else {
          flag = false
        }
      }
      heap(stone) = a
    }
    temp
  }
  def peek: (Particle,Particle,Double) = heap(1)
  def isEmpty: Boolean = heap.length == 1
}