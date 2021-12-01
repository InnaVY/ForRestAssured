public class RandomCreation {
    public static String createRandomWord() {
        String field = "";
        for (int i = 0; i < 7; i++) {
            int v = 1 + (int) (Math.random() * 26);
            char c = (char) (v + (i == 0 ? 'A' : 'a') - 1);
            field += c;
        }
        return field;
    }
    public static int createRandomInt(){
        return (int) (Math.random()*10);
    }
}
