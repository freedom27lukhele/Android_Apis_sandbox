package com.example.androidapis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapis.api.RetrofitApi;
import com.example.androidapis.models.RecyclerData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtJob;
    private Button btnPost;
    private TextView tvResponse;

    private ProgressBar loadingPB;
    // creating a variable for recycler view,
    // array list and adapter class.
    private RecyclerView courseRV;
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseRV = findViewById(R.id.idRVCourse);
//        progressBar = findViewById(R.id.idPBLoading);

        recyclerDataArrayList = new ArrayList<>();

        //calling all the methods to get all this courses
        getAllCourses();

    }

    private void getAllCourses() {
// on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonkeeper.com/b/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);

        Call<ArrayList<RecyclerData>> call = retrofitAPI.getAllCourses();
        call.enqueue(new Callback<ArrayList<RecyclerData>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerData>> call, Response<ArrayList<RecyclerData>> response) {

                if (response.isSuccessful()) {
                 //   progressBar.setVisibility(View.GONE);
                    recyclerDataArrayList = response.body();

                    for (int i = 0; i < recyclerDataArrayList.size(); i++) {
                        recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, MainActivity.this);

                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                        courseRV.setLayoutManager(manager);
                        courseRV.setAdapter(recyclerViewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}