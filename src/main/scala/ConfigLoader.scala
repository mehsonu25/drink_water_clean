import scala.io.Source
import java.io.File
import scala.collection.mutable
import scala.collection.mutable.Map

object ConfigLoader {
  def loadEnvFile(filePath: String = ".env"): mutable.Map[String, String] = {
    val envVars = mutable.Map[String, String]()
    
    try {
      if (new File(filePath).exists()) {
        Source.fromFile(filePath).getLines().foreach { line =>
          if (line.trim.nonEmpty && !line.startsWith("#")) {
            val parts = line.split("=", 2)
            if (parts.length == 2) {
              envVars += (parts(0).trim -> parts(1).trim)
            }
          }
        }
      }
    } catch {
      case e: Exception =>
        println(s"Warning: Could not load .env file: ${e.getMessage}")
    }
    
    envVars
  }
  
  def getEnvVar(name: String, default: String = ""): String = {
    sys.env.get(name) 
      .orElse(loadEnvFile().get(name)) 
      .getOrElse(default)
  }
}