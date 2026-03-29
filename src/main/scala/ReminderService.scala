import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.`type`.PhoneNumber

import scala.util.Try
import scala.util.Success
import scala.util.Failure

class ReminderService(config: TwilioConfig) {
  Twilio.init(config.accountSid, config.authToken)

  def sendReminder(): Either[String, String] = {
    Try {
      config.toNumber.map {toNumber =>
        Message.creator(
          new PhoneNumber(toNumber),
          new PhoneNumber(config.fromNumber),
          "Drink Water, my sweet love!"
        ).create()
      }
    } match {
      case Success(message) => Right(s"Sent message SID: ${message.map(_.getSid).mkString(" ")}")
      case Failure(exception) => Left(s"Failed to send reminder: ${exception.getMessage}")
    }
  }
}