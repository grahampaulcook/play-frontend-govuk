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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package accordion

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.CheckboxItem
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class Section(
  headingContent: Content = Empty,
  summaryContent: Content = Empty,
  content: Content        = Empty,
  expanded: Boolean       = false
)

object Section extends JsonDefaultValueFormatter[Section] {

  override def defaultObject: Section = Section()

  override def defaultReads: Reads[Section] =
    (
      readsHtmlOrText((__ \ "heading" \ "html"), (__ \ "heading" \ "text")) and
        readsHtmlOrText((__ \ "summary" \ "html"), (__ \ "summary" \ "text")) and
        readsHtmlOrText((__ \ "content" \ "html"), (__ \ "content" \ "text")) and
        (__ \ "expanded").read[Boolean]
    )(Section.apply _)

  override implicit def jsonWrites: OWrites[Section] = new OWrites[Section] {
    override def writes(section: Section): JsObject = Json.obj(
      "heading"  -> writesContent().writes(section.headingContent),
      "summary"  -> writesContent().writes(section.summaryContent),
      "content"  -> writesContent().writes(section.content),
      "expanded" -> section.expanded
    )
  }

  def sectionSequenceReads: Reads[Seq[Section]] = new Reads[Seq[Section]] {
    override def reads(json: JsValue): JsResult[Seq[Section]] =
      (json \ "items").validate[Seq[JsObject]] match {
        case JsSuccess(values, _) =>
          val maybeItems = values.map {
            _.asOpt[Section]
          }
          JsSuccess(maybeItems.flatten)
        case error: JsError => {
          println("ERROR parsing as Seq[JsObject]")
          error
        }
      }
  }
}
