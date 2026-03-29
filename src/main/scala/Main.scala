import scala.util.{Try, Success, Failure}

object Main extends App {
  val config = AppConfig(
    twilio = TwilioConfig(
      accountSid = ConfigLoader.getEnvVar("TWILIO_ACCOUNT_SID"),
      authToken = ConfigLoader.getEnvVar("TWILIO_AUTH_TOKEN"),
      fromNumber = ConfigLoader.getEnvVar("TWILIO_FROM_NUMBER"),
      toNumber = List(
        ConfigLoader.getEnvVar("CUST1_TWILIO_TO_NUMBER"),
        ConfigLoader.getEnvVar("CUST2_TWILIO_TO_NUMBER")
      )
    ),
    reminderIntervalHours = ConfigLoader.getEnvVar("REMINDER_INTERVAL_HOURS", "1").toLong,
    initialDelayHours = ConfigLoader.getEnvVar("INITIAL_DELAY_HOURS", "0").toLong
  )

  val reminderService = new ReminderService(config.twilio)
  val scheduler = Scheduler(reminderService, config)

  Try {
    scheduler.start()
    scheduler.awaitTermination()
  } match {
    case Success(_) => println("Application completed successfully")
    case Failure(e) => println(s"Application failed: ${e.getMessage}")
  }

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    scheduler.shutdown()
    println("Application shutdown complete")
  }))
}