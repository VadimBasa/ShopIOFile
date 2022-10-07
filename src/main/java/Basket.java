import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Basket implements Serializable {
    protected int[] prices;
    protected String[] products;
    protected int[] marcetProduct;
    //private int sumProducts = 0;

    private static final long serialVersionUID = 1L;

    //public int[] getMarcetProduct() {
    //    return marcetProduct;
    //}
    //public int[] setMarcetProduct(int[] marcetProduct){
    //    this.marcetProduct = marcetProduct;
    //}

    Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.marcetProduct = new int[products.length];
    }

    private Basket(int[] prices, String[] products, int[] marcetProduct) {
        this.prices = prices;
        this.products = products;
        this.marcetProduct = marcetProduct;
    }

    public void addToCart(int productNum, int amount) {//метод добавления штук в корзину
        this.marcetProduct[productNum] += amount; // создаю массив корзины и добавляю в ячеку количество товара
    }

    public void printCart() {// метод вывода корзины на экран
        System.out.println("Ваша корзина:");
        int TotalsumProducts = 0;
        for (int i = 0; i < products.length; i++) {
            if (marcetProduct[i] > 0) {
                int sumProduct = marcetProduct[i] * prices[i];
                System.out.println(products[i] + " " + marcetProduct[i] + " шт " + prices[i] + " руб/шт " + sumProduct + " руб в сумме");
            }
            TotalsumProducts += prices[i] * marcetProduct[i];
        }
        System.out.println("Итого: " + TotalsumProducts + " руб");
    }

    public void saveTxt(File textFile) throws IOException {//метод сохранения корзины в текстовый файл

        try (PrintWriter out = new PrintWriter(new FileWriter(textFile))) {

            for (String e : products) {
                out.print(e + " ");
            }
            out.println();
            for (int e : prices) {
                out.print(e + " ");
            }
            out.println();
            for (int e : marcetProduct) {
                out.print(e + " ");
            }
            out.println();
        }
    }

    public static Basket loadFromTextFile(File textFile) throws IOException {///метод восстановления объекта корзины из текстового файла

        try (Scanner scanner = new Scanner(new FileInputStream(textFile))) {
            String[] products = scanner.nextLine().trim().split(" ");
            int[] prices = Arrays.stream(scanner.nextLine().trim().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] marcetProduct = Arrays.stream(scanner.nextLine().trim().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            Basket b01 = new Basket(prices, products, marcetProduct);
            return b01;
        }
    }

    public void saveBin(File binFile) throws IOException {//метод сохранения корзины в bin файл

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(binFile))) {

            out.writeObject(this);
        }
    }

    public static Basket loadFromBinFile(File binFile) throws IOException, ClassNotFoundException {///метод восстановления объекта корзины из bin файла

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(binFile))) {

            return (Basket) in.readObject();

        }
    }

}