package com.example.asus.activititytest;

import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.id;


public class NetworkTest extends AppCompatActivity {
    private ArrayList<AirRequest> airRequest_list = new ArrayList<>();
    private static String result =
            "http://apis.haoservice.com/air/cityair?city=%E6%AD%A6%E6%B1%89&key=d8cb4d702d26483c8d6b85a2c40cb2d2";

    public class AirAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<AirRequest> AirData;

        public AirAdapter(Context context, List<AirRequest> listAirRequest){
            this.mLayoutInflater = LayoutInflater.from(context);
            this.AirData = listAirRequest;
        }
        @Override
        public int getCount() {
            return AirData.size();
        }

        @Override
        public Object getItem(int position) {
            return AirData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AirRequest airRequest= AirData.get(position);
            View view = mLayoutInflater.inflate(R.layout.app_item,null);
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.app_item, null);
                viewHolder = new ViewHolder();
                viewHolder.ranking = (TextView) convertView.findViewById(R.id.ranking);
                viewHolder.city = (TextView) convertView.findViewById(R.id.city);
                viewHolder.province = (TextView) convertView.findViewById(R.id.province);
                viewHolder.AQI = (TextView) convertView.findViewById(R.id.AQI);
                viewHolder.quality = (TextView) convertView.findViewById(R.id.Quality);
                viewHolder.PM25 = (TextView) convertView.findViewById(R.id.PM25);
                viewHolder.UpdateTime = (TextView) convertView.findViewById(R.id.UpdateTime);
                convertView.setTag(viewHolder);
            } else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.ranking.setText(airRequest.getRanking());
            viewHolder.city.setText(airRequest.getCityName());
            viewHolder.province.setText(airRequest.getProvinceName());
            viewHolder.AQI.setText(airRequest.getAQI());
            viewHolder.quality.setText(airRequest.getQuality());
            viewHolder.PM25.setText(airRequest.getPM25());
            viewHolder.UpdateTime.setText(airRequest.getUpdateTime());
            return view;
        }

        class ViewHolder {
            TextView ranking;
            TextView city;
            TextView province;
            TextView AQI;
            TextView quality;
            TextView PM25;
            TextView UpdateTime;
        }
    }

    public void  parseJSONToAir (final String jsonData){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONObject result = jsonObject.getJSONObject("result");
                    AirRequest airRequest = new AirRequest();
                    airRequest.setRanking(result.getInt("Ranking"));
                    airRequest.setCityName(result.getString("CityName"));
                    airRequest.setProvinceName(result.getString("ProvinceName"));
                    airRequest.setAQI(result.getInt("AQI"));
                    airRequest.setQuality(result.getString("Quality"));
                    airRequest.setPM25(result.getString("PM25"));
                    airRequest.setUpdateTime(result.getString("UpdateTime"));
                    airRequest_list.add(airRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public  ArrayList<AirRequest> getAirRequest (Context context,final String urlString) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlString);
                            OkHttpClient connection =new OkHttpClient ();
                            Request request = new Request.Builder().url(url).build();
                            Response response = connection.newCall(request).execute();
                            String responseData = response.body().string();
                            parseJSONToAir(responseData);
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

        return airRequest_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);
        getAirRequest(NetworkTest.this,result);
               AirAdapter airAdapter = new AirAdapter(NetworkTest.this,airRequest_list);
               ListView airlist = (ListView) findViewById(R.id.Air_list);
               airlist.setAdapter(airAdapter);
           }

   public class AirRequest{
        private int Ranking;
        private int AQI;
        private String CityName;
        private String ProvinceName;
        private String Quality;
        private String PM25;
        private String UpdateTime;

        public int getRanking() {
            return Ranking;
        }
        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }
        public int getAQI() {
            return AQI;
        }
        public void setAQI(int AQI) {
            this.AQI = AQI;
        }
        public String getCityName() {
            return CityName;
        }
        public void setCityName(String CityName){
            this.CityName = CityName;
        }
        public String getProvinceName() {
            return ProvinceName;
        }
        public void setProvinceName(String ProvinceName){
            this.ProvinceName = ProvinceName;
        }

        public String getQuality() {
            return Quality;
        }
        public void setQuality(String Quality ) {
            this.Quality = Quality;
        }
        public String getPM25() {
            return PM25;
        }
        public void setPM25(String PM25 ) {
            this.PM25 = PM25;
        }
        public String getUpdateTime() {
            return UpdateTime;
        }
        public void setUpdateTime(String UpdateTime ) {
            this.UpdateTime = UpdateTime;
        }
    }
    }