package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TodaysForecastController implements IObserver {

    @FXML
    private GridPane weeklyForecast;

    @FXML
    private Button updateButton;

    @FXML
    private Label lastUpdateTime;

    @FXML
    private ImageView weatherTypeImg;

    @FXML
    private Label locationText;

    @FXML
    private Label temperature;

    @FXML
    private Label condition;

    @FXML
    private Label humidity;

    @FXML
    private Label humidityText;

    @FXML
    private Label pressure;

    @FXML
    private Label pressureText;

    @FXML
    private ImageView windSpeedIcon;

    @FXML
    private Label windSpeed;

    @FXML
    private Label windSpeedVal;


    private DayForecastController[] dayForecastControllers;

    private ForecastService forecast;



    @FXML
    private void initialize() throws IOException {
        forecast = ForecastService.getInstance();
        forecast.registerObserver(this);
        dayForecastControllers = new DayForecastController[6];
        FXMLLoader dayFxml;
        DayForecastController dayController;

        int index = 0;
        for (int i=0;i<2;i++){
            for (int j=0;j<3;j++){
                dayFxml = new FXMLLoader(getClass().getResource("DayForecastWidget.fxml"));
                dayController = new DayForecastController();
                dayController.setDayForecast(forecast.week[index]);
                dayForecastControllers[index++]=dayController;
                dayFxml.setController(dayController);
                forecast.registerObserver(dayController);
                weeklyForecast.add(dayFxml.load(),j,i);
            }
        }

        Platform.runLater(()-> refreshClick());


    }

    @FXML
    public void update() {
        condition.setText(forecast.today.getCondition());
        temperature.setText(forecast.today.getTemperature());
        humidity.setText(forecast.today.getHumidity());
        pressure.setText(forecast.today.getPressure());
        windSpeed.setText(forecast.today.getWindSpeed());
        locationText.setText(forecast.today.getLocation());
        windSpeedIcon.setRotate(forecast.today.getWindDegree());
        weatherTypeImg.setImage(new ImageFactory().getShape(forecast.today.getWeatherCode()));
    }

    @FXML
    public void refreshClick() {
        forecast.setState();
    }
}
