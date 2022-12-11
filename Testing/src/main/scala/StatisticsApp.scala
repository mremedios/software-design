
object StatisticsApp extends App {

  val vkConfig = VkApiClient.VkConfig(
    "https://api.vk.com/method/",
    ???,
    "5.131")

  val vkClient = VkApiClient.make(vkConfig)
  val vkStats = VkStats.make(vkClient)

  vkStats.getNewsStats("#фото", 4) match {
    case Left(error) => println(error)
    case Right(res) => println(res.mkString(" "))
  }
}
