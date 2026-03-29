case class TwilioConfig(
  accountSid: String,
  authToken: String,
  fromNumber: String,
  toNumber: List[String]
)

case class AppConfig(
  twilio: TwilioConfig,
  reminderIntervalHours: Long = 1,
  initialDelayHours: Long = 0
)