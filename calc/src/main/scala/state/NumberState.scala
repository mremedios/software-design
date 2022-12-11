package state

import token.Token
import tokenizer.Tokenizer
import token.NumberToken

import scala.collection.mutable

case class NumberState() extends State {

    override def parseToken(context: Tokenizer): Option[Token] = {
        val token: mutable.StringBuilder = new mutable.StringBuilder
        while (!context.eof && context.currentChar.isDigit) {
            token.append(context.currentChar)
            context.nextChar()
        }
        context.state = StartState()
        Some(NumberToken(token.toString.toInt))
    }

    override def nextState(context: Tokenizer): Unit = {
        if (!context.currentChar.isDigit) context.state = StartState()
    }
}
