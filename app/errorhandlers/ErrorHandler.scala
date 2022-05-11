package errorhandlers

import play.api.http.HttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._

import javax.inject.Singleton
import scala.concurrent._

@Singleton
class ErrorHandler extends HttpErrorHandler {
  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful(
      exception.getMessage match {
        case "BookId not found" => NotFound("A server error occurred. " + exception.getMessage)
        case "BookId already used" => NotAcceptable("A server error occurred. " + exception.getMessage)
        case _ => InternalServerError("A server error occurred. " + exception.getMessage)}
    )
  }
}
