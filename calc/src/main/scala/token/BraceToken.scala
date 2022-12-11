package token

import visitor.TokenVisitor

case class BraceToken(braceType: BraceType) extends Token {

    override def accept(visitor: TokenVisitor): Unit = visitor.visit(this)

    override def show: String = braceType.show
}

trait BraceType {
    def show: String
}

object Open extends BraceType {
    override def show: String = "("
}

object Close extends BraceType {
    override def show: String = ")"
}