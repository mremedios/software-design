import munit.FunSuite
import token._
import tokenizer.Tokenizer
import visitor.{CalcVisitor, ParseVisitor, PrintVisitor}


class CalculatorSuite extends FunSuite {

    test("Tokenizer") {
        val x: Map[String, List[Token]] = Map(
            " " -> List(),
            "  1  " -> List(NumberToken(1)),
            "+  " -> List(BinaryOpToken(Add)),
            "( 5 )" -> List(BraceToken(Open), NumberToken(5), BraceToken(Close)),
            "2 - ( 3 * 5 )" -> List(NumberToken(2), BinaryOpToken(Sub), BraceToken(Open),
                NumberToken(3), BinaryOpToken(Mul), NumberToken(5), BraceToken(Close)),
            " ( 4/)" -> List(BraceToken(Open), NumberToken(4), BinaryOpToken(Div), BraceToken(Close))
        )

        x.foreach { case (in, expected) =>
            assertEquals(Tokenizer.parse(in), expected)
        }
    }

    test("ParseVisitor correct") {
        val x: Map[String, List[Token]] = Map(
            "3 + 4" -> List(NumberToken(3),  NumberToken(4), BinaryOpToken(Add))
        )
        val parseVisitor = new ParseVisitor()
        x.foreach { case (in, expected) =>
            val tokens = Tokenizer.parse(in)
            val act = parseVisitor.parse(tokens)
            assertEquals(act, expected)
        }
    }

    test ("PrintVisitor") {
        val x: Map[String, String] = Map(
            "" -> "",
            "5" -> "5",
            "3 + 4" -> "3 4 +",
            "7 - 2 * 3" -> "7 2 3 * -",
            "(10 - 15) / 3" -> "10 15 - 3 /",
            "(1 + 2) * 4 + 3 " -> "1 2 + 4 * 3 +"
        )
        val parseVisitor = new ParseVisitor()
        val printVisitor = new PrintVisitor()
        x.foreach { case (in, expected) =>
            val tokens = Tokenizer.parse(in)
            val act = parseVisitor.parse(tokens)
            val str = printVisitor.print(act)
            assertEquals(str, expected)
        }
    }

    test("CalcVisitor") {
        val x: Map[String, Int] = Map(
            "" -> 0,
            "5" -> 5,
            "3 + 4" -> 7,
            "7 - 2 * 3" -> 1,
            "(10 - 15) / 1" -> -5,
            "(1 + 2) * 4 + 3 " -> 15
        )
        val parseVisitor = new ParseVisitor()
        val printVisitor = new CalcVisitor()
        x.foreach { case (in, expected) =>
            val tokens = Tokenizer.parse(in)
            val act = parseVisitor.parse(tokens)
            val eval = printVisitor.calculate(act)
            assertEquals(eval, expected)
        }
    }
}
