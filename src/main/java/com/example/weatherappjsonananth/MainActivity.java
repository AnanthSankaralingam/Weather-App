package com.example.weatherappjsonananth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    TextView city1, city2, city3, weather1, weather2, weather3, des1,des2,des3, date1,date2,date3, time1,time2,time3;
    Button ananth;
    ImageView imageView1, imageView2, imageView3;
    EditText longitude, latitude;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.rl);
        city1 = findViewById(R.id.city1);
        city2=findViewById(R.id.city2);
        city3=findViewById(R.id.city3);
        weather1=findViewById(R.id.Temp1);
        weather2=findViewById(R.id.Temp2);
        weather3=findViewById(R.id.Temp3);
        des1 = findViewById(R.id.Climate1);
        des2=findViewById(R.id.Climate2);
        des3=findViewById(R.id.Climate3);
        date1=findViewById(R.id.Date1);
        date2=findViewById(R.id.Date2);
        date3=findViewById(R.id.Date3);
        time1=findViewById(R.id.Time1);
        time2=findViewById(R.id.Time2);
        time3=findViewById(R.id.Time3);
        imageView1=findViewById(R.id.image1);
        imageView2=findViewById(R.id.image2);
        imageView3=findViewById(R.id.image3);
        longitude=findViewById(R.id.longitude);
        latitude =findViewById(R.id.latitude);
        ananth = findViewById(R.id.button);
        ananth.setOnClickListener(v -> {
            DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
            downloadFilesTask.execute();
        });
        //imageView1.setImageResource(R.drawable.back);
        imageView1.setAlpha(10);
        imageView2.setAlpha(10);
        imageView3.setAlpha(10);
    }

    private class DownloadFilesTask extends AsyncTask<String, String, String> {
        String weather ="";

        @Override
        protected String doInBackground(String... strings)
        {
            URL myURL = null;
            try {
                myURL = new URL("https://api.openweathermap.org/data/2.5/find?lat="+ latitude.getText().toString()+"&lon="+longitude.getText().toString()+"&cnt=3&appid=78c6433b4c572b7fbfdd00a8e8c8ed8d&units=imperial");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            assert myURL != null;
            Log.d("TAG", myURL.toString());

            URLConnection connection = null;
            try {
                connection = myURL.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG",e.toString());
            }
            Log.d("TAG", connection.toString());

            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG",e.toString());
            }
            Log.d("TAG",inputStream.toString());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                weather= bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("TAG", bufferedReader.toString());
            return weather;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject innerObject = jsonArray.getJSONObject(i);
                    int t1, t2, t3;
                    Date dt;
                    if(i==0) {
                        imageView1.setAlpha(250);
                        city1.setText(innerObject.getString("name"));

                        double w1 = (innerObject.getJSONObject("main").getDouble("temp"));
                        w1= (double)(Math.round(w1*10.0)/10.0);
                        weather1.setText(w1+" °F");
                        des1.setText(innerObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                        t1 = Integer.valueOf(innerObject.getString("dt"));
                        long batch_date = t1;
                         dt = new Date(batch_date*1000);
                        SimpleDateFormat time = new SimpleDateFormat("hh:mm a z");
                        time1.setText(time.format(dt));
                        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
                        date1.setText(sfd.format(dt));
                        String img = innerObject.getJSONArray("weather").getJSONObject(0).getString("main");
                        if(img.equalsIgnoreCase("Thunderstorm"))
                            imageView1.setImageResource(R.drawable.storm);
                        if((img.equals("Drizzle"))||img.equals("Rain"))
                            imageView1.setImageResource(R.drawable.rain);
                        if(img.equals("Snow"))
                            imageView1.setImageResource(R.drawable.snow);
                        if(img.equals("Clear"))
                            imageView1.setImageResource(R.drawable.clear);
                        if(img.equals("Clouds"))
                            imageView1.setImageResource(R.drawable.cloudy);
                    }
                    if(i==1) {
                        imageView2.setAlpha(250);
                        city2.setText(innerObject.getString("name"));
                        double w2 = (innerObject.getJSONObject("main").getDouble("temp"));
                        w2= (double)(Math.round(w2*10.0)/10.0);
                        weather2.setText(w2 + " °F");
                        des2.setText(innerObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                        t2 = Integer.valueOf(innerObject.getString("dt"));
                        long batch_date = t2;
                        dt = new Date(batch_date*1000);
                        SimpleDateFormat time = new SimpleDateFormat("hh:mm a z");
                        time2.setText(time.format(dt));
                        if((time.format(dt).substring(6,8).equalsIgnoreCase("pm")))
                                constraintLayout.setBackgroundResource(R.drawable.night);
                        else
                            constraintLayout.setBackgroundResource(R.drawable.back);

                        SimpleDateFormat sfd = new SimpleDateFormat("MM-dd-yyyy");
                        date2.setText(sfd.format(dt));
                        String img = innerObject.getJSONArray("weather").getJSONObject(0).getString("main");

                        switch(img){
                            case "Thunderstorm":
                                imageView2.setImageResource(R.drawable.storm);
                                break;
                            case "Drizzle":
                                imageView2.setImageResource(R.drawable.rain);
                                break;
                            case "Rain":
                                imageView2.setImageResource(R.drawable.rain);
                                break;
                            case "Snow":
                                imageView2.setImageResource(R.drawable.snow);
                                break;
                            case "Clear":
                                imageView2.setImageResource(R.drawable.clear);
                                break;
                            case "Clouds":
                                imageView2.setImageResource(R.drawable.cloudy);
                                break;
                            default:
                                imageView2.setImageResource(R.drawable.ic_launcher_background);
                        }
                    }
                    if(i==2) {
                        imageView3.setAlpha(250);
                        city3.setText(innerObject.getString("name"));
                        double w3 = (innerObject.getJSONObject("main").getDouble("temp"));
                        w3= (double)(Math.round(w3*10.0)/10.0);
                        weather3.setText(w3 + " °F");
                        des3.setText(innerObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                        t3 = Integer.valueOf(innerObject.getString("dt"));
                        long batch_date = t3;
                         dt = new Date(batch_date*1000);
                        SimpleDateFormat time = new SimpleDateFormat("hh:mm a z");
                        time3.setText(time.format(dt));
                        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
                        date3.setText(sfd.format(dt));
                        String img = innerObject.getJSONArray("weather").getJSONObject(0).getString("main");

                        switch(img){
                            case "Thunderstorm":
                                imageView3.setImageResource(R.drawable.storm);
                                break;
                            case "Drizzle":
                                imageView3.setImageResource(R.drawable.rain);
                                break;
                            case "Rain":
                                imageView3.setImageResource(R.drawable.rain);
                                break;
                            case "Snow":
                                imageView3.setImageResource(R.drawable.snow);
                                break;
                            case "Clear":
                                imageView3.setImageResource(R.drawable.clear);
                                break;
                            case "Clouds":
                                imageView3.setImageResource(R.drawable.cloudy);
                                break;
                            default:
                                imageView3.setImageResource(R.drawable.ic_launcher_background);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ERROR",e.toString());
            }

        }
    }


}
