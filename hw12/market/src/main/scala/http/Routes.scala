package http

import cats.effect.IO
import dao.MarketRepository
import domain.Stocks
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax.EncoderOps
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl

object Routes {
  def stocksExchange(repo: MarketRepository): HttpRoutes[IO] = {

    val dsl = Http4sDsl[IO]
    import dsl._

    def createResponse[T](x: Either[String, T])(implicit en: Encoder[T]) = {
      x match {
        case Left(err) => BadRequest(err)
        case Right(v)  => Ok(v)
      }
    }

    object Count extends QueryParamDecoderMatcher[Int]("count")
    object Price extends QueryParamDecoderMatcher[Int]("price")
    object Name extends QueryParamDecoderMatcher[String]("company")

    implicit val StocksEncoder: Encoder[Stocks] = deriveEncoder

    HttpRoutes.of[IO] {
      case POST -> Root / "company" :? Name(company) +& Count(count) +& Price(price) =>
        createResponse(repo.addCompany(company, Stocks(count, price)))

      case GET -> Root / "company" :? Name(company) =>
        createResponse(repo.getCompanyInfo(company))

      case GET -> Root / "company" => Ok(repo.getStocksInfo)

      case POST -> Root / "buy" :? Name(company) +& Count(count) =>
        createResponse(repo.buyStocks(company, count))

      case POST -> Root / "change" :? Name(company) +& Price(price) =>
        createResponse(repo.changeStocksPrice(company, price))
    }

  }
}
