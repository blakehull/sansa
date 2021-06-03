package io.sansa.ops

import io.circe.{Decoder, Encoder}
import io.circe.parser.decode
import io.circe.generic.auto._
import io.circe.syntax._
import cats.implicits._

object Transformer {
	implicit class JSONOps[D <: Sansa](n: Decoder[D])(implicit encoder: Encoder[D], decoder: Decoder[D]){
		object Input {
			def getCaseClass(e: String): Option[D] = decode[D](e)(n).toOption
			def getJsonString: String => Option[String] = _fromString andThen _toJson
			private def _toJson: Option[D] => Option[String] = e => if(e.isDefined) Option(e.get.asJson.noSpaces) else None
			private def _fromString: String => Option[D] = e => getCaseClass(e)
		}

		object Output {
			def getCaseClass(e: String): Option[D] = decode[D](e)(decoder).toOption
		}
	}
}
