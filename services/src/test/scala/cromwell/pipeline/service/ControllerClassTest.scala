package cromwell.pipeline.service

import akka.event.LoggingAdapter
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import akka.http.scaladsl.settings.ConnectionPoolSettings
import akka.http.scaladsl.{ HttpExt, HttpsConnectionContext }
import org.mockito.Matchers.any
import org.mockito.Mockito.when
import org.scalatest.{ AsyncWordSpec, BeforeAndAfterAll, Matchers }
import org.scalatestplus.mockito.MockitoSugar

import scala.concurrent.{ ExecutionContext, Future }

class ControllerClassTest extends AsyncWordSpec with Matchers with MockitoSugar with BeforeAndAfterAll {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  val mockHttp: HttpExt = mock[HttpExt]
  val cromwellResponse: Response = Response(200, "", Map())

  class TestControllerClient extends ControllerClient {
    override def http: HttpExt = mockHttp
  }

  "ControllerClassTest" when {

    "get" should {
      "return OK response status" taggedAs Service in {
        val response = Future[HttpResponse](HttpResponse())
        when(
          mockHttp.singleRequest(
            any[HttpRequest],
            any[HttpsConnectionContext],
            any[ConnectionPoolSettings],
            any[LoggingAdapter]
          )
        ).thenReturn(response)

        val client = new TestControllerClient
        client.get("https://test.com/get").flatMap(_ shouldBe cromwellResponse)
      }
    }

    "post" should {
      "return OK response status" taggedAs Service in {
        val response = Future[HttpResponse](HttpResponse())
        when(
          mockHttp.singleRequest(
            any[HttpRequest],
            any[HttpsConnectionContext],
            any[ConnectionPoolSettings],
            any[LoggingAdapter]
          )
        ).thenReturn(response)

        val client = new TestControllerClient
        client.post("https://test.com/put", payload = "test payload").flatMap(_ shouldBe cromwellResponse)
      }
    }

    "put" should {
      "return OK response status" taggedAs Service in {
        val response = Future[HttpResponse](HttpResponse())
        when(
          mockHttp.singleRequest(
            any[HttpRequest],
            any[HttpsConnectionContext],
            any[ConnectionPoolSettings],
            any[LoggingAdapter]
          )
        ).thenReturn(response)

        val client = new TestControllerClient
        client.put("https://test.com/put", payload = "test payload").flatMap(_ shouldBe cromwellResponse)
      }
    }
  }
}
