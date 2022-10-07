import java.io.File;
import java.io.IOException;
import java.util.Scanner;
//import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

class Main {

    public static void main(String[] args) throws IOException, XPathExpressionException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко", "Яйца", "Колбаса"};
        int[] prices = {100, 200, 300, 150, 400};
        Basket pleyShop = new Basket(prices, products);
        ClientLog clientLog = new ClientLog(prices, products);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("shop.xml"));

            XPath xPath = XPathFactory.newInstance().newXPath();
            String loadFileName = xPath
                    .compile("config/load/fileName")
                    .evaluate(doc);
            boolean loadFileEnabled = Boolean.parseBoolean(xPath
                    .compile("config/load/enabled")
                    .evaluate(doc));
            boolean saveFileEnabled = Boolean.parseBoolean(xPath
                    .compile("config/save/enabled")
                    .evaluate(doc));
            String saveFileName = xPath
                    .compile("config/save/fileName")
                    .evaluate(doc);
            boolean logFileEnabled = Boolean.parseBoolean(xPath
                    .compile("config/log/enabled")
                    .evaluate(doc));
            String logFileName = xPath
                    .compile("config/log/fileName")
                    .evaluate(doc);
            String textFileBasket = "basket.txt";
            File basketText = new File("basket.txt");
            String jsonFileBasket = "basket.json";
            File basketJsonFile = new File("basket.json");
            //File txtFile = new File("log.csv");

            //JSONObject basketJson = new JSONObject();//("basket.json");
            File fileJson = new File(loadFileName);

            if (loadFileEnabled) {
                if (basketJsonFile.exists()) {
                    clientLog.loadJson(basketJsonFile);
                    System.out.println("Файл json загружен");
                    pleyShop.printCart();
                }
            } else if (!loadFileEnabled) {
                System.out.println("Загружать корзину с прошлого сеанса не требуется");
            } else {
                try {
                    if (fileJson.createNewFile()) {
                        System.out.println("Файл basket.jason создан");
                    }
                } catch (IOException e) {
                    System.out.println("Папка " + jsonFileBasket + " отсутствует.");
                    throw new RuntimeException(e);
                }
            }
            if (basketText.exists()) {
                System.out.println("У Вас есть корзина с прошлого сеанса в формате txt");
                pleyShop.loadFromTextFile(basketText);
                //System.out.println(;
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
                    if (saveFileEnabled) {
                        clientLog.saveJson(new File(saveFileName));
                    }
                    pleyShop.printCart();

                    if (logFileEnabled) {

                        clientLog.exportAsCSV(new File(logFileName));

                        System.out.println("Журнал действий пользователя сохранен в client.csv");
                    }
                    break;
                }

                String[] inputProduct = inputString.split(" ");
                productNumber = Integer.parseInt(inputProduct[0]) - 1;
                productCount = Integer.parseInt(inputProduct[1]);
                pleyShop.addToCart(productNumber, productCount);
                pleyShop.saveTxt(new File(textFileBasket));//new File(textFileBasket)
                clientLog.log(productNumber, productCount);
                pleyShop.printCart();
            }
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
}

