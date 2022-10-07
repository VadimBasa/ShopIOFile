import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientLog {

    protected final String[] products;
    public int[] marcetProduct;
    protected final int[] prices;
    protected List<String[]> history = new ArrayList<>();


    ClientLog(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.marcetProduct = new int[products.length];
    }

    public void log(int productNum, int amount) {// метод добавления всех действий пользователя в историю
        history.add(new String[]{String.valueOf(productNum), String.valueOf(amount)});
    }

    public void saveJson(File jsonFile) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFile))) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            out.println(json);
        }
    }

    public static Basket loadJson(File jsonFile) throws IOException {
        try (Scanner scanner = new Scanner(jsonFile)) {
            String json = scanner.nextLine();
            Gson gson = new Gson();
            return gson.fromJson(json, Basket.class);
        }
    }

    public void exportAsCSV(File csvFile) throws IOException { //метод сохранения журнала действий в файл log.csv

            try (FileWriter writer = new FileWriter(csvFile, false)) {
                   writer.write("productNum, amount\n");
                for (String[] line : history) {
                    writer.write(line[0] + "," + line[1] + "\n");
                }
                }
            }
        }


