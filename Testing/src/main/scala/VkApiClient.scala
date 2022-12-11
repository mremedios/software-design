import sttp.client3._

trait VkApiClient {
  def sendRequest(method: String, params: Map[String, String]): Either[String, String]
}


object VkApiClient {

  def make(config: VkConfig): VkApiClient = {
    new VkApiClientImpl(config)
  }

  class VkApiClientImpl(config: VkConfig) extends VkApiClient {
    val client: SimpleHttpClient = SimpleHttpClient()
    val MAX_COUNT = 200

    val defaultParams = Map(
      "count" -> MAX_COUNT,
      "access_token" -> config.accessToken,
      "v" -> config.version)

    override def sendRequest(method: String, params: Map[String, String]): Either[String, String] = {
      val allParams = params ++ defaultParams
      val uri = uri"${config.baseUrl}$method?$allParams"
      val response = client.send(basicRequest.get(uri))
      response.body
    }

  }

  case class VkConfig(baseUrl: String, accessToken: String, version: String)
}