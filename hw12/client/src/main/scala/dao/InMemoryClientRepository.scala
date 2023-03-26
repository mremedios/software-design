package dao

import domain.{Account, Stocks}

class InMemoryClientRepository extends ClientRepository {

  val clients = new java.util.ArrayList[Account]()

  var total = 0

  override def createUser(name: String): Either[String, Int] = {
    val sameName = clients.stream().filter(_.name == name).toList
    if (sameName.isEmpty) {
      clients.add(Account(name, 0, List.empty))
      total += 1
      Right(total - 1)
    } else {
      Left(s"$name already exists")
    }

  }

  override def addMoney(id: Int, amount: Int): Either[String, Unit] = {
    check(
      id,
      old =>
        clients.add(id, Account(old.name, old.cash + amount, old.stockList))
    )
  }

  override def changeStocks(id: Int, stock: Stocks): Either[String, Unit] = {
    check(
      id,
      old => {
        val st = old.stockList.filter(_.company == stock.company)
        if (st.isEmpty) {
          clients.add(id, Account(old.name, old.cash, st :+ stock))
        } else {
          val newSt = old.stockList.map { s =>
            if (s.company == stock.company) stock
            else s
          }
          clients.add(id, Account(old.name, old.cash, newSt))
        }

      }
    )
  }

  override def getUserInfo(id: Int): Either[String, Account] = {
    if (id >= total) {
      Left(s"$id is invalid")
    } else {
      Right(clients.get(id))
    }
  }

  private def check(id: Int, f: Account => Unit): Either[String, Unit] = {
    if (id >= total) {
      Left(s"$id is invalid")
    } else {
      val old = clients.get(id)
      f(old)
      Right()
    }
  }
}
