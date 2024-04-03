package com.example.androidapis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapis.api.RetrofitApi;
import com.example.androidapis.models.DataModal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText edtName,edtJob;
    private Button btnPost;
    private TextView tvResponse;

    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtJob = findViewById(R.id.idEdtJob);
        edtName = findViewById(R.id.idEdtName);
        tvResponse = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);
        btnPost = findViewById(R.id.idBtnPost);

        btnPost.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().isEmpty() && edtJob.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                }
                // calling a method to post the data and passing our name and job.

                postData(edtName.getText().toString(),edtJob.getText().toString());
            }
        });
    }

    private void postData(String name,String job){
        loadingPB.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        DataModal modal = new DataModal(name,job);

        Call<DataModal> call = retrofitApi.createPost(modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {

                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                loadingPB.setVisibility(View.GONE);

                edtJob.setText("");
                edtName.setText("");

                DataModal responseFromAPI = response.body();


                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getJob();

                tvResponse.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

                tvResponse.setText(String.format("%s%s", getString(R.string.error_found_is), t.getMessage()));
            }
        });
    }
}