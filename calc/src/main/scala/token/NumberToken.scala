package token

import visitor.TokenVisitor

case class NumberToken(value: Int) extends Token {
  override def accept(visitor: TokenVisitor): Unit = visitor.visit(this)

  override def show: String = value.toString
}
