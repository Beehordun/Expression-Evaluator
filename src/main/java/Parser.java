public class Parser {

    private Tokenizer tokenizer;


    public int evaluate(String input) {
        tokenizer = new Tokenizer(input);
        return evaluateAdditiveExpression();
    }

    /*
    E → T ( + T )*  ---> Left Associative Additive Production
    E → T ( - T )*  ---> Left Associative Subtraction Production
    T → F × (F)*  ---> Multiplication Production
    F → n | ( E )  ----> Number production
    */
    private int evaluateAdditiveExpression() {
        int left = evaluateMultiplicativeExpression();
        while (!tokenizer.isEndOfInput() &&
                (tokenizer.nextToken().type() == TokenType.Add ||
                        tokenizer.nextToken().type() == TokenType.Subtract)
        ) {
            Token nextToken = tokenizer.nextToken();
            switch (nextToken.type()) {
                case Add -> {
                    tokenizer.consume();
                    left = left + evaluateMultiplicativeExpression();
                }
                case Subtract -> {
                    tokenizer.consume();
                    left = left - evaluateMultiplicativeExpression();
                }
            }
        }
        return left;
    }

    private int evaluateMultiplicativeExpression() {
        int left = evaluateNumericExpression();
        while (!tokenizer.isEndOfInput() &&
                (tokenizer.nextToken().type() == TokenType.Multiplication ||
                        tokenizer.nextToken().type() == TokenType.Division)
        ) {
            Token nextToken = tokenizer.nextToken();
            switch (nextToken.type()) {
                case Multiplication -> {
                    tokenizer.consume();
                    left = left * evaluateNumericExpression();
                }

                case Division -> {
                    tokenizer.consume();
                    left = left / evaluateNumericExpression();
                }
            }
        }
        return left;
    }

    private int evaluateNumericExpression() {
        Token nextToken = tokenizer.nextToken();
        if (nextToken == null) return 0;
        switch (nextToken.type()) {
            case NumericLiteral -> {
                String consumedValue = tokenizer.consume();
                return Integer.parseInt(consumedValue);
            }
            case Subtract -> {
                tokenizer.consume();
                return - 1 * evaluateNumericExpression();
            }
            case OpenBracket -> {
                tokenizer.consume("(");
                int res = evaluateAdditiveExpression();
                tokenizer.consume(")");
                return res;
            }
        }
        throw new SyntaxException("Multiplication Invalid syntax ");
    }
}
