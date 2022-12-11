import io.circe.generic.auto._
import io.circe.parser._

import java.time.{Clock, Duration, Instant, ZoneId}
import java.time.temporal.ChronoUnit
import scala.collection.mutable.ArrayBuffer


trait VkStats {
  def getNewsStats(tag: String, n: Int): Either[String, Seq[Int]]
}

object VkStats {

  def make(apiClient: VkApiClient, clock: Clock = Clock.systemDefaultZone()): VkStats = new VkStatsImpl(apiClient, clock)

  class VkStatsImpl(apiClient: VkApiClient, clock: Clock) extends VkStats {

    def getNewsStats(tag: String, lastHours: Int): Either[String, Seq[Int]] = {
      val end = clock.instant()
      val start = end.minus(lastHours, ChronoUnit.HOURS)
      val pars = Map(
        "q" -> tag,
        "start_time" -> start.getEpochSecond.toString,
        "end_time" -> end.getEpochSecond.toString)
      val response = apiClient.sendRequest("newsfeed.search", pars)

      for {
        raw <- response
        jsonResponse <- parse(raw).left.map(_.message)
        items <- jsonResponse.hcursor
          .downField("response")
          .downField("items")
          .as[List[Item]]
          .left.map(_.message)
        dates = items.map(d => Instant.ofEpochSecond(d.date))
      } yield count(dates, start, lastHours)
    }

    def count(dates: Seq[Instant], start: Instant, lastHours: Int): Seq[Int] = {
      val countQueriesList = ArrayBuffer.fill(lastHours)(0)
      val hours = dates.map( Duration.between(start, _).toHours.toInt)
      hours.groupBy(identity).foreach{ case (k, value) => countQueriesList.update(k, value.size)}
      countQueriesList.toSeq // from the oldest to new
    }

    case class Item(date: Long)
  }

}
