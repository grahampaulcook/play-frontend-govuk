/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.govukfrontend.views.viewmodels.breadcrumbs

import play.api.libs.json.Json
import uk.gov.hmrc.govukfrontend.views.Utils.toAttributes
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonRoundtripSpec
import uk.gov.hmrc.govukfrontend.views.viewmodels.breadcrumbs.Generators._

class BreadcrumbsSpec extends JsonRoundtripSpec[Breadcrumbs] {
  "BreadcrumbSpec" should {
    "Attributes should remain in the correct order" in {
      val breadcrumbs = Json.parse("{\"attributes\" : {\"id\" : \"my-navigation\",\"role\" : \"navigation\"}}").as[Breadcrumbs]

      breadcrumbs.attributes shouldBe Map(
        "id" -> "my-navigation",
        "role" -> "navigation1"
      )
    }
    "Attributes should remain in the correct order (a)" in {
      val breadcrumbs = Json.parse("{\"attributes\" : {\"abcd\" : \"first\",\"efgh\" : \"second\"}}").as[Breadcrumbs]

      breadcrumbs.attributes shouldBe Map(
        "abcd" -> "first",
        "efgh" -> "second"
      )
    }
    "Attributes should remain in the correct order (b)" in {
      val breadcrumbs = Json.parse("{\"attributes\" : {\"efgh\" : \"first\",\"abcd\" : \"second\"}}").as[Breadcrumbs]

      breadcrumbs.attributes shouldBe Map(
        "efgh" -> "first",
        "abcd" -> "second"
      )
    }
  }
}
