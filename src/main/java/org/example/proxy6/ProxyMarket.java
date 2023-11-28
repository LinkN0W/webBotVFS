package org.example.proxy6;

import org.example.entities.Proxy;
import org.example.jsonMethods.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProxyMarket {

    private static final String API_KEY = "5f1dba63847471c51d3371c30d5c75f7";

    public static List<Proxy> getProxy() {
        List<Proxy> proxies = null;

        try {
            // Создаем URL-адрес для запроса
            URL url = new URL("https://api.dashboard.proxy.market/dev-api/list/" + API_KEY);

            // Создаем объект HttpURLConnection и настраиваем его
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String body = "{\n" +
                    "  \"type\": \"ipv4\",\n" +
                    "  \"proxy_type\": \"server\",\n" +
                    "  \"page\": 1,\n" +
                    "  \"page_size\": 30,\n" +
                    "  \"sort\": 1\n" +
                    "}";
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(body.length()));
            connection.setRequestProperty("accept", "application/json");

            // Создаем тело запроса


            // Записываем тело запроса в поток вывода
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();

            // Считываем ответ от сервера
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Выводим ответ от сервера
            System.out.println(response.toString());

            proxies = JsonParser.getProxiesMarketFromJson(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxies;
    }

}
