import org.junit.Before
import java.time.Instant
import java.time.temporal.ChronoUnit

class EventStatisticSuite extends munit.FunSuite {
    private var clock = new TestClock(Instant.now)
    private var eventStatistic = new EventStatisticImpl(clock)

    @Before
    def init(): Unit = {
        clock = new TestClock(Instant.now)
        eventStatistic = new EventStatisticImpl(clock)
    }

    test("Non existing name") {
        assertEquals(eventStatistic.getEventStatisticByName("Event"), 0.0d)
    }


    test("Stat by name") {
        eventStatistic.incEvent("Event")
        eventStatistic.incEvent("Event")
        eventStatistic.incEvent("Event2")

        assertEquals(eventStatistic.getEventStatisticByName("Event"), 2.0 / 60)
        assertEquals(eventStatistic.getEventStatisticByName("Event2"), 1.0 / 60)
    }

    test("Outdated events") {
        eventStatistic.incEvent("Event")
        clock.plus(1, ChronoUnit.HOURS)

        assertEquals(eventStatistic.getEventStatisticByName("Event"), 0.0)
    }

    test("All stat") {
        eventStatistic.incEvent("Event")
        clock.plus(40, ChronoUnit.MINUTES)

        eventStatistic.incEvent("Event2")
        eventStatistic.incEvent("Event2")
        clock.plus(20, ChronoUnit.MINUTES)

        eventStatistic.incEvent("Event3")

        val stats = eventStatistic.getAllEventStatistic
        assertEquals(stats.get("Event"), Some(0.0))
        assertEquals(stats.get("Event2"), Some(2.0 / 60))
        assertEquals(stats.get("Event3"), Some(1.0 / 60))
    }
}
