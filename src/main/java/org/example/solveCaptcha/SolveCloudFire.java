package org.example.solveCaptcha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SolveCloudFire {


    private static final String API_KEY = "2277c94a92955f560246de5a9ce9e16e";


    static String solveCaptcha() throws MalformedURLException {
        StringBuilder response = new StringBuilder();

        URL url = new URL("https://api.dashboard.proxy.market/dev-api/list/" + API_KEY);

        // Создаем объект HttpURLConnection и настраиваем его
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
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

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response.toString();
    }
}
