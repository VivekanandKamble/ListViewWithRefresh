package com.vivek.listviewwithrefresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView listViewData;
    SwipeRefreshLayout pullToRefresh;
    String webservice="https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";
    String[] strings_Title,strings_Description,strings_Image;
    String strings_MainTitle;
    ArrayList<Data> data_list=new ArrayList<Data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewData=findViewById(R.id.listViewData);
        pullToRefresh=findViewById(R.id.pullToRefresh);

        ShowInformation();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                if (pullToRefresh.isRefreshing()) {
                    pullToRefresh.setRefreshing(false);
                }
                else
                {
                    ShowInformation();
                }
            }
        });

    }

    private void ShowInformation()
    {
        StringRequest getInformation = new StringRequest(Request.Method.GET, webservice, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseShowData) {
                System.out.println("Response JSON :- " + responseShowData);

                try {

                    JSONObject jsonObject=new JSONObject(responseShowData);
                    strings_MainTitle=jsonObject.get("title").toString();
                    getSupportActionBar().setTitle(strings_MainTitle);
                    JSONArray jsonArray =new JSONArray(jsonObject.get("rows").toString());

                    strings_Title=new String[jsonArray.length()];
                    strings_Description=new String[jsonArray.length()];
                    strings_Image=new String[jsonArray.length()];

                    System.out.println("Json Array Length: "+ jsonArray.length());
                    System.out.println("Json Array : "+ jsonArray.toString());

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        strings_Title[i]=obj.getString("title");
                        strings_Description[i]=obj.getString("description");
                        strings_Image[i]=obj.getString("imageHref");

                        System.out.println("Data Number : "+ i);
                        System.out.println("Data Title : "+ strings_Title[i]);
                        System.out.println("Data Description : "+ strings_Description[i]);
                        System.out.println("Data Image: "+ strings_Image[i]);

                        Data data= new Data(strings_Title[i],strings_Description[i],strings_Image[i]);
                        data_list.add(data);
                    }

                    Adapter adapter =new Adapter(MainActivity.this,data_list);
                    listViewData.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Volley Error : " + error.getMessage());
                        ShowInformation();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(getInformation);

    }
}
