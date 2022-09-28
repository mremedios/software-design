import java.util
import java.util.LinkedList
import java.util.HashMap


class LRUCache[T](_capacity: Int) {
  private val cache = util.LinkedList[Node]()
  private val address = util.HashMap[Int, Node]()
  private var size = 0

  def clear(): Unit = {
    assertSize()

    cache.clear()
    address.clear()
    size = 0
  }

  def get(key: Int): Option[T] = {
    assertSize()

    address.get(key) match
      case null => // cache miss
        None
      case ref => // cache hit
        moveToHead(ref, ref)
        assert(cache.getFirst == ref)
        Some(ref.value)
  }

  def put(key: Int, value: T): Unit = {
    assertSize()

    val newNode = Node(key, value)
    address.get(key) match
      case null => addToCache(newNode)
      case oldRef => moveToHead(oldRef, newNode)

    assertSize()
    assert(cache.getFirst == newNode)
    assert(address.get(key).value == value)
  }

  private def moveToHead(old: Node, newNode: Node): Unit = {
    assertSize()
    assert(old.key == newNode.key)

    cache.remove(old)
    addToHead(newNode)

    assert(address.get(old.key) == cache.getFirst)
    assert(cache.getFirst.value == newNode.value)
    assertSize()
  }

  private def addToCache(ref: Node): Unit = {
    assertSize()

    if (size == _capacity) {
      size -= 1
      val old = cache.removeLast()
      address.remove(old.key)
    }
    size += 1
    addToHead(ref)

    assertSize()
    assert(cache.getFirst == ref)
    assert(address.get(ref.key) == ref)
  }

  private def addToHead(ref: Node): Unit = {
    assertSize()
    assert(cache.size() == size - 1)

    cache.addFirst(ref)
    address.put(ref.key, cache.getFirst)

    assert(address.get(ref.key) == ref) // reference equality
    assert(cache.getFirst == ref)
    assert(cache.size() == size)
  }

  private def assertSize(): Unit = {
    assert(size <= _capacity)
    assert(size >= 0)
  }

  private case class Node(key: Int, value: T)
}