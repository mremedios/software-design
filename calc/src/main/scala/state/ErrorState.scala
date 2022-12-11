package state

import token.Token
import tokenizer.Tokenizer

case class ErrorState() extends State {
    override def parseToken(tokenizer: Tokenizer): Option[Token] = None

    override def nextState(context: Tokenizer): Unit = {}
}
