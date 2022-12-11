import org.mockito.ArgumentMatchers.{any, anyString}
import org.mockito.Mockito.{verify, when}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.mockito.MockitoSugar.mock

import java.time.{Clock, Instant, ZoneId}
import scala.io.Source

class VkApiSpec extends AnyFlatSpec {
  val apiClient: VkApiClient = mock[VkApiClient]

  val stats: VkStats = VkStats.make(
    apiClient,
    Clock.fixed(Instant.ofEpochSecond(1666850881), ZoneId.of("UTC")))

  val successResponse: String = Source.fromResource("success_response.txt").mkString
  val invalidResponse: String = Source.fromResource("invalid_json.txt").mkString

  "VkStats" should "return correct answer" in {
    when(apiClient.sendRequest(anyString(), any[Map[String, String]])).thenReturn(Right(successResponse))
    assert(stats.getNewsStats("tag", 4).equals(Right(List(1, 0, 0, 3))))
  }

  it should "return the api error" in {
    when(apiClient.sendRequest(anyString(), any[Map[String, String]])).thenReturn(Left("Error"))
    assert(stats.getNewsStats("tag", 4).equals(Left("Error")))
    verify(apiClient).sendRequest(anyString(), any[Map[String, String]])
  }

  it should "process invalid response" in {
    when(apiClient.sendRequest(any[String], any[Map[String, String]])).thenReturn(Right(invalidResponse))
    assert(stats.getNewsStats("tag", 4).isLeft)
  }

}