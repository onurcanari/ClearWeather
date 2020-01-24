package main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class DayForecastController implements IObserver{

    @FXML
    private Label dayNameText;

    @FXML
    private ImageView weatherTypeImg;

    @FXML
    private Label highTemp;

    @FXML
    private Label lowTemp;


    private DayForecast dayForecast;



    @FXML
    void initialize() {
    }


    @FXML
    public void update() {
        System.out.println(dayForecast);
        dayNameText.setText(dayForecast.getDay());
        highTemp.setText(String.valueOf(dayForecast.getHighTemp()));
        lowTemp.setText(String.valueOf(dayForecast.getLowTemp()));
        weatherTypeImg.setImage(new ImageFactory().getShape(dayForecast.getWeatherCode()));
    }

    public DayForecast getDayForecast() {
        return dayForecast;
    }

    public void setDayForecast(DayForecast dayForecast) {
        this.dayForecast = dayForecast;
    }



}
