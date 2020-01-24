package main;

import javafx.util.Pair;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class YahooWeatherApi {
    final String appId = "PCYUZa44";
    final String consumerKey = "dj0yJmk9djkxYW41TnB2STdpJmQ9WVdrOVVFTlpWVnBoTkRRbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PWMz";
    final String consumerSecret = "f094263f859396b7fdc0fb5a385f07815454cff0";


    private final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";
    private String unit = "tr";
    private String city = "7e4c5ae89e548854fc5b484ff72139b9";



    private String createAuth() throws UnsupportedEncodingException {
        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");

        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        parameters.add("location=" + URLEncoder.encode("bolu,tr", "UTF-8"));
        parameters.add("u=" + URLEncoder.encode("c", "UTF-8"));
        parameters.add("format=json");
        Collections.sort(parameters);
        StringBuffer parametersList = new StringBuffer();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
        }
        String signatureString = "GET&" +
                URLEncoder.encode(url, "UTF-8") + "&" +
                URLEncoder.encode(parametersList.toString(), "UTF-8");

        String signature = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (Exception e) {
            System.err.println("Unable to append signature");
            System.exit(0);
        }

        String authorizationLine = "OAuth " +
                "oauth_consumer_key=\"" + consumerKey + "\", " +
                "oauth_nonce=\"" + oauthNonce + "\", " +
                "oauth_timestamp=\"" + timestamp + "\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_signature=\"" + signature + "\", " +
                "oauth_version=\"1.0\"";

        return authorizationLine;

    }

    private String fetchData() {
        try {

            final HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().
                    uri(URI.create(url + "?location=bolu,tr&u=c&format=json"))
                    .header("Authorization", createAuth())
                    .header("X-Yahoo-App-Id", appId)
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return response.body();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pair<TodaysForecast, DayForecast[]> getForecast() {
        var fetchJson = fetchData();
        if (fetchJson == null) {
            System.out.println("fetched null");
            return null;
        }
        JSONObject obj = new JSONObject(fetchJson);
        var loc = obj.getJSONObject("location");
        var currentObservation = obj.getJSONObject("current_observation");
        var wind = currentObservation.getJSONObject("wind");
        var condition = currentObservation.getJSONObject("condition");
        var atmosphere = currentObservation.getJSONObject("atmosphere");
        TodaysForecast todaysForecast = new TodaysForecast();
        todaysForecast.setCity(loc.getString("city"));
        todaysForecast.setCountry(loc.getString("country"));

        todaysForecast.setCondition(condition.getString("text"));
        todaysForecast.setTemperature(String.valueOf(condition.getInt("temperature")));
        todaysForecast.setWeatherCode(condition.getInt("code"));

        todaysForecast.setHumidity(String.valueOf(atmosphere.getInt("humidity")));
        todaysForecast.setWindDegree(wind.getInt("direction"));
        todaysForecast.setWindSpeed(String.valueOf(wind.getFloat("speed")));
        todaysForecast.setPressure(String.valueOf(atmosphere.getFloat("pressure")));

        var forecasts = obj.getJSONArray("forecasts");
        var weeklyForecast = new DayForecast[6];

        for (int i = 0; i < weeklyForecast.length; i++) {
            weeklyForecast[i] = new DayForecast();
            JSONObject dayJson = forecasts.getJSONObject(i+1);
            weeklyForecast[i].setDay(dayJson.getString("day"));
            weeklyForecast[i].setLowTemp(dayJson.getInt("low"));
            weeklyForecast[i].setHighTemp(dayJson.getInt("high"));
            weeklyForecast[i].setWeatherCode(dayJson.getInt("code"));
            //System.out.println(weeklyForecast[i]);
        }

        return new Pair<>(todaysForecast, weeklyForecast);
    }


}
