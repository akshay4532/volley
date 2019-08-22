package com.example.volley;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextInputEditText textInputEditText;
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.name);
        textInputEditText=findViewById(R.id.edit);
        button=findViewById(R.id.data);
        imageView=findViewById(R.id.img);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callVolley(textInputEditText.getText().toString().trim());
            }
        });

    }
    public  void callVolley(String id){
        String url="https://api.github.com/users/"+id;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String name=jsonObject.getString("name");
                    String imgurl=jsonObject.getString("avatar_url");
                    textView.setText(name);
                    Glide.with(MainActivity.this).load(imgurl).into(imageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.getMessage());
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
