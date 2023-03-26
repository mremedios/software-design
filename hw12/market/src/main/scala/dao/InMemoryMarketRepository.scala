package dao

import domain.Stocks

import scala.collection.mutable

class InMemoryMarketRepository extends MarketRepository {

  private val storage = mutable.Map[String, Stocks]()

  override def addCompany(company: String, stocks: Stocks): Either[String, Unit] = {
    if (storage.contains(company)) {
      Left(s"$company already exists")
    } else {
      storage.put(company, stocks)
      Right()
    }
  }

  override def addStocks(company: String, count: Int): Either[String, Unit] = {
    update(
      company,
      s => storage.put(company, Stocks(s.count + count, s.price))
    )
  }

  override def getStocksInfo: List[(String, Stocks)] = storage.toList

  override def getCompanyInfo(company: String): Either[String, Stocks] = {
    storage.get(company).toRight(s"$company not found")
  }

  override def buyStocks(company: String, count: Int): Either[String, Unit] = {
    update(
      company,
      s =>
        if (s.count - count < 0) None
        else storage.put(company, Stocks(s.count - count, s.price))
    )
  }

  override def changeStocksPrice(
      company: String,
      newStocksPrice: Int
  ): Either[String, Unit] = {
    update(
      company,
      s => storage.put(company, Stocks(s.count, newStocksPrice))
    )
  }

  private def update(
      company: String,
      f: Stocks => Option[Stocks]
  ): Either[String, Unit] = {
    val old = storage.get(company).toRight(s"$company not found")
    old.flatMap(s => f(s).map(_ => ()).toRight("Operation cannot be performed"))
  }

}
