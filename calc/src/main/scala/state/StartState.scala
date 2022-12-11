package state

import cats.implicits.catsSyntaxOptionId
import token._
import tokenizer.Tokenizer

case class StartState() extends State {

    override def parseToken(context: Tokenizer): Option[Token] = {
        val tokenOpt = getToken(context.currentChar)
        context.nextChar()
        tokenOpt match {
            case Some(_) => context.state = StartState()
            case None => context.state = ErrorState()
        }
        tokenOpt
    }

    private def getToken(c: Char): Option[Token] = {
        c match {
            case '(' => BraceToken(Open).some
            case ')' => BraceToken(Close).some
            case '+' => BinaryOpToken(Add).some
            case '*' => BinaryOpToken(Mul).some
            case '/' => BinaryOpToken(Div).some
            case '-' => BinaryOpToken(Sub).some
            case _ => None
        }
    }

    override def nextState(context: Tokenizer): Unit = {
        while (!context.eof && context.currentChar.isWhitespace) context.nextChar()
        if (context.eof) context.state = EndState()
        else {
            val c = context.currentChar
            c match {
                case _ if c.isDigit => context.state = NumberState()
                case _ if isAvailableSymbol(c) => context.state = StartState()
                case _ => context.state = ErrorState()
            }
        }
    }

    private def isAvailableSymbol(c: Char): Boolean = {
        val availableSymbols = "+-*/()"
        availableSymbols.indexOf(c) >= 0
    }
}
