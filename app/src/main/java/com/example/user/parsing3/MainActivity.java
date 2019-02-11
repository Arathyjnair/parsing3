package com.example.user.parsing3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AsyncHttpClient client;
    RequestParams params;
    JSONObject jobject;
    JSONArray jarray;


    ArrayList<String> titlee;
    ArrayList<String> categoryy;
    ArrayList<String> datee;
    ArrayList<String> imagey;

    RecyclerView recyrvw;
    Verticaladapter adapt;
    ImageView imageyA;

    LinearLayoutManager layutmanager;

    String url = "https://thecity247.com/api/get_posts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyrvw = findViewById(R.id.recyclervw);

        client = new AsyncHttpClient();
        params = new RequestParams();

        titlee = new ArrayList<String>();
        categoryy = new ArrayList<String>();
        datee = new ArrayList<String>();
        imagey = new ArrayList<String>();
        Log.e("kkkk","jjjjj");
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);

                try {
                    Log.e("kkkk","out");

                    jobject = new JSONObject(content);
                    if (jobject.getString("status").equals("ok")) {
                        jarray = jobject.getJSONArray("posts");
                        Log.e("kkkk","in");

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject obj1 = jarray.getJSONObject(i);
                            titlee.add(obj1.getString("title"));
                            datee.add(obj1.getString("date"));

                            Log.e("kkkk","inside");
                            JSONArray jarray1 = obj1.getJSONArray("categories");

                            for (int j = 0; j < jarray1.length(); j++) {
                                JSONObject obj3 = jarray1.getJSONObject(j);

                                categoryy.add(obj3.getString("title"));
                            }
                            JSONArray jarray2 = obj1.getJSONArray("attachments");

                            for (int k = 0; k < jarray2.length(); k++) {
                                JSONObject obj4 = jarray2.getJSONObject(k);

                                imagey.add(obj4.getString("url"));
                            }
                        }
                    }
                    adapt=new Verticaladapter(categoryy);
                    layutmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyrvw.setLayoutManager(layutmanager);
                    recyrvw.setAdapter(adapt);


                } catch (Exception e) {
                }
            }
        });
    }

    class Verticaladapter extends RecyclerView.Adapter<Verticaladapter.MyViewHolder> {
        private List<String> vrticallist;

        Verticaladapter(List<String> vrticallist)


        {
            this.vrticallist = datee;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView titleA, categoryA, dateeA;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                titleA = itemView.findViewById(R.id.txt1);
                categoryA =itemView.findViewById(R.id.txt2);
                dateeA = itemView.findViewById(R.id.dte);
                imageyA = itemView.findViewById(R.id.img1);
            }
        }

        @NonNull
        @Override
        public Verticaladapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single, viewGroup, false);
            return new MyViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull Verticaladapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.titleA.setText(titlee.get(i));
            myViewHolder.dateeA.setText(datee.get(i));
            myViewHolder.categoryA.setText(categoryy.get(i));
            Log.e("cccc"," "+titlee);
           Picasso.with(getApplicationContext())
                    .load(imagey.get(i))
                    .into(imageyA);


        }

        @Override
        public int getItemCount() {
            return datee.size();
        }
    }
}


