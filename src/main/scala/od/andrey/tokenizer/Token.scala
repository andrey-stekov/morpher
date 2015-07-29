package od.andrey.tokenizer

/**
 * Created by andrey on 29.07.2015.
 */
object TokenType extends Enumeration {
  class TokenTypeVal extends Val
  val RuWord, Number, Unknown = new TokenTypeVal
}

case class Token(s: String, tokenType: TokenType.TokenTypeVal)