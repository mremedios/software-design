package service

import cats.MonadError
import cats.effect.{IO, Resource}
import cats.effect.IO.{raiseError, whenA}
import cats.implicits.{
  catsSyntaxApplicativeByName,
  catsSyntaxApplicativeErrorId,
  toTraverseOps
}
import dao.ClientRepository
import domain.{Account, Stocks}
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import service.models.CompanyStocks
import org.http4s.{EntityDecoder, Method, ParseResult, Request, Uri}
import org.http4s.client.Client

class ClientServiceImpl(
    uri: Uri,
    http: Resource[IO, Client[IO]],
    repo: ClientRepository
) extends ClientService {

  implicit val CompanyStocksDecoder: Decoder[CompanyStocks] = deriveDecoder

  override def createUser(name: String): IO[Int] =
    mkIO(repo.createUser(name))

  override def addMoney(id: Int, amount: Int): IO[Unit] =
    mkIO(repo.addMoney(id, amount))

  override def getUserInfo(id: Int): IO[Account] =
    mkIO(repo.getUserInfo(id)) //TODO

  override def getTotalUserMoney(id: Int): IO[Int] = {
    for {
      account <- mkIO(repo.getUserInfo(id))
      prices <- account.stockList.traverse(s =>
        getCompanyInfo(s.company).map(_.price * s.count)
      )
    } yield prices.sum
  }

  override def buy(id: Int, company: String, count: Int): IO[Unit] = {
    for {
      current <- getCompanyInfo(company)
      userInfo <- mkIO(repo.getUserInfo(id))
      _ <- whenA(
        current.price * count > userInfo.cash && count > current.count
      )(raiseError(new Exception("Not enough money or stocks")))
      // should be atomical
      _ <- buyStocks(company, count)
      _ <- mkIO(repo.changeStocks(id, Stocks(company, current.count - count)))
      _ <- mkIO(repo.addMoney(id, -current.price * count))
    } yield ()
  }

  override def sell(
      id: Int,
      company: String,
      count: Int
  ): IO[Unit] = {
    for {
      current <- getCompanyInfo(company)
      userInfo <- mkIO(repo.getUserInfo(id))
      st = userInfo.stockList.filter(_.company == company).head
      // should be atomical
      _ <- mkIO(repo.changeStocks(id, Stocks(company, st.count - count)))
      _ <- mkIO(repo.addMoney(id, current.price * count))
    } yield ()
  }

  private def getCompanyInfo(company: String): IO[CompanyStocks] = {
    val req = (uri / "company")
      .withQueryParam("company", company)
    http.use {
      _.expect[CompanyStocks](req)
    }
  }

  private def buyStocks(company: String, count: Int): IO[Unit] = {
    val fullUri = (uri / "buy")
      .withQueryParam("company", company)
      .withQueryParam("count", count)
    val req = Request[IO](Method.POST, fullUri)
    http.use { _.expect[Unit](req) }
  }

  private def mkIO[T](e: Either[String, T]): IO[T] =
    IO.fromEither(e.left.map(new Exception(_)))

}
