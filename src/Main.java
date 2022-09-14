import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко", "Яйца", "Колбаса"};
        int[] prices = {100, 200, 300, 150, 400};
        Basket pleyShop = new Basket(prices, products);
        Basket.setMarcetProduct(products.length);
        String textFileBasket = "C:\\Users\\User\\IdeaProjects\\ShopIOFile/basket.txt";
        File basketText = new File("basket.txt");
        if ((basketText).exists()) {
            System.out.println("У Вас есть корзина с прошлого сеанса");
            pleyShop.loadFromTextFile(basketText);
        } else {
            try {
                if (basketText.createNewFile()) {
                    System.out.println("Создана новая корзина");
                }
            } catch (IOException e) {
                System.out.println("Папка " + textFileBasket + " отсутствует.");
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
                pleyShop.addToCart(productNumber, productCount);
                break;
            }
            String[] inputProduct = inputString.split(" ");
            productNumber = Integer.parseInt(inputProduct[0]) - 1;
            productCount = Integer.parseInt(inputProduct[1]);
            pleyShop.addToCart(productNumber, productCount);
            pleyShop.saveTxt(new File(textFileBasket));//
            //pleyShop.saveBin(new File(textFileBasket));//
        }
        pleyShop.printCart();
        //pleyShop.loadFromBinFile(new File(textFileBasket));
        //pleyShop.printCart();

    }

}