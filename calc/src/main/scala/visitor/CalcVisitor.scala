package visitor

import token.{BinaryOpToken, BraceToken, NumberToken, Token}

import java.util

class CalcVisitor extends TokenVisitor {
    private val stack = new util.Stack[Int]

    def calculate(tokens: List[Token]): Int = {
        stack.clear()
        if (tokens.isEmpty) 0
        else {
            tokens.foreach(_.accept(this))
            if (stack.size != 1) throw CalcException()
            stack.pop()
        }
    }

    override def visit(token: NumberToken): Unit = {
        stack.push(token.value)
    }

    override def visit(token: BraceToken): Unit = {
        throw CalcException()
    }

    override def visit(token: BinaryOpToken): Unit = {
        if (stack.size < 2) throw CalcException()
        val x = stack.pop()
        val y = stack.pop()
        stack.push(token.op.evaluate(y, x))
    }
}

case class CalcException() extends RuntimeException