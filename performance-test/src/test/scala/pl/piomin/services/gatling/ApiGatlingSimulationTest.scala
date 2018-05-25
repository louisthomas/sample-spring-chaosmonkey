package pl.piomin.services.gatling

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import java.util.Date
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scala.util.Random

class ApiGatlingSimulationTest extends Simulation {

  val scn = scenario("AddAndFindOrders").repeat(5, "n") {
        exec(
          http("AddOrder-API")
            .post("https://super-order-service.cfapps.io/orders")
            .header("Content-Type", "application/json")
            .body(StringBody("""{"productId":""" + Random.nextInt(20) + ""","customerId":""" + Random.nextInt(20) + ""","productsCount":1,"price":1000,"status":"NEW"}"""))
            .check(status.is(200),  jsonPath("$.id").saveAs("orderId"))
        ).pause(Duration.apply(5, TimeUnit.MILLISECONDS))    
        .
        exec(
          http("GetOrder-API")
            .get("https://super-order-service.cfapps.io/${orderId}")
            .check(status.is(200))
        )   
  }
  
  setUp(scn.inject(atOnceUsers(5))).maxDuration(FiniteDuration.apply(10, "minutes"))
  
}