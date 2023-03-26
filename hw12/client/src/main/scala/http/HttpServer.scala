package http

import cats.effect.IO
import com.comcast.ip4s.IpLiteralSyntax
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import service.ClientService

object HttpServer {

  private def appRouter(service: ClientService) = Router(
    "/" -> Routes.clients(service)
  ).orNotFound

  def run(service: ClientService): IO[Unit] = {
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(appRouter(service))
      .build
      .useForever
  }

}
