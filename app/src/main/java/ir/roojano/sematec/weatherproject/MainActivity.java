package ir.roojano.sematec.weatherproject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import ir.roojano.sematec.weatherproject.Model.WeatherDataModel;
public class MainActivity extends AppCompatActivity {

    TextView cityText;
    ImageView iconic;
    TextView tempter;
    TextView WindText;
    TextView humidText;
    TextView pressText;
    TextView sunriseText;
    TextView sunsetText;
    TextView updateText;
    TextView cloudText;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityText    = findViewById(R.id.cityText);
        iconic      = findViewById(R.id.iconImage);
        tempter     = findViewById(R.id.temptext);
        WindText    = findViewById(R.id.windtext);
        humidText   = findViewById(R.id.Hummidtytext);
        pressText   = findViewById(R.id.presstext);
        sunriseText = findViewById(R.id.SunriseText);
        sunsetText  = findViewById(R.id.SunsetText);
        updateText  = findViewById(R.id.LastUpdatetext);
        cloudText   = findViewById(R.id.CloudText);
        InternetDownloadTask cl = new InternetDownloadTask();
        SharedPreferences preferences = getSharedPreferences("City", MODE_PRIVATE);
        String value = preferences.getString("Saved","City");
        cl.execute(preferences.getString("City","Tehran,IR"));
        RelativeLayout layout =(RelativeLayout)findViewById(R.id.backgrounda);
        layout.setBackgroundResource(R.drawable.back);
    }



    @SuppressLint("StaticFieldLeak")
    public class InternetDownloadTask extends AsyncTask<String, Void, WeatherDataModel> {
        @Override
        protected WeatherDataModel doInBackground(String... strings) {
            try {
                String urls = Util.Base_URL+strings[0]+Util.UNIT+Util.APi_Key;
                URL uri = new URL(urls);
                HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();
                InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
                StringBuilder builder = new StringBuilder();
                int temp = reader.read();
                while (temp != -1) {
                    builder.append(((char) temp));
                    temp = reader.read();
                }
                reader.close();
                stream.close();
                connection.disconnect();
                return Util.parsweatherjson(builder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(WeatherDataModel  model) {
            cityText.setText(model.getCity());
            tempter.setText(model.getTemp() + "C");
            WindText.setText("WindSpeed: " + Double.toString( model.getWindSpeed()));
            humidText.setText("humidity: " +Integer.toString(model.getHumidity()));
            pressText.setText("pressure: " +Integer.toString(model.getPress()));
            String sunriseString = DateFormat.getTimeInstance().format(new Date(model.getSunrise()));
            sunriseText.setText("Sunset: " +sunriseString);
            String sunsetString = DateFormat.getTimeInstance().format(new Date(model.getSunset()));
            sunsetText.setText("Sunrise: " +sunsetString);
            updateText.setText("LastUpdate" + DateFormat.getTimeInstance().format(model.getLastUpdate()));
            imagedownload download = new imagedownload();
            download.execute(model.getIcon());
            getSharedPreferences("City",MODE_PRIVATE).edit().putString("City",model.getCity()).apply();
          super.onPostExecute(model);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.changecityitem){
            showchangecity();

        }
        if (item.getItemId() == R.id.aboutWeather){
            setContentView(R.layout.activity_about_us);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showchangecity() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText tex = new EditText(this);
        tex.setHint("Entrer your city");
        tex.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setTitle("Change City Name");
        builder.setMessage("Enter Your City!!");
        builder.setView(tex);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new InternetDownloadTask().execute(tex.getText().toString());
            }
        });
        builder.show();
    }

    class imagedownload extends AsyncTask<String, Void , Bitmap>{
                @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(Util.ICON_URL+strings[0]+".png");
                HttpURLConnection connection;
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap bmp =BitmapFactory.decodeStream(in);
                return bmp;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
                    iconic.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }

    }


}
