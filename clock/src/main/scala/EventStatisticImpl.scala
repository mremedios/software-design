import java.time.{Clock, Instant}
import java.time.temporal.ChronoUnit
import scala.collection.mutable

class EventStatisticImpl(val clock: Clock) extends EventStatistic {
    final private val HOUR = 60.0
    final private val events = mutable.Map[String, List[Instant]]()

    override def incEvent(name: String): Unit = {
        val list: List[Instant] = events.getOrElse(name, List())
        events.put(name, list :+ clock.instant())
    }

    private def lastHourRpm(list: List[Instant]): Double = {
        val hourAgo = clock.instant.minus(1, ChronoUnit.HOURS)
        list.count(_.isAfter(hourAgo)) / HOUR
    }

    override def getEventStatisticByName(name: String): Double = {
        lastHourRpm(events.getOrElse(name, List()))
    }

    override def getAllEventStatistic: Map[String, Double] = {
        events.view.mapValues(value => lastHourRpm(value)).toMap
    }

    override def printStatistic(): Unit = {
        for ((name, stats) <- getAllEventStatistic)
            System.out.println(String.format("%s:   %f rpm", name, stats))
    }
}
