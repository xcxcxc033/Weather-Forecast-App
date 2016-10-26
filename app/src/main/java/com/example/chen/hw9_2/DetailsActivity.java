package com.example.chen.hw9_2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Chen on 2015/11/22.
 */
public class DetailsActivity extends Activity {

    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.detailsactivity);
        String JSONdata = getIntent().getStringExtra("JSON_file");
        String degree =  getIntent().getStringExtra("degree_name");
        String city_name = getIntent().getStringExtra("city_name");
        String state_name = getIntent().getStringExtra("state_name");

        String MainSentence = "More Details for " + city_name + ", " + state_name;
        TextView txt0 = (TextView)findViewById(R.id.title_2);
        txt0.setText(MainSentence);

        clickListener();

        //createTabs();
        createTableList(JSONdata, degree);
        creastTableListSevenDay(JSONdata, degree);
    }

    private void clickListener(){
        final Button next7 = (Button)findViewById(R.id.next7);
        final Button next24 = (Button)findViewById(R.id.next24);

        final LinearLayout N7 = (LinearLayout)findViewById(R.id.days_7);
        final LinearLayout N24 = (LinearLayout)findViewById(R.id.hours_24);
        N7.setVisibility(View.GONE);
        N24.setVisibility(View.VISIBLE);

        next7.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){
                        next7.setBackgroundColor(Color.BLUE);
                        next24.setBackgroundColor(Color.LTGRAY);
                        N7.setVisibility(View.VISIBLE);
                        N24.setVisibility(View.GONE);

                    }
                }
        );

        next24.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){
                        next7.setBackgroundColor(Color.LTGRAY);
                        next24.setBackgroundColor(Color.BLUE);
                        N7.setVisibility(View.GONE);
                        N24.setVisibility(View.VISIBLE);

                    }
                }
        );



    }

    private void creastTableListSevenDay(String JSONdata,String degree){
        JSONObject obj = null;
        try {
            obj = new JSONObject(JSONdata);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int x = 0; x<7 ; x++){
            try {
                Long date = obj.getJSONObject("daily").getJSONArray("data").getJSONObject(x).getLong("time");
                Date ndate = new Date(1000*date);

                Calendar date_c = Calendar.getInstance();
                date_c.setTime(ndate);

                int newx = x + 1;
                int day = date_c.get(Calendar.DAY_OF_MONTH);

                String date_1 = "days_" + String.valueOf(newx) + "_1" ;
                String date_2 = "days_" + String.valueOf(newx) + "_2" ;
                String date_3 = "days_" + String.valueOf(newx) + "_3" ;

                int str_1_ID = getResources().getIdentifier(date_1, "id", getPackageName());
                int str_2_ID = getResources().getIdentifier(date_2, "id", getPackageName());
                int str_3_ID = getResources().getIdentifier(date_3, "id", getPackageName());

                String myMonth = (String)android.text.format.DateFormat.format("MMM", ndate);
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

                String myWeek = sdf.format(ndate);

                DecimalFormat monthFormat =new DecimalFormat("00");
                String monthFormat_n = monthFormat.format(day);

                TextView txt1 = (TextView)findViewById(str_1_ID);
                txt1.setText(myWeek + ", " + myMonth + " "+ monthFormat_n);





            // **********************  set picture  *******************
                int[] icons = {R.drawable.clear, R.drawable.clear_night,R.drawable.cloudy,R.drawable.cloud_day,R.drawable.cloud_night,R.drawable.fog,R.drawable.rain,R.drawable.wind,R.drawable.sleet,R.drawable.snow};

                String getIcon = obj.getJSONObject("daily").getJSONArray("data").getJSONObject(x).getString("icon");

                ImageView newdata;
                newdata = (ImageView)findViewById(str_2_ID);

                if(getIcon.equals("clear-day") ){
                    newdata.setImageResource(icons[0]);
                }else if(getIcon.equals("clear-night")){
                    newdata.setImageResource(icons[1]);
                }else if(getIcon.equals("rain")){
                    newdata.setImageResource(icons[6]);
                }else if(getIcon.equals("snow")){
                    newdata.setImageResource(icons[9]);
                }else if(getIcon.equals("sleet")){
                    newdata.setImageResource(icons[8]);
                }else if(getIcon.equals("wind")){
                    newdata.setImageResource(icons[7]);
                }else if(getIcon.equals( "fog")){
                    newdata.setImageResource(icons[5]);
                }else if(getIcon.equals("cloudy")){
                    newdata.setImageResource(icons[2]);
                }else if(getIcon.equals("partly-cloudy-day")){
                    newdata.setImageResource(icons[3]);
                }else if(getIcon.equals("partly-cloudy-night")){
                    newdata.setImageResource(icons[4]);
                }

                // ----------------------------- MIN MAX DEGREE -----------------------------

                int temperatureMin =(int) Math.round(obj.getJSONObject("daily").getJSONArray("data").getJSONObject(x).getDouble("temperatureMin"));
                int temperatureMax = (int)Math.round(obj.getJSONObject("daily").getJSONArray("data").getJSONObject(x).getDouble("temperatureMax"));

                DecimalFormat newformat =new DecimalFormat("0");
                String NtemperatureMin = newformat.format(temperatureMin);
                String NtemperatureMax = newformat.format(temperatureMax);

                TextView txt3 = (TextView)findViewById(str_3_ID);


                if(degree.equals("si")){
                    String TMP = "Min: " + NtemperatureMin + "°C | Max: " + NtemperatureMax + "°C";
                    txt3.setText(TMP);
                }else{
                    String TMP = "Min: " + NtemperatureMin + "°F | Max: " + NtemperatureMax + "°F";
                    txt3.setText(TMP);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private void createTableList(String JSONdate,String degree){

        TextView tempFC_2;
        tempFC_2 = (TextView)findViewById(R.id.line_0_3);
        if(degree.equals("si")){
            String TMP = "Temp(" + "\u00b0"+"C)";
            tempFC_2.setText(TMP);
        }else {
            String TMP = "Temp(" + "\u00b0"+"F)";
            tempFC_2.setText(TMP);
        }


        JSONObject obj = null;
        try {
            obj = new JSONObject(JSONdate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int row;
        int[] icons = {R.drawable.clear, R.drawable.clear_night,R.drawable.cloudy,R.drawable.cloud_day,R.drawable.cloud_night,R.drawable.fog,R.drawable.rain,R.drawable.wind,R.drawable.sleet,R.drawable.snow};


        for(row = 0; row <= 24; row++) {
            int n_row = row +1;
            String txt_1_ID = "line_"+ String.valueOf(n_row) +"_1" ;
            String pic_ID = "line_" + String.valueOf(n_row) + "_2" ;
            String txt_3_ID = "line_" + String.valueOf(n_row) + "_3" ;
            String row_id = "row_"+  String.valueOf(row);
            int txtID = getResources().getIdentifier(txt_1_ID,"id",getPackageName());
            int picID = getResources().getIdentifier(pic_ID,"id",getPackageName());
            int txt_3ID = getResources().getIdentifier(txt_3_ID,"id",getPackageName());
            int row_ID = getResources().getIdentifier(row_id,"id",getPackageName());


            TableRow tablerow = (TableRow)findViewById(row_ID);
            if(row > 0 && row%2 == 0 ){
                tablerow.setBackgroundColor(Color.LTGRAY);
            }else if(row > 0 && row%2 == 1 ){
                tablerow.setBackgroundColor(Color.WHITE);
            }


            // MAKE row dispear --------------------------------------------

            /*TableRow tablerow = (TableRow)findViewById(row_ID);
            tablerow.setVisibility(View.GONE);*/

           // MAKE row dispear --------------------------------------------


            TextView txt_1;
            txt_1 = (TextView)findViewById(txtID);

            ImageView newdata;
            newdata = (ImageView)findViewById(picID);

            TextView txt_2;
            txt_2 = (TextView)findViewById(txt_3ID);
            //txt_2.setGravity(Gravity.CENTER);

            Log.i("xxxxxxxxxxxx_txtPID", String.valueOf(txtID));
            Log.i("xxxxxxxxxxxx_picIDPID", String.valueOf(R.id.line_1_1));
            Log.i("xxxxxxxxxxxx_picIDPID", String.valueOf(picID));
            Log.i("xxxxxxxxxxxx_picIDPID", String.valueOf(R.id.line_1_2));

            try {
                String getIcon = obj.getJSONObject("hourly").getJSONArray("data").getJSONObject(row).getString("icon");
                long time = obj.getJSONObject("hourly").getJSONArray("data").getJSONObject(row).getLong("time");
                Double temp = obj.getJSONObject("hourly").getJSONArray("data").getJSONObject(row).getDouble("temperature");

                DecimalFormat timeformat =new DecimalFormat("0");

                txt_2.setText(timeformat.format(temp));

                Date date= new Date(1000*time);
                Double offset = obj.getDouble("offset");
                String MyTime = printSUNdate(date,offset);

                txt_1.setText(MyTime);

                if(getIcon.equals("clear-day") ){
                    newdata.setImageResource(icons[0]);
                }else if(getIcon.equals("clear-night")){
                    newdata.setImageResource(icons[1]);
                }else if(getIcon.equals("rain")){
                    newdata.setImageResource(icons[6]);
                }else if(getIcon.equals("snow")){
                    newdata.setImageResource(icons[9]);
                }else if(getIcon.equals("sleet")){
                    newdata.setImageResource(icons[8]);
                }else if(getIcon.equals("wind")){
                    newdata.setImageResource(icons[7]);
                }else if(getIcon.equals( "fog")){
                    newdata.setImageResource(icons[5]);
                }else if(getIcon.equals("cloudy")){
                    newdata.setImageResource(icons[2]);
                }else if(getIcon.equals("partly-cloudy-day")){
                    newdata.setImageResource(icons[3]);
                }else if(getIcon.equals("partly-cloudy-night")){
                    newdata.setImageResource(icons[4]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for(row = 25; row <= 47; row++) {
            int n_row = row +1;
            String txt_1_ID = "line_"+ String.valueOf(n_row) +"_1" ;
            String pic_ID = "line_" + String.valueOf(n_row) + "_2" ;
            String txt_3_ID = "line_" + String.valueOf(n_row) + "_3" ;
            String row_id = "row_"+  String.valueOf(row);
            int txtID = getResources().getIdentifier(txt_1_ID,"id",getPackageName());
            int picID = getResources().getIdentifier(pic_ID,"id",getPackageName());
            int txt_3ID = getResources().getIdentifier(txt_3_ID,"id",getPackageName());
            int row_ID = getResources().getIdentifier(row_id,"id",getPackageName());


            TableRow tablerow = (TableRow)findViewById(row_ID);
            if(row > 0 && row%2 == 0 ){
                tablerow.setBackgroundColor(Color.LTGRAY);
            }else if(row > 0 && row%2 == 1 ){
                tablerow.setBackgroundColor(Color.WHITE);
            }


            // MAKE row dispear --------------------------------------------

                            TableRow tablerow_2 = (TableRow)findViewById(row_ID);
                            tablerow_2.setVisibility(View.GONE);

            // MAKE row dispear --------------------------------------------


            TextView txt_1;
            txt_1 = (TextView)findViewById(txtID);

            ImageView newdata;
            newdata = (ImageView)findViewById(picID);

            TextView txt_2;
            txt_2 = (TextView)findViewById(txt_3ID);
            // txt_2.setGravity(Gravity.CENTER);
//
//            Log.i("xxxxxxxxxxxx_txtPID", String.valueOf(txtID));
//            Log.i("xxxxxxxxxxxx_picIDPID", String.valueOf(R.id.line_1_1));
//            Log.i("xxxxxxxxxxxx_picIDPID", String.valueOf(picID));
//            Log.i("xxxxxxxxxxxx_picIDPID", String.valueOf(R.id.line_1_2));

            try {
                String getIcon = obj.getJSONObject("hourly").getJSONArray("data").getJSONObject(row).getString("icon");
                long time = obj.getJSONObject("hourly").getJSONArray("data").getJSONObject(row).getLong("time");
                Double temp = obj.getJSONObject("hourly").getJSONArray("data").getJSONObject(row).getDouble("temperature");

                DecimalFormat timeformat =new DecimalFormat("0");

                txt_2.setText(timeformat.format(temp));

                Date date= new Date(1000*time);
                Double offset = obj.getDouble("offset");
                String MyTime = printSUNdate(date,offset);

                txt_1.setText(MyTime);

                if(getIcon.equals("clear-day") ){
                    newdata.setImageResource(icons[0]);
                }else if(getIcon.equals("clear-night")){
                    newdata.setImageResource(icons[1]);
                }else if(getIcon.equals("rain")){
                    newdata.setImageResource(icons[6]);
                }else if(getIcon.equals("snow")){
                    newdata.setImageResource(icons[9]);
                }else if(getIcon.equals("sleet")){
                    newdata.setImageResource(icons[8]);
                }else if(getIcon.equals("wind")){
                    newdata.setImageResource(icons[7]);
                }else if(getIcon.equals( "fog")){
                    newdata.setImageResource(icons[5]);
                }else if(getIcon.equals("cloudy")){
                    newdata.setImageResource(icons[2]);
                }else if(getIcon.equals("partly-cloudy-day")){
                    newdata.setImageResource(icons[3]);
                }else if(getIcon.equals("partly-cloudy-night")){
                    newdata.setImageResource(icons[4]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        TableRow tablerow_2 = (TableRow)findViewById(R.id.row_48);
        tablerow_2.setVisibility(View.GONE);
        tablerow_2.setBackgroundColor(Color.LTGRAY);

        TableRow btnrow = (TableRow)findViewById(R.id.row_button);
        btnrow.setBackgroundColor(Color.LTGRAY);

        Button cross_btn = (Button)findViewById(R.id.line_button_cross);
        cross_btn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){
                        int row = 0;

                        TableRow tablerow_2 = (TableRow)findViewById(R.id.row_button);
                        tablerow_2.setVisibility(View.GONE);
                        tablerow_2.setBackgroundColor(Color.LTGRAY);

                        TableRow tablerow_4 = (TableRow)findViewById(R.id.row_48);
                        tablerow_4.setVisibility(View.VISIBLE);

                        for(row = 25; row <= 47; row++) {
                            String row_id = "row_"+  String.valueOf(row);

                            int row_ID = getResources().getIdentifier(row_id,"id",getPackageName());

                            TableRow tablerow_3 = (TableRow)findViewById(row_ID);
                            tablerow_3.setVisibility(View.VISIBLE);

                        }

                    }
                }
        );


        /* ----------------------- next next 24 hours--------------------------*/


    }

    private String printSUNdate(Date date,double org_offset){
         /*String time = "1369148661";
            long timestampLong = Long.parseLong(time)*1000;*/
           /* Date d = new Date(timestampLong);*/

        Calendar date_c = Calendar.getInstance();
        date_c.setTime(date);

        TimeZone tz = TimeZone.getDefault();
        int offset = tz.getRawOffset();

        int hour = date_c.get(Calendar.HOUR_OF_DAY);
        int min = date_c.get(Calendar.MINUTE);


        double new_offset = offset / 3600000 + (offset / 60000) % 60;

        hour = hour + (int)(org_offset - new_offset);

        DecimalFormat timeformat =new DecimalFormat("00");


        if(hour >= 12){
            hour -= 12;
            return timeformat.format(hour) + ":" + timeformat.format(min) + " PM";
        }else{
            return timeformat.format(hour) + ":" + timeformat.format(min) + " AM";
        }
    }


//    public void createTabs(){
//        TabHost mytab = (TabHost)findViewById(R.id.mytableHost);
//        mytab.setup();
//        TabHost.TabSpec specTab = mytab.newTabSpec("NEXT 24 HOURS");
//        specTab.setContent(R.id.hours_24);
//        specTab.setIndicator("NEXT 24 HOURS");
//        mytab.addTab(specTab);
//
//        specTab = mytab.newTabSpec("NEXT 7 DAYS");
//        specTab.setContent(R.id.days_7);
//        specTab.setIndicator("NEXT 7 DAYS");
//        mytab.addTab(specTab);
//    }
}
