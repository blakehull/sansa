package io.sansa.ops

import org.scalatest.funspec.AnyFunSpec
import io.circe.Decoder
import io.sansa.ops.Transformer.JSONOps
import io.circe.generic.auto._

class TransformerTest extends AnyFunSpec {

	case class TestSchema(id: String,
	                            guid: String,
	                            gender: String,
	                            email: String,
	                            friendCount: Int,
	                            friendNames: Seq[String]
	                           ) extends Sansa

	case class Friends(id: Int, name: String)

	lazy val idDecoder: Decoder[String] = Decoder.instance[String](_.get[String]("_id"))
	lazy val guidDecoder: Decoder[String] = Decoder.instance[String](_.get[String]("guid"))
	lazy val genderDecoder: Decoder[String] = Decoder.instance[String](_.get[String]("gender"))
	lazy val emailDecoder: Decoder[String] = Decoder.instance[String](_.get[String]("email"))
	lazy val friendsDecoder: Decoder[Seq[Friends]] = Decoder.instance[Seq[Friends]](_.get[Seq[Friends]]("friends"))

	lazy val myCustomDecoder: Decoder[TestSchema] = for {
		id <- idDecoder
		guid <- guidDecoder
		gender <- genderDecoder
		email <- emailDecoder
		friends <- friendsDecoder
	} yield {
		TestSchema(id, guid, gender, email, friends.size, friends.map(_.name))
	}

	describe("checks that decoder ops is working by checking if sansa"){
		val inp = "{\"_id\":\"6035e49e358333899c860b19\",\"index\":0,\"guid\":\"a22c047e-fee9-45fd-b473-e9685062279e\",\"isActive\":false,\"balance\":\"$3,852.91\",\"picture\":\"http://placehold.it/32x32\",\"age\":30,\"eyeColor\":\"green\",\"name\":\"Margret Dickson\",\"gender\":\"female\",\"company\":\"SULTRAX\",\"email\":\"margretdickson@sultrax.com\",\"phone\":\"+1 (954) 430-3188\",\"address\":\"850 Veterans Avenue, Callaghan, Hawaii, 3681\",\"about\":\"Id deserunt duis commodo eu ut culpa qui adipisicing. Fugiat duis excepteur sint dolor. Magna amet dolor do minim qui laborum pariatur amet. Sit cillum non duis duis do Lorem et nulla cupidatat esse.\\r\\n\",\"registered\":\"2014-05-20T07:31:36 +07:00\",\"latitude\":-74.431424,\"longitude\":48.008482,\"tags\":[\"irure\",\"ex\",\"eiusmod\",\"cillum\",\"laboris\",\"quis\",\"qui\"],\"friends\":[{\"id\":0,\"name\":\"Barber Simpson\"},{\"id\":1,\"name\":\"Keisha Myers\"},{\"id\":2,\"name\":\"Deidre Dickerson\"}],\"greeting\":\"Hello, Margret Dickson! You have 4 unread messages.\",\"favoriteFruit\":\"banana\"}"

		it("parses into a JSON object"){
			assert(myCustomDecoder.Input.getJsonString(inp).isDefined)
		}

		it("parses into TestSchema"){
			val testClass: TestSchema = myCustomDecoder.Input.getCaseClass(inp).get
			assert(testClass.isInstanceOf[TestSchema])
			assert(testClass.id == "6035e49e358333899c860b19")
			assert(testClass.guid == "a22c047e-fee9-45fd-b473-e9685062279e")
			assert(testClass.gender == "female")
			assert(testClass.email == "margretdickson@sultrax.com")
			assert(testClass.friendCount == 3)
			assert(testClass.friendNames == Seq("Barber Simpson" ,"Keisha Myers", "Deidre Dickerson"))
		}

		it("parses TestSchema json back into case class"){
			val testClass: String = myCustomDecoder.Input.getJsonString(inp).get
			assert(myCustomDecoder.Output.getCaseClass(testClass).isDefined)
		}

	}
}
