package com.example.chen.hw9_2;

import android.os.AsyncTask;
import android.os.Bundle;

//import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.app.Activity;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;*/


/*import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;*/

public class weather extends AppCompatActivity {

    String jsonURL = "http://xcxcxc033-env.elasticbeanstalk.com/?address="; //http://cs-server.usc.edu:20198/index.php/?streets=";

    //String jsonURL = "http://cs-server.usc.edu:20198/index_1.php/?street=";
    //String jsonURL = "http://cs-server.usc.edu:20198/index_1.php/?street=";
    //1248%20W%20ADAMS=Los%20Angeles&state=CA&degree=si";


    // google map api AIzaSyBZhK83icd6RtMezUxDyLZEfbl4w7vH3oU

    String street_s,city_s,state_s,degree_s;
    public TextView mytst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather);

        mytst = (TextView)findViewById(R.id.redDATA);
        Button searchbutton = (Button)findViewById(R.id.button_search);

        everyClickListener();

        searchbutton.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View view) {

                        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
                        String degree = ((RadioButton)findViewById(rg.getCheckedRadioButtonId() )).getText().toString();
                        if(degree.equals("Celsius")){
                            degree_s = "si";
                        }else{
                            degree_s = "us";
                        }

                        TextView redtxt = (TextView)findViewById(R.id.redDATA);

                        EditText street = (EditText) findViewById(R.id.street_input);
                        street_s = street.getText().toString();


                        jsonURL = jsonURL + street_s + "&city=";

                        EditText city = (EditText) findViewById(R.id.city_input);
                        city_s = city.getText().toString();
                        jsonURL = jsonURL + city_s + "&state=";


                        Spinner state = (Spinner) findViewById(R.id.state);
                        state_s = state.getSelectedItem().toString();
                        jsonURL = jsonURL + state_s + "&degree=" + degree_s;


                        // jsonURL = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";
                        jsonURL = jsonURL.replace(" ","%20");


                        if(street_s.equals("")){
                            redtxt.setText("Please enter Street Address");
                            return;
                        }else if(city_s.equals("")){
                            redtxt.setText("Please enter a City");
                            return;
                        }else if(state_s.equals("Select")){
                            redtxt.setText("Please select a State");
                            return;
                        }else{
                            redtxt.setText("");
                            new handleMainForJson().execute(jsonURL);
                        }

                        //mytst.setText(degree_s);

                    }


                    // googleURL = "xxxx";
                }
        );
    }

    private void everyClickListener(){


        Button icon = (Button)findViewById(R.id.iconbtn);
        icon.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View view) {

                        Uri uri = Uri.parse("http://forecast.io"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                   }
                }
        );

        Button about = (Button)findViewById(R.id.button);
        about.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View view) {


                        Intent aboutactivity = new Intent(weather.this, AboutActivity.class);
                        startActivity(aboutactivity);
                    }
                }
        );



        Button clearBtn = (Button)findViewById(R.id.button_clear);

        clearBtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        EditText street = (EditText) findViewById(R.id.street_input);
                        street.setText("");

                        EditText city = (EditText) findViewById(R.id.city_input);
                        city.setText("");

                        Spinner spinner = (Spinner) findViewById(R.id.state);

                        spinner.setSelection(0);


                        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
                        rg.check(R.id.us);

                        TextView redtxt = (TextView)findViewById(R.id.redDATA);
                        redtxt.setText("");
                    }


                    // googleURL = "xxxx";
                }
        );

    }

    public class handleMainForJson extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection jsConnect = null;
            BufferedReader jsReader = null;

            try {
                URL url = new URL(params[0].toString()); //"http://xcxcxc033-env.elasticbeanstalk.com/?address=1248 w adams&city=LA&state=CA&degree=si"
                //URL url = new URL("http://xcxcxc033-env.elasticbeanstalk.com/?address=1248%20w%20adams&city=LA&state=CA&degree=si");
                jsConnect = (HttpURLConnection)url.openConnection();
                // jsConnect.connect();
                InputStream readbuffer = new BufferedInputStream(jsConnect.getInputStream());
                jsReader = new BufferedReader(new InputStreamReader(readbuffer));
                StringBuffer jsbuffer = new StringBuffer();
                String jscontent = "";
                while((jscontent = jsReader.readLine()) != null){
                    jsbuffer.append(jscontent);
                }


                return jsbuffer.toString();
                // ResultActivity object_1 = new ResultActivity();
                // object_1.storeJSON(jsbuffer.toString());
                // return jsbuffer.toString();


              /*  Intent resultactivity = new Intent(wheather.this, ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("JSON_file",jsbuffer.toString());
                bundle.putString("street_name",street_s);
                bundle.putString("city_name",city_s);
                bundle.putString("state_name",state_s);
                bundle.putString("degree_name",degree_s);

                resultactivity.putExtras(bundle);
                startActivity(resultactivity);*/



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(jsConnect != null)jsConnect.disconnect();
                try {
                    if(jsReader != null)jsReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            //mytst.setText(str);
            Intent resultactivity = new Intent(weather.this, ResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("JSON_file",str);
            bundle.putString("street_name",street_s);
            bundle.putString("city_name",city_s);
            bundle.putString("state_name",state_s);
            bundle.putString("degree_name",degree_s);

            resultactivity.putExtras(bundle);
            startActivity(resultactivity);

        }
    }


}
