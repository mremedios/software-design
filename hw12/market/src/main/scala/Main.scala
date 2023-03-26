import cats.effect.{IO, IOApp}
import http.HttpServer

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    HttpServer.run
  }
}
