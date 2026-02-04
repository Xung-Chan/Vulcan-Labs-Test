class Main {
    public static void main(String[] args) {
        String input = "yxofdq";
        input.chars().map(t -> {
            int a = t - 3;
            return (char) a;
        }).forEach(t -> System.out.print((char) t));
    }
}