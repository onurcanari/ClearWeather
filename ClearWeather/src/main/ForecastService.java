package main;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class ForecastService implements IObservable {
    public List<IObserver> listeners = new ArrayList<IObserver>();
    public TodaysForecast today;
    public DayForecast week[];

    private static ForecastService forecastService;

    private ForecastService() {
        today = new TodaysForecast();
        week = new DayForecast[6];
        for (int i = 0; i < week.length; i++) {
            week[i] = new DayForecast();
        }
    }


    @Override
    public void notifyListeners() {
        for (var listener : listeners) {
            listener.update();
        }
    }

    @Override
    public void setState() {
        var update = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                var forecast = new YahooWeatherApi().getForecast();
                today = forecast.getKey();

                for (int i = 0; i < week.length; i++)
                    week[i].updateData(forecast.getValue()[i]);

                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                notifyListeners();
            }

        };
        var t = new Thread(update);
        t.setDaemon(true);
        t.start();
    }

    public static ForecastService getInstance() {
        if (forecastService == null) {
            forecastService = new ForecastService();
        }
        return forecastService;
    }

    @Override
    public void registerObserver(IObserver observer) {
        listeners.add(observer);
    }


    @Override
    public void unregisterObserver(IObserver observer) {
        listeners.remove(observer);
    }
}
