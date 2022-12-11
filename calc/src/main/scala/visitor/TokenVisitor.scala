package visitor

import token.BraceToken
import token.NumberToken
import token.BinaryOpToken


trait TokenVisitor {
  def visit(token: NumberToken): Unit

  def visit(token: BraceToken): Unit

  def visit(token: BinaryOpToken): Unit
}
