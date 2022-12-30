import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.TemporalUnit

class TestClock(var now: Instant) extends Clock {

    override def instant: Instant = now

    def setNow(now: Instant): Unit = {
        this.now = now
    }

    def plus(amountToAdd: Long, unit: TemporalUnit): Unit = {
        setNow(now.plus(amountToAdd, unit))
    }

    override def getZone: ZoneId = throw new UnsupportedOperationException()

    override def withZone(zoneId: ZoneId) = throw new UnsupportedOperationException
}
