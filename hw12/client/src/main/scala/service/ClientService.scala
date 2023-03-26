package service

import cats.effect.{IO, Resource}
import dao.ClientRepository
import domain.Account
import org.http4s.Uri
import org.http4s.client.Client

trait ClientService {
  def createUser(name: String): IO[Int]

  def addMoney(id: Int, amount: Int): IO[Unit]

  def getUserInfo(id: Int): IO[Account]

  def getTotalUserMoney(id: Int): IO[Int]

  def buy(id: Int, company: String, count: Int): IO[Unit]

  def sell(id: Int, company: String, count: Int): IO[Unit]
}

object ClientService {
  def apply(http: Resource[IO, Client[IO]], repo: ClientRepository) =
    new ClientServiceImpl(
      Uri.unsafeFromString("http://localhost:8080"),
      http,
      repo
    )
}
