package visitor

import token.{BinaryOpToken, BraceToken, NumberToken, Token}

import scala.collection.mutable

class PrintVisitor extends TokenVisitor {
    val builder = new mutable.StringBuilder()

    def print(tokens: List[Token]): String = {
        builder.clear()
        tokens.foreach(_.accept(this))
        builder.toString().trim
    }

    override def visit(token: NumberToken): Unit = {
        add(token.show)
    }

    override def visit(token: BraceToken): Unit = {
        add(token.show)
    }

    override def visit(token: BinaryOpToken): Unit = {
        add(token.show)
    }

    private def add(s: String): Unit = {
        builder.append(s).append(" ")
    }
}
