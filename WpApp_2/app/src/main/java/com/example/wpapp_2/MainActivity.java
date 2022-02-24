package com.example.wpapp_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface{
    private EditText searchEdt;
    private ImageView searchIV;
    private RecyclerView categoryRV,wallpaperRV;
    private ProgressBar loadingPB;
    private ArrayList<String> wallpaperArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private WalpaperRVAdapter walpaperRVAdapter;
    //563492ad6f91700001000001a56f89fad4344c47ba7417aab55856c2



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEdt=findViewById(R.id.isEdtSearch);
        searchIV=findViewById(R.id.idIVsearch);
        categoryRV=findViewById(R.id.idRVCategory);
        wallpaperRV=findViewById(R.id.idRVWalpapers);
        loadingPB=findViewById(R.id.idPBLoading);

        wallpaperArrayList=new ArrayList<>();
        categoryRVModalArrayList=new ArrayList<>();

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
        categoryRV.setLayoutManager(linearLayoutManager);
        categoryRVAdapter=new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        categoryRV.setAdapter(categoryRVAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        wallpaperRV.setLayoutManager(gridLayoutManager);
        walpaperRVAdapter= new WalpaperRVAdapter(wallpaperArrayList,this);
        wallpaperRV.setAdapter(walpaperRVAdapter);
        getCategories();
        getWallpapers();

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchStr= searchEdt.getText().toString();
                if(searchStr.isEmpty()){
                    Toast.makeText(MainActivity.this," arama işlemi yapınız",Toast.LENGTH_SHORT).show();
                }
                else{
                    getWallpapersByCategory(searchStr);

                }
            }
        });

    }
    private void getWallpapersByCategory(String category){
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url="https://api.pexels.com/v1/search?query=" +category+ "&per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray photoArray= null;
                loadingPB.setVisibility(View.GONE);
                try {
                    //JSONArray photoArray= response.getJSONArray("photos");
                    photoArray= response.getJSONArray("photos");

                    for(int i=0;i<photoArray.length();i++){
                        JSONObject photoObj=photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    walpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"hatalı yükleme",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers= new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001a56f89fad4344c47ba7417aab55856c2");
                return headers;

            }

        };
        requestQueue.add(jsonObjectRequest);




    }

    private void getWallpapers() {
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url="https://api.pexels.com/v1/curated?per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try{
                    JSONArray photoArray= response.getJSONArray("photos");
                    for(int i=0;i<photoArray.length();i++){
                        JSONObject photoObj=photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    walpaperRVAdapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"hatalı yükleme",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers= new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001a56f89fad4344c47ba7417aab55856c2");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("Doğa",
                "https://images.pexels.com/photos/15286/pexels-photo.jpg?cs=srgb&dl=pexels-luis-del-r%C3%ADo-15286.jpg&fm=jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Animals",
                "https://images.pexels.com/photos/5060270/pexels-photo-5060270.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"));
        categoryRVModalArrayList.add(new CategoryRVModal("Cars",
                "https://images.pexels.com/photos/831475/pexels-photo-831475.jpeg?cs=srgb&dl=pexels-charles-kettor-831475.jpg&fm=jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Resim",
                "https://images.pexels.com/photos/1646953/pexels-photo-1646953.jpeg?cs=srgb&dl=pexels-deeana-creates-1646953.jpg&fm=jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Müzik",
                "https://images.pexels.com/photos/1389429/pexels-photo-1389429.jpeg?cs=srgb&dl=pexels-elviss-railijs-bit%C4%81ns-1389429.jpg&fm=jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology",
                "https://images.unsplash.com/photo-1593642532454-e138e28a63f4?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2000&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("Seyahat",
                "https://images.pexels.com/photos/2245436/pexels-photo-2245436.png?cs=srgb&dl=pexels-amine-m%27siouri-2245436.jpg&fm=jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("architecture",
                "https://images.pexels.com/photos/137594/pexels-photo-137594.jpeg?cs=srgb&dl=pexels-scott-webb-137594.jpg&fm=jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Çiçekler",
                "https://images.pexels.com/photos/1390433/pexels-photo-1390433.jpeg?cs=srgb&dl=pexels-lilartsy-1390433.jpg&fm=jpg"));
        categoryRVAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCategoryClick(int position) {

        String category = categoryRVModalArrayList.get(position).getCategory();
        getWallpapersByCategory(category);

    }
}