package dao

import domain.Stocks

trait MarketRepository {
    def addCompany(company: String, stocks: Stocks): Either[String, Unit]
    def addStocks(company: String, count: Int): Either[String, Unit]
    def getStocksInfo: List[(String, Stocks)]
    def getCompanyInfo(company: String): Either[String, Stocks]
    def buyStocks(company: String, count: Int): Either[String, Unit]
    def changeStocksPrice(company: String, newStocksPrice: Int): Either[String, Unit]
}