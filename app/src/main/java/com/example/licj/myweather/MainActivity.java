package com.example.licj.myweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.licj.myweather.volley.HTTPSTrustManager;
import com.example.licj.myweather.volley.ImageLoaderUtil;
import com.example.licj.myweather.volley.VolleyListenerInterface;
import com.example.licj.myweather.volley.VolleyRequestUtil;
import com.example.licj.myweather.model.*;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    public TextView tvTmp;
    public TextView tvCond_txt;
    public ImageView imCnod_code;
    public static String wheatherIcon="http://files.heweather.com/cond_icon/%s.png";

    public now nowW;//实时天气
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        VolleyGet();

    }

    // 网络请求标签为"abcGet"
    public void onStop() {
        super.onStop();
       // MyApp.getRequestQueue().cancelAll("hopePage");
    }

    private void ParseNow(JSONObject jData)
    {
        if(nowW==null) {
            nowW=new now();}
        try {
            nowW.fl = jData.getString("fl");
            nowW.tmp = jData.getString("tmp");
            nowW.vis = jData.getString("vis");
            nowW.pres = jData.getString("pres");
            nowW.pcpn = jData.getString("pcpn");
            cond _cond=new cond();
            _cond.code=jData.getJSONObject("cond").getString("code");
            _cond.txt=jData.getJSONObject("cond").getString("txt");
            nowW.cond=_cond;
            wind _wind=new wind();
            _wind.deg = jData.getJSONObject("wind").getString("deg");
            _wind.dir = jData.getJSONObject("wind").getString("deg");
            _wind.sc = jData.getJSONObject("wind").getString("deg");
            _wind.spd = jData.getJSONObject("wind").getString("deg");
            nowW.wind =_wind;
        }catch (Exception e)
        {
            e.printStackTrace ();
        }
        finally
        {

        }
    }

    private void initViews()
    {
        tvTmp=(TextView)findViewById(R.id.tmp);
        tvCond_txt=(TextView)findViewById(R.id.cond_txt);
        imCnod_code=(ImageView) findViewById(R.id.cond_img);
    }
    private void showInfo()
    {
        tvTmp.setText(nowW.tmp);
        tvCond_txt.setText(nowW.cond.txt);
        String iconUrl =  String.format(wheatherIcon,nowW.cond.code);
        new ImageLoaderUtil().setImageLoader(iconUrl, imCnod_code,R.drawable.dft,R.drawable.dft);
    }

    private void VolleyGet() {

        String wUrl="https://api.heweather.com/x3/weather?cityid=CN101021300&key=63319111568742b785c5a367170da8e5";
        if(wUrl.startsWith("https"))
        {
            HTTPSTrustManager.allowAllSSL();
        }
       // wUrl="http://www.baidu.com/";
        new VolleyRequestUtil().RequestGet(MainActivity.this,wUrl, "hopePage",
                new VolleyListenerInterface(MainActivity.this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        Log.v("LICJ",result);
                        String s="OKK";
                        Toast.makeText(MainActivity.this,"aa", Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jRoot = new JSONObject(result);
                            //JSONObject ss=jRoot.getJSONObject("JSONObject");
                            //JSONObject  HeW= jRoot.getJSONObject("HeWeather data service 3.0"); .getJSONObject("aqi")
                            JSONArray jarr = jRoot.getJSONArray("HeWeather data service 3.0");

                            JSONObject nowJson= jarr.getJSONObject(0).getJSONObject("now");
                            ParseNow(nowJson);
                            showInfo();
                            //JSONObject jcity=aqi.getJSONObject("city");
                            //tv1.setText(jcity.getString("aqi"));
                        }catch (Exception e)
                        {
                            e.printStackTrace ();
                        }
                        finally
                        {

                        }

                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        Log.v("LICJ","ERROR licj");

                    }
                });


        }


}
