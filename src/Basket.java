import java.io.*;

//public class Basket implements Serializable {
public class Basket {
    protected final int[] prices;
    protected final String[] products;
    private static int[] marcetProduct;
    private int sumProducts = 0;

    //private static final long serialVersionUID = 1L;

    Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
    }

    public static void setMarcetProduct(int productsLength) {
        marcetProduct = new int[productsLength];
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

    public void saveTxt(File textFile) throws IOException {//метод сохранения корзины в текстовый файл

        try (PrintWriter out = new PrintWriter(new FileWriter(textFile))) {

            for (int e : marcetProduct)
                out.print(e + " ");
        } catch (IOException e) {
            System.out.println("Файл или путь C:\\Users\\User\\IdeaProjects\\ShopIOFile/basket.txt отсутствует");
            throw new RuntimeException(e);
        }
    }

    protected static void loadFromTextFile(File textFile) throws IOException {//метод восстановления объекта корзины из текстового файла
        try (BufferedReader in = new BufferedReader(new FileReader(textFile))) {
            String[] itemSplit = in.readLine().split(" ");
            for (int i = 0; i < itemSplit.length; i++) {
                marcetProduct[i] = Integer.parseInt(itemSplit[i]);
            }
        }
    }

    protected void saveBin(File file) throws IOException {//метод сохранения корзины в файл в бинарном формате.
        try (FileOutputStream fos = new FileOutputStream(file);// откроем выходной поток для записи в файл
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);// запишем экземпляр корзины в файл
            System.out.println("Файл Basket.bin создан.");
        }
        //outBin.close(); Не требуется закрывать, ресурс т.к. он закрывается автоматически при использовании try .... catch
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {//метод загрузки корзины из бинарного файла
        try (ObjectInputStream loadBinFile = new ObjectInputStream(new FileInputStream(file))) {
            Basket baskets = (Basket) loadBinFile.readObject();
            loadBinFile.close();
            System.out.println("Файл Basket.bin прочитан методом десериализации");
            return baskets;
        }
    }
}

