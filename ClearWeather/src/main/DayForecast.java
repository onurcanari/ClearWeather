package main;

public class DayForecast {

    private String day;
    private int highTemp;
    private int lowTemp;
    private int weatherCode;

    @Override
    public String toString() {
        return "DayForecast{" +
                "day='" + day + '\'' +
                ", highTemp=" + highTemp +
                ", lowTemp=" + lowTemp +
                ", weatherCode=" + weatherCode +
                '}';
    }

    public void updateData(DayForecast forecast) {
        this.day = forecast.day;
        this.highTemp = forecast.highTemp;
        this.lowTemp = forecast.lowTemp;
        this.weatherCode = forecast.weatherCode;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }


}
