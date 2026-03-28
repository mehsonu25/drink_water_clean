import java.util.concurrent.{Executors, TimeUnit, CountDownLatch, ScheduledExecutorService}
import scala.util.{Try, Success, Failure}

class Scheduler(reminderService: ReminderService, config: AppConfig) {
  private val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
  private val latch = new CountDownLatch(1)

  def start(): Unit = {
    scheduler.scheduleAtFixedRate(
      () => {
        reminderService.sendReminder() match {
          case Right(success) => println(success)
          case Left(error) => println(error)
        }
      },
      config.initialDelayHours,
      config.reminderIntervalHours,
      TimeUnit.HOURS
    )
    println(s"Hourly WhatsApp water reminder started. Interval: ${config.reminderIntervalHours} hour(s)")
  }

  def awaitTermination(): Unit = {
    Try(latch.await())
  }

  def shutdown(): Unit = {
    scheduler.shutdown()
    Try(scheduler.awaitTermination(5, TimeUnit.SECONDS))
    latch.countDown()
  }
}

object Scheduler {
  def apply(reminderService: ReminderService, config: AppConfig): Scheduler = {
    new Scheduler(reminderService, config)
  }
}