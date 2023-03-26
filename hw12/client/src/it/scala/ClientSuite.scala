import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.dimafeng.testcontainers.{ForAllTestContainer, GenericContainer}
import dao.InMemoryClientRepository
import domain.{Account, Stocks}
import http.HttpClient
import io.circe.Decoder
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.{Method, Request, Uri}
import org.scalatest.funsuite.AnyFunSuite
import org.testcontainers.containers.wait.strategy.Wait
import service.ClientServiceImpl

class ClientSuite extends AnyFunSuite with ForAllTestContainer {

  val container: GenericContainer = GenericContainer(
    "market:0.1.0-SNAPSHOT",
    exposedPorts = Seq(8080),
    waitStrategy = Wait.forHttp("/company")
  )

  container.start()

  val base = Uri.unsafeFromString(
    s"http://${container.host}:${container.mappedPort(8080)}/"
  )

  val http = HttpClient.make
  val rep = new InMemoryClientRepository
  val service = new ClientServiceImpl(base, http, rep)

  def mkHttpCall[T](r: String, method: Method)(implicit
      x: Decoder[T]
  ): IO[T] = {
    val uri = Uri.unsafeFromString(base + r)
    val req = Request[IO](method, uri)
    http.use {
      _.expect[T](req)
    }
  }

  private def check(f: IO[Unit]): Unit = {
    f.unsafeRunSync()
  }

  test("init") {
    check(
      for {
        _ <- mkHttpCall[Unit](
          "company?company=www&count=4&price=10",
          Method.POST
        )
      } yield ()
    )
  }

  test("add user") {
    check(
      for {
        id <- service.createUser("FirstUser")
        acc <- service.getUserInfo(id)
      } yield {
        assert(id == 0)
        assert(acc == Account("FirstUser", 0, List.empty))
      }
    )
  }

  test("add money to user") {
    check(
      for {
        id <- service.addMoney(0, 100)
        acc <- service.getUserInfo(0)
      } yield {
        assert(acc == Account("FirstUser", 100, List.empty))
      }
    )
  }

  test("buy") {
    check(
      for {
        _ <- service.buy(0, "www", 2)
        acc <- service.getUserInfo(0)
      } yield {
        assert(acc == Account("FirstUser", 80, List(Stocks("www", 2))))
      }
    )
  }


  test("sell") {
    check(
      for {
        _ <- service.sell(0, "www", 1)
        acc <- service.getUserInfo(0)
      } yield {
        assert(acc == Account("FirstUser", 90, List(Stocks("www", 1))))
      }
    )
  }

}
