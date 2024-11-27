public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        int ans = parser.evaluate("-(9 * 3) / (5 + 4)");
        System.out.println(ans);
    }
}
