import dao.InMemoryMarketRepository
import domain.Stocks
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite

class MarketSuite extends AnyFunSuite with BeforeAndAfterEach {

  var rep = new InMemoryMarketRepository

  override def beforeEach(): Unit = {
    rep = new InMemoryMarketRepository
  }

  test("empty") {
    assert(rep.getStocksInfo.isEmpty)
    assert(rep.getCompanyInfo("www").isLeft)
  }

  test("add and get") {
    rep.addCompany("www", Stocks(4, 10))

    assert(rep.getStocksInfo.size == 1)

    val st = rep.getCompanyInfo("www")
    assert(st.isRight)
    assert(st.equals(Right(Stocks(4, 10))))
  }

  test("buy and get") {
    rep.addCompany("www", Stocks(4, 10))

    rep.buyStocks("www", 2)

    val st = rep.getCompanyInfo("www")
    assert(st.isRight)
    assert(st.equals(Right(Stocks(2, 10))))
    assert(rep.buyStocks("www", 5).isLeft)
  }

  test("change") {
    rep.addCompany("www", Stocks(4, 10))

    rep.changeStocksPrice("www", 20)

    val st = rep.getCompanyInfo("www")
    assert(st.isRight)
    assert(st.equals(Right(Stocks(4, 20))))
  }

}
