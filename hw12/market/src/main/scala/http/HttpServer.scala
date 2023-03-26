package http

import cats.effect.IO
import com.comcast.ip4s.IpLiteralSyntax
import dao.InMemoryMarketRepository
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router

object HttpServer {

  private def appRouter[IO] = Router(
    "/" -> Routes.stocksExchange(new InMemoryMarketRepository)
  ).orNotFound

  def run: IO[Unit] = {
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(appRouter)
      .build
      .useForever
  }
}
