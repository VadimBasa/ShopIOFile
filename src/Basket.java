import java.io.*;

public class Basket implements Serializable {
    protected final int[] prices;
    protected final String[] products;
    public int[] marcetProduct;
    private int sumProducts = 0;

    private static final long serialVersionUID = 1L;

    Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.marcetProduct = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {//метод добавления штук в корзину
        sumProducts += prices[productNum] * amount;
        marcetProduct[productNum] += amount; // создаю массив корзины и добавляю в ячеку количество товара
    }

    public void printCart() {// метод вывода корзины на экран
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            if (marcetProduct[i] > 0) {
                int sumProduct = marcetProduct[i] * prices[i];
                System.out.println(products[i] + " " + marcetProduct[i] + " шт " + prices[i] + " руб/шт " + sumProduct + " руб в сумме");
            }
        }
        System.out.println("Итого: " + sumProducts + " руб");
    }

    //сериализация
    protected static void saveBin(File file, Basket b01) throws IOException {//метод сохранения файла в бинарном формате.

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(b01);// запишем экземпляр корзины в файл

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {//метод загрузки корзины из бинарного файла
        try (ObjectInputStream loadBinFile = new ObjectInputStream(new FileInputStream(file))) {
            Basket b01 = (Basket) loadBinFile.readObject();
            loadBinFile.close();
            System.out.println("Файл Basket.bin создан методом десериализации");
            return b01;
        }
    }
}

