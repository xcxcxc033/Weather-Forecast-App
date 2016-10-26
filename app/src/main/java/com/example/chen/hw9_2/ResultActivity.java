package com.example.chen.hw9_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Chen on 2015/11/20.
 */
public class ResultActivity extends Activity {

    String TAG = "XXXXXXXXXXXXXXXXXXXXXXXXXX";

    CallbackManager facebiikCallBC;
    ShareDialog MYshare;
    String stn_1 = "";
    String stn_2 = "";
    String stn_3 = "";
    String stn_4 = "via Weather APP";
    String pic = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultactivity);

//face book ---------------------------------------------------------
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebiikCallBC = CallbackManager.Factory.create();
        MYshare = new ShareDialog(this);

//face book ---------------------------------------------------------

        String JSONdata = getIntent().getStringExtra("JSON_file");
        String street_name = getIntent().getStringExtra("street_name");
        String city_name = getIntent().getStringExtra("city_name");
        String state_name = getIntent().getStringExtra("state_name");
        String degree_name = getIntent().getStringExtra("degree_name");
        JSONdata = JSONdata.replace("(", "");
        JSONdata = JSONdata.replace(")", "");



        int[] icons = {R.drawable.clear, R.drawable.clear_night,R.drawable.cloudy,R.drawable.cloud_day,R.drawable.cloud_night,R.drawable.fog,R.drawable.rain,R.drawable.wind,R.drawable.sleet,R.drawable.snow};

        // String newJSONdate = JSONdata.substring(1,JSONdata.length()-2);
        final String js = JSONdata;
        final String MYcity = city_name;
        final String MYstate = state_name;

        printFirstActvt(street_name, city_name, state_name, degree_name, JSONdata, icons);

        Button map = (Button)findViewById(R.id.button_map);
        map.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View view) {
                        try {
                            JSONObject obj = new JSONObject(js);
                            Double lat = obj.getDouble("latitude");
                            Double lont = obj.getDouble("longitude");
                            Intent mapactivity = new Intent(ResultActivity.this, MapsActivity.class);
                            Bundle bundle = new Bundle();



                            bundle.putString("lat",lat.toString());
                            bundle.putString("lont",lont.toString());


                            mapactivity.putExtras(bundle);
                            startActivity(mapactivity);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }
        );

        Button detailButton = (Button)findViewById(R.id.details);

        final String jsFILE = JSONdata;
        final String degree_s = degree_name;
        detailButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent detailActivity = new Intent(ResultActivity.this, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("JSON_file", jsFILE);
                        bundle.putString("degree_name", degree_s);
                        bundle.putString("city_name", MYcity);
                        bundle.putString("state_name", MYstate);


                        detailActivity.putExtras(bundle);
                        startActivity(detailActivity);

                    }

                }

        );


        Button fbButton = (Button)findViewById(R.id.facebook_btn);

        fbButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {


                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                    .setContentTitle(stn_1)
                                    .setContentDescription(
                                            stn_3)
                                    .setContentUrl(Uri.parse(stn_2))
                                    .setImageUrl(Uri.parse(pic))
                                    .build();
                            MYshare.registerCallback(facebiikCallBC, new FacebookCallback<Sharer.Result>() {



                            @Override
                            public void onSuccess(Sharer.Result result) {

                                Log.i("txtxttxtx","success");
                                Context context = getApplicationContext();
                                CharSequence text = "Facebook Post Successful";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                //Toast.makeText(mContext, R.string.succsss, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel() {
                                Log.i("txtxttxtx","falsese");
                                Context context = getApplicationContext();
                                CharSequence text = "Posted Cancelled";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }

                            @Override
                            public void onError(FacebookException e) {
                                e.printStackTrace();
                            }
                        });
                            MYshare.show(linkContent);
                    }

                    }
                }
        );


    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebiikCallBC.onActivityResult(requestCode, resultCode, data);
    }

    public void printFirstActvt(String street,String city,String state,String degree,String json, int[] icons){
        /*        TextView newdata;
        newdata = (TextView)findViewById(R.id.xxx);
        TextView newdata1;
        newdata1 = (TextView)findViewById(R.id.xxx1);
        TextView newdata2;
        newdata2 = (TextView)findViewById(R.id.xxx2);*/
        String mainCentence = "";

        stn_1 = "Current Weather in " + city + ", " + state;
        stn_2 = "forecast.io";



        ImageView newdata;
        newdata = (ImageView)findViewById(R.id.picture);

        TextView mytst;
        mytst = (TextView)findViewById(R.id.maintxt);
        mytst.setText(json);

        try {
            JSONObject obj = new JSONObject(json);
            // newdata.setText("chanfe1");
            // JSONArray first_level = obj.getJSONArray("timezone");
            //JSONArray rootArray = obj.getJSONArray("timezone");
            //JSONObject timezone = obj.getJSONObject("timezone");
            // newdata.setText(obj.toString());
            printA(city, state, obj);
            printPic(obj, icons);
            printForm(obj,degree);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }

    private void printForm(JSONObject obj,String degree){

        TextView samllDegree;
        samllDegree = (TextView)findViewById(R.id.smallDegree);
        String degree_CF;

        int precip = 0;
        int chanceOfRain = 0;
        double windSpeed = 0;
        int dewPoint = 0;
        int Humidity= 0;
        String Visibility= null;

        try {
            precip = (int)(1000 * obj.getJSONObject("currently").getDouble("precipIntensity"));
            chanceOfRain = (int) Math.round(100 * obj.getJSONObject("currently").getDouble("precipProbability"));
            windSpeed = (obj.getJSONObject("currently").getDouble("windSpeed"));


            dewPoint = (int) Math.round(obj.getJSONObject("currently").getDouble("dewPoint"));
            Humidity = (int) (100 * (obj.getJSONObject("currently").getDouble("humidity")));
            Visibility = obj.getJSONObject("currently").getString("visibility");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView precipt;
        precipt = (TextView)findViewById(R.id.Precipitation);
        if(precip >= 0 && precip < 2){
            precipt.setText("None");
        }else if(precip >= 2 && precip < 17){
            precipt.setText("Very Light");
        }else if(precip >= 17 && precip < 100){
            precipt.setText("Light");
        }else if(precip >= 17 && precip < 100){
            precipt.setText("Moderate");
        }else if(precip >= 400){
            precipt.setText("Heavy");
        }

        TextView chanceRain;
        chanceRain = (TextView)findViewById(R.id.ChanceOfRain_R);
        chanceRain.setText(chanceOfRain + "%");

        DecimalFormat decimalFormat =new DecimalFormat("0.00");
        String new_windSpeed = decimalFormat.format(windSpeed);
        //String new_dewPoint = decimalFormat.format(dewPoint);

        //String new_Visibility = decimalFormat.format(Visibility);

        TextView wind_speed;
        wind_speed = (TextView)findViewById(R.id.WindSpeed_R);

        TextView new_dewPoint_txt;
        new_dewPoint_txt = (TextView)findViewById(R.id.DewPoint_R);

        TextView new_Humidity_txt;
        new_Humidity_txt = (TextView)findViewById(R.id.Humidity_R);

        TextView new_Visibility_txt;
        new_Visibility_txt = (TextView)findViewById(R.id.Visibility_R);




        if(degree.equals("si")){
            degree_CF = "C";
            wind_speed.setText(new_windSpeed + " m/s");
            new_Visibility_txt.setText(Visibility + " km");
            stn_3 = stn_3 + (char)0x00B0 + "C";



        }else{
            degree_CF = "F";
            wind_speed.setText(new_windSpeed + " mph");
            new_Visibility_txt.setText(Visibility + " mi");

            stn_3 =stn_3 + (char)0x00B0 + "C";
        }

        samllDegree.setText((char)0x00B0 + degree_CF);
        String dew = (char)0x00B0 + degree_CF;
        new_dewPoint_txt.setText(dewPoint + dew);
        new_Humidity_txt.setText(Humidity + "%");






        return;
    }

    private void printSUNdate(Date date_rise,Date date_set,double org_offset){
         /*String time = "1369148661";
            long timestampLong = Long.parseLong(time)*1000;*/
           /* Date d = new Date(timestampLong);*/
        Calendar date_rise_c = Calendar.getInstance();
        Calendar date_set_c = Calendar.getInstance();

        date_set_c.setTime(date_set);
        date_rise_c.setTime(date_rise);

        TimeZone tz = TimeZone.getDefault();
        int offset = tz.getRawOffset();

        int rise_hour = date_rise_c.get(Calendar.HOUR_OF_DAY);
        int rise_min = date_rise_c.get(Calendar.MINUTE);

        int set_hour = date_set_c.get(Calendar.HOUR_OF_DAY);
        int set_min = date_set_c.get(Calendar.MINUTE);
        //int AM_PM_rise = date_rise_c.get(Calendar.AM_PM);
        Log.i("XCXCXCX SET H",String.valueOf(set_hour));
        Log.i("XCXCXCX RIS H",String.valueOf(rise_hour));

        double new_offset = offset / 3600000 + (offset / 60000) % 60;

        rise_hour = rise_hour + (int)(org_offset - new_offset);
        set_hour = set_hour + (int)(org_offset - new_offset);

        DecimalFormat timeformat =new DecimalFormat("00");
        //  String NtemperatureMin = newformat.format(temperatureMin);
        // String NtemperatureMin = newformat.format(temperatureMin);
        Log.i("XCXCXCX NEW",String.valueOf(new_offset));
        Log.i("XCXCXCX ORG",String.valueOf(org_offset));

        Log.i("XCXCXCX AFT SET H",String.valueOf(set_hour));
        Log.i("XCXCXCX AFT RIS H",String.valueOf(rise_hour));
        //  Log.i("XCXCXCX sec",String.valueOf(set_sec));

        TextView SunRise;
        TextView SunSet;
        SunRise = (TextView)findViewById(R.id.Sunrise_R);
        SunSet = (TextView)findViewById(R.id.SunsetR);

        if(rise_hour >= 12){
            rise_hour -= 12;

            SunRise.setText(String.valueOf(rise_hour) + ":" + timeformat.format(rise_min) + " PM");
        }else{

            SunRise.setText(String.valueOf(rise_hour) + ":" + timeformat.format(rise_min) + " AM");

        }

        if(set_hour >= 12){
            set_hour -= 12;

            SunSet.setText(String.valueOf(set_hour) + ":" + timeformat.format(set_min) + " PM");

        }else{

            SunSet.setText(String.valueOf(set_hour) + ":" + timeformat.format(set_min) + " AM");

        }

    }



    private void printPic(JSONObject obj,int[] icons){
        ImageView newdata;
        newdata = (ImageView)findViewById(R.id.picture);
        TextView txtDegree;
        txtDegree = (TextView)findViewById(R.id.degree);



        TextView underline;
        underline = (TextView)findViewById(R.id.twodegree);

        String getIcon = null;
        //String degreeCtnt = null;
        try {
            getIcon = obj.getJSONObject("currently").getString("icon");
            int currentDegree = (int)Math.round(obj.getJSONObject("currently").getDouble("temperature"));

            int temperatureMin =(int) Math.round(obj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getDouble("temperatureMin"));
            int temperatureMax = (int)Math.round(obj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getDouble("temperatureMax"));



            Date date_rise = new Date(1000*obj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getLong("sunriseTime"));
            Date date_set = new Date(1000*obj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getLong("sunsetTime"));
            Double offset = obj.getDouble("offset");
            printSUNdate(date_rise,date_set,offset);


            DecimalFormat newformat =new DecimalFormat("0");
            String NtemperatureMin = newformat.format(temperatureMin);
            String NtemperatureMax = newformat.format(temperatureMax);
            String NdegreeCtnt = newformat.format(currentDegree);

            Log.i("xxxxxxxxxxxxMIN",NtemperatureMin);
            Log.i("xxxxxxxxxxxxMIN", NtemperatureMax);

            underline.setText("L:" + NtemperatureMin + "\u00B0" + " | H:" + NtemperatureMax + "\u00B0"); // "\u00B0"
            txtDegree.setText(NdegreeCtnt);
            stn_3 = stn_3 + NdegreeCtnt;

        } catch (JSONException e) {
            e.printStackTrace();
        }




        if(getIcon.equals("clear-day") ){
            newdata.setImageResource(icons[0]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/clear.png";

        }else if(getIcon.equals("clear-night")){
            newdata.setImageResource(icons[1]);

            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/clear_night.png";
        }else if(getIcon.equals("rain")){
            newdata.setImageResource(icons[6]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/rain.png";
        }else if(getIcon.equals("snow")){
            newdata.setImageResource(icons[9]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/snow.png";
        }else if(getIcon.equals("sleet")){
            newdata.setImageResource(icons[8]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/sleet.png";
        }else if(getIcon.equals("wind")){
            newdata.setImageResource(icons[7]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/wind.png";
        }else if(getIcon.equals( "fog")){
            newdata.setImageResource(icons[5]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/fog.png";
        }else if(getIcon.equals("cloudy")){
            newdata.setImageResource(icons[2]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/cloudy.png";
        }else if(getIcon.equals("partly-cloudy-day")){
            newdata.setImageResource(icons[3]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_day.png";
        }else if(getIcon.equals("partly-cloudy-night")){
            newdata.setImageResource(icons[4]);
            pic = "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_night.png";
        }
        return;
    }

    private void printA(String city, String state,JSONObject obj){
        String summary = null;
        try {
            summary = obj.getJSONObject("currently").getString("summary");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String mainA = summary + " in " + city + "， " + state;

        stn_3 = summary + ", ";
        TextView mytst;
        mytst = (TextView)findViewById(R.id.maintxt);
        mytst.setText(mainA);


        return;
    }

}
