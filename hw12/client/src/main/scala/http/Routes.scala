package http

import cats.effect.IO
import domain.{Account, Stocks}
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl
import service.ClientService

object Routes {
  def clients(svc: ClientService): HttpRoutes[IO] = {

    val dsl = Http4sDsl[IO]
    import dsl._

    def createResponse[T](x: IO[T])(implicit en: Encoder[T]) =
      x.flatMap(Ok(_))

    object Amount extends QueryParamDecoderMatcher[Int]("amount")
    object Id extends QueryParamDecoderMatcher[Int]("id")
    object Name extends QueryParamDecoderMatcher[String]("name")

    object Count extends QueryParamDecoderMatcher[Int]("count")
    object Company extends QueryParamDecoderMatcher[String]("company")


    implicit val StocksEncoder: Encoder[Stocks] = deriveEncoder
    implicit val AccountEncoder: Encoder[Account] = deriveEncoder

    HttpRoutes.of[IO] {
      case POST -> Root / "user" :? Name(name) =>
        createResponse(svc.createUser(name))

      case GET -> Root / "user" :? Id(id) => createResponse(svc.getUserInfo(id))

      case POST -> Root / "user" / IntVar(id) / "add" :? Amount(m) =>
        createResponse(svc.addMoney(id, m))

      case POST -> Root / "user" / IntVar(id) / "buy" :? Company(c) +& Count(count) =>
        createResponse(svc.buy(id, c, count))
    }
  }
}
