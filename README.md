## Sansa

This basic package just provides an implicit class which allows you to use a created decoder
directly to manage strings.

Current functionality supports three stories:

1) You have a JSON string you'd like to make some case class D
2) You have a case class D you'd like to turn into a JSON string
3) You have a JSON string of case class D that you'd like to turn back into case class D

When you create a decoder, you can call from that decoder directly

```myDecoder.Input.getCaseClass(string)```

this makes it nice when mapping over a full dataset, making your code look much simpler

```textDataset.flatMap(myDecoder.Input.getCaseClass)``` will return a `Dataset[D]` 

If you want to turn a string directly into another JSON string, by passing the case class step,
you'd call

```myDecoder.Input.getJsonString(string)```

and when you have that _new_ JSON string representing case class `D` then you can call `myDecoder.Output.getCaseClass(newString)` and you
will find that you are now back in the case class `D` from your `getJsonString` value from `Input`