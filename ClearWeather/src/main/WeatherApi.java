package main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;
import org.json.*;


public class WeatherApi {

    private static WeatherApi instance=null;
    private final String URL = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";
    private String cityId = "750516";
    private String lang="tr";
    private String key= "7e4c5ae89e548854fc5b484ff72139b9";

    HttpRequest request;
    final HttpClient client = HttpClient.newBuilder().build();

    private WeatherApi(){}

    public String fetchData() {
        try {

            request = HttpRequest.newBuilder().uri(new URI(URL)).GET().build();
            var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            return response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "--";

    }
    public static WeatherApi getInstance(){
        if(instance!=null)
            return instance;
        instance = new WeatherApi();
        return instance;
    }
    void parseData(){
        TodaysForecast todaysForecast = new TodaysForecast();
        String data = fetchData();
        JSONObject parsedJson = new JSONObject(data);
        JSONObject parsedWeather = parsedJson.getJSONArray("weather").getJSONObject(0);
        todaysForecast.setCondition(parsedWeather.getString("main"));
    }

    public static void main(String[] args) {
        var api = WeatherApi.getInstance();
        System.out.println(api.fetchData());
    }

}
