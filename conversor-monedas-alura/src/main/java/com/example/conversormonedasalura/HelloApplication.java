import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;


public class CurrencyConverter {
    private static final String API_KEY = "18fce159572b98f6437f581a";
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al conversor de moneda");
        System.out.print("Introduce la moneda de origen (ej. USD): ");
        String fromCurrency = scanner.nextLine().toUpperCase();
        System.out.print("Introduce la moneda de destino (ej. EUR): ");
        String toCurrency = scanner.nextLine().toUpperCase();
        System.out.print("Introduce la cantidad a convertir: ");
        double amount = scanner.nextDouble();

        double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
        if (exchangeRate != -1) {
            double result = amount * exchangeRate;
            System.out.printf("%.2f %s son %.2f %s\n", amount, fromCurrency, result, toCurrency);
        } else {
            System.out.println("No se pudo obtener la tasa de cambio.");
        }

        scanner.close();
    }

    private static double getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            URL url = new URL(API_URL + fromCurrency + "?apikey=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONObject json = new JSONObject(content.toString());
            return json.getJSONObject("rates").getDouble(toCurrency);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
