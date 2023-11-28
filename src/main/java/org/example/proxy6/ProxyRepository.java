package org.example.proxy6;

import org.example.entities.Proxy;
import org.example.jsonMethods.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProxyRepository {

    private final String API_KEY = "53616800dd711aa4956dd8c85d053703";



    public List<Proxy> getProxies(){
        final String address = "https://proxymania.ru/api/get_proxies/"+API_KEY+"/?extended=1";

        List<Proxy> proxies = null;
        try{
            URL url = new URL(address);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);


            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Response: " + response.toString());

            proxies = JsonParser.getProxiesFromJson(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxies;
    }

}
