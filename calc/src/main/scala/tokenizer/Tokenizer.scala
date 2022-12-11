package tokenizer

import state.{EndState, ErrorState, NumberState, StartState, State}
import token.Token

import scala.collection.mutable

object Tokenizer {
    def parse(input: String): List[Token] = {
        new Tokenizer(input).parse()
    }
}

sealed class Tokenizer(input: String) {
    private var curInd = 0
    var state: State = StartState()
    val tokens: mutable.ListBuffer[Token] = mutable.ListBuffer()

    def parse(): List[Token] = {
        while (state != EndState() && state != ErrorState()) {
            state.nextState(this)
            state.parseToken(this).foreach(tokens.append)
        }
        tokens.toList
    }

    def eof: Boolean = curInd >= input.length

    def currentChar: Char = input.charAt(curInd)

    def nextChar(): Unit = curInd += 1

}