import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко", "Яйца", "Колбаса"};
        int[] prices = {100, 200, 300, 150, 400};
        Basket b01 = new Basket(prices, products);
        String binFileBasket = "basket.bin";
        File basketBin = new File("basket.bin");
        if (Files.exists(Path.of(String.valueOf(binFileBasket)))) {
            System.out.println("У Вас есть корзина с прошлого сеанса");
            b01 = Basket.loadFromBinFile(basketBin);
            b01.printCart();
        } else {
            try {
                if (basketBin.createNewFile()) {
                    System.out.println("Создана новая корзина");
                }
            } catch (IOException e) {
                System.out.println("Папка " + binFileBasket + " отсутствует.");
                throw new RuntimeException(e);
            }
        }

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }
        while (true) {
            int productNumber = 0;
            int productCount = 0;
            System.out.println("Выберите товар и количество или введите `end`");
            String inputString = scanner.nextLine(); // Считываем номер операции
            if (inputString.equals("end")) {
                b01.printCart();
                b01.saveBin(basketBin);
                System.out.println("Данные корзины сохранены в basket.bin");
                break;
            }
            String[] inputProduct = inputString.split(" ");
            productNumber = Integer.parseInt(inputProduct[0]) - 1;
            productCount = Integer.parseInt(inputProduct[1]);
            b01.addToCart(productNumber, productCount);
            b01.printCart();
        }
    }

}
