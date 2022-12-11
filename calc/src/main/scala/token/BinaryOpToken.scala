package token

import visitor.TokenVisitor

case class BinaryOpToken(op: BiOpType) extends Token {

    override def accept(visitor: TokenVisitor): Unit = visitor.visit(this)

    override def show: String = op.show
}

trait BiOpType {
    val priority: Int
    def evaluate(x: Int, y: Int): Int
    def show: String
}

object Add extends BiOpType {
    val priority = 1
    def evaluate(x: Int, y: Int): Int = x + y
    def show: String = "+"
}

object Sub extends BiOpType {
    val priority = 1
    def evaluate(x: Int, y: Int): Int = x - y
    def show: String = "-"
}

object Mul extends BiOpType {
    val priority = 2
    def evaluate(x: Int, y: Int): Int = x * y
    def show: String = "*"
}

object Div extends BiOpType {
    val priority = 2
    def evaluate(x: Int, y: Int): Int = x / y
    def show: String = "/"
}