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
                // this method is called when we get response from our api.
                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.
                loadingPB.setVisibility(View.GONE);

                // on below line we are setting empty text
                // to our both edit text.
                edtJob.setText("");
                edtName.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                DataModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getJob();

                // below line we are setting our
                // string to our text view.
                tvResponse.setText(responseString);
            }
            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                tvResponse.setText("Error found is : " + t.getMessage());
            }
        });
    }
}