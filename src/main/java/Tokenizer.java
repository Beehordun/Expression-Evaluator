import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private final String input;
    private int currPos = 0;

    private final Map<String, TokenType> regexToTokenType = new LinkedHashMap<>();

    public Tokenizer(String input) {
        this.input = input;
        regexToTokenType.put("^\\(", TokenType.OpenBracket);
        regexToTokenType.put("^\\)", TokenType.CloseBracket);
        regexToTokenType.put("^\\+", TokenType.Add);
        regexToTokenType.put("^\\-", TokenType.Subtract);
        regexToTokenType.put("^\\*", TokenType.Multiplication);
        regexToTokenType.put("^/", TokenType.Division);
        regexToTokenType.put("^\\d+", TokenType.NumericLiteral);
    }

    public Token nextToken() {
        if (isEndOfInput()) return null;
        while (isWhiteSpace()) {
            currPos += 1;
        }
        String stringToMatch = input.substring(currPos);
        for (Map.Entry<String, TokenType> entry : regexToTokenType.entrySet()) {
            Matcher matcher = Pattern.compile(entry.getKey()).matcher(stringToMatch);
            if (matcher.find()) {
                String matchedString = matcher.group();
                return new Token(entry.getValue(), matchedString);
            }
        }
        throw new SyntaxException("Tokenizer Incorrect syntax");
    }

    public String consume() {
        Token nextToken = nextToken();
        if (nextToken != null) {
            currPos += nextToken.value().length();
            return nextToken.value();
        }

        throw new SyntaxException("Syntax exception");

    }

    public String consume(String token) {
        Token nextToken = nextToken();
        if (nextToken != null) {
            String nextTokenValue = nextToken.value();
            if (!nextTokenValue.equals(token)) throw new SyntaxException("Invalid token");
            currPos += nextTokenValue.length();
            return nextTokenValue;
        }
        throw new SyntaxException("Syntax exception");
    }

    public boolean isEndOfInput() {
        return currPos >= input.length();
    }

    private boolean isWhiteSpace() {
        return input.substring(currPos, currPos + 1).isBlank();
    }
}
