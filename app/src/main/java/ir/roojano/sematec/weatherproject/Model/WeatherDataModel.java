package ir.roojano.sematec.weatherproject.Model;

public class WeatherDataModel {
    private double temp;
    private String icon;
    private double WindSpeed;
    private int winddeg;
    private int press;
    private int Humidity;
    private String Cloud;
    private long Sunrise;
    private long Sunset;
    private long LastUpdate;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    private String City;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        WindSpeed = windSpeed;
    }

    public int getWinddeg() {
        return winddeg;
    }

    public void setWinddeg(int winddeg) {
        this.winddeg = winddeg;
    }

    public int getPress() {
        return press;
    }

    public void setPress(int press) {
        this.press = press;
    }

    public int getHumidity() {
        return Humidity;
    }

    public void setHumidity(int humidity) {
        Humidity = humidity;
    }

    public String getCloud(Object description) {
        return Cloud;
    }

    public void setCloud(String cloud) {
        Cloud = cloud;
    }

    public long getSunrise() {
        return Sunrise;
    }

    public void setSunrise(long sunrise) {
        Sunrise = sunrise;
    }

    public long getSunset() {
        return Sunset;
    }

    public void setSunset(long sunset) {
        Sunset = sunset;
    }

    public long getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public char getCloud() {
        return 0;
    }
}
