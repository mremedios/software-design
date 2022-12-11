package state

import token.Token
import tokenizer.Tokenizer

trait State {
  def parseToken(context: Tokenizer): Option[Token]

  def nextState(context: Tokenizer): Unit
}

