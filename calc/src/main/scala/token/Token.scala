package token

import visitor.TokenVisitor


trait Token extends {
  def accept(visitor: TokenVisitor): Unit

  def show: String
}
