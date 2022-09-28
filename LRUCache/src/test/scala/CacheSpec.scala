import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfter

class CacheSpec extends AnyFlatSpec with Matchers with BeforeAndAfter {

  val cache: LRUCache[Int] = LRUCache[Int](3)

  before {
    cache.clear()
  }

  "Empty cache" should "not return value" in {
    cache.get(4) shouldBe None
  }

  "Cache" should "return single value" in {
    cache.put(4, 5)
    cache.get(4) shouldBe Some(5)
  }

  it should "return several values in straight order" in {
    cache.put(4, 5)
    cache.put(6, 7)
    cache.get(4) shouldBe Some(5)
    cache.get(6) shouldBe Some(7)
  }

  it should "return several values in reversed order" in {
    cache.put(4, 5)
    cache.put(6, 7)
    cache.get(6) shouldBe Some(7)
    cache.get(4) shouldBe Some(5)
  }

  it should "return several values in random order" in {
    cache.put(4, 5)
    cache.put(6, 7)
    cache.put(8, 9)
    cache.get(6) shouldBe Some(7)
    cache.get(8) shouldBe Some(9)
    cache.get(4) shouldBe Some(5)
  }

  it should "remove old value" in {
    cache.put(4, 5)
    cache.put(6, 7)
    cache.get(8) shouldBe None
    cache.put(8, 9)
    cache.get(4) shouldBe Some(5)
    cache.put(1, 2)
    cache.get(6) shouldBe None
    cache.get(1) shouldBe Some(2)
  }

  it should "replace value by key" in {
    cache.put(4, 5)
    cache.put(6, 7)
    cache.put(1, 2)
    cache.get(8) shouldBe None
    cache.put(8, 9)
    cache.get(4) shouldBe None
    cache.put(1, 6)
    cache.get(6) shouldBe Some(7)
    cache.get(1) shouldBe Some(6)
    cache.get(8) shouldBe Some(9)
  }

}
