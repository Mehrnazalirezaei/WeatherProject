package ir.roojano.sematec.weatherproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.roojano.sematec.weatherproject.Model.WeatherDataModel;

public class Util {
    public static String Base_URL="https://api.openweathermap.org/data/2.5/weather?q=";
    public static String APi_Key = "&appid=2a7614dec3ed4c91d905f35963ba0b43";
    public static String UNIT = "&units=metric";
    public static String ICON_URL="https://api.openweathermap.org/img/w/";
    public static WeatherDataModel parsweatherjson(String jsonData){
        try {
            JSONObject parentObject = new JSONObject(jsonData);
        WeatherDataModel model = new WeatherDataModel();
        model.setLastUpdate(parentObject.getLong("dt"));
        JSONObject sys =parentObject.getJSONObject("sys");
        model.setSunrise(sys.getLong("sunrise"));
        model.setSunset(sys.getLong("sunset"));
            JSONArray weatherArray=parentObject.getJSONArray("weather");
            JSONObject firstWeatherArrayObject=weatherArray.getJSONObject(0);
            model.setCloud(firstWeatherArrayObject.getString("description"));
        JSONObject innerMainObject = parentObject.getJSONObject("main");
        model.setHumidity(innerMainObject.getInt("humidity"));
            model.setPress(innerMainObject.getInt("pressure"));
            model.setTemp(innerMainObject.getDouble("temp"));
            JSONObject WindObj = parentObject.getJSONObject("wind");
            model.setWinddeg(WindObj.getInt("deg"));
            model.setWindSpeed(WindObj.getDouble("speed"));
            model.setCity(parentObject.getString("name"));
            model.setIcon(firstWeatherArrayObject.getString("icon"));
        return model;
        } catch (JSONException e) {
            Log.i("Error",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
