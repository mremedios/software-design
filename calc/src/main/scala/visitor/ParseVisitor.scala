package visitor

import token._

import java.util
import scala.jdk.CollectionConverters.ListHasAsScala

class ParseVisitor extends TokenVisitor {
    private val output = new util.ArrayList[Token]
    private val stack = new util.Stack[Token]

    def parse(tokens: List[Token]): List[Token] = {
        output.clear()
        stack.clear()

        tokens.foreach(_.accept(this))

        while (!stack.isEmpty) {
            val el = stack.pop()
            el match {
                case BinaryOpToken(_) => output.add(el)
                case _ => throw InvalidExpression("")
            }
        }
        output.asScala.toList
    }

    override def visit(token: NumberToken): Unit = {
        output.add(token)
    }

    override def visit(token: BraceToken): Unit = {
        token match {
            case x@BraceToken(Open) => stack.push(x)
            case BraceToken(Close) =>
                while (!stack.isEmpty && stack.peek() != BraceToken(Open)) {
                    output.add(stack.pop())
                }
                if (!stack.isEmpty) {
                    stack.pop()
                } else {
                    throw InvalidExpression("Invalid brackets")
                }
        }
    }

    override def visit(token: BinaryOpToken): Unit = {
        var continue = true
        while (!stack.isEmpty && continue) {
            stack.peek() match {
                case BinaryOpToken(op) if op.priority >= token.op.priority =>
                    output.add(stack.pop())
                case _ => continue = false
            }
        }
        stack.add(token)
    }
}

case class InvalidExpression(msg: String) extends RuntimeException(msg)