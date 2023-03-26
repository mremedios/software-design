import cats.effect.{IO, IOApp, Resource}
import dao.InMemoryClientRepository
import http.{HttpClient, HttpServer}
import org.http4s.client.Client
import service.ClientService

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    val http: Resource[IO, Client[IO]] = HttpClient.make
    val rep = new InMemoryClientRepository
    val service = ClientService(http, rep)
    HttpServer.run(service)
  }
}
