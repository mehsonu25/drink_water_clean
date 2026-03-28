import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.`type`.PhoneNumber

class ReminderService(config: TwilioConfig) {
  Twilio.init(config.accountSid, config.authToken)

  def sendReminder(): Either[String, String] = {
    try {
      val message = Message.creator(
        new PhoneNumber(config.toNumber),
        new PhoneNumber(config.fromNumber),
        "Drink water, my love."
      ).create()
      Right(s"Sent message SID: ${message.getSid}")
    } catch {
      case e: Exception => Left(s"Failed to send reminder: ${e.getMessage}")
    }
  }
}