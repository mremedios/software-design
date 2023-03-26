package dao

import domain.{Account, Stocks}

trait ClientRepository {
  /*
      returns user id or error response if user with the name already exists
   */
  def createUser(name: String): Either[String, Int]

  def addMoney(id: Int, amount: Int): Either[String, Unit]

  def getUserInfo(id: Int): Either[String, Account]

  def changeStocks(id: Int, stock: Stocks): Either[String, Unit]
}
