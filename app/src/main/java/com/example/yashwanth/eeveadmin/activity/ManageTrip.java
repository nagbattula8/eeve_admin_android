package com.example.yashwanth.eeveadmin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yashwanth.eeveadmin.AdminHome;
import com.example.yashwanth.eeveadmin.Config;
import com.example.yashwanth.eeveadmin.R;
import com.example.yashwanth.eeveadmin.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ManageTrip extends AppCompatActivity {

    private EditText editTripId;
    private EditText editTextUserID;
    private EditText editTextHotelID;
    private EditText editTextModel;
    private EditText editTextRegNo;
    private EditText editTextStatus;
    private EditText editTextStart;

    private Button buttonCloseTrip;
    private Button buttonDelete;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_trip);


        Intent intent = getIntent();

        id = intent.getStringExtra(Config.TAG_TRIP_ID);

        Log.e("id", id);

        editTripId = (EditText) findViewById(R.id.editTripId);
        editTextUserID = (EditText) findViewById(R.id.editTextUserID);
        editTextHotelID = (EditText) findViewById(R.id.editTextHotelID);
        editTextModel = (EditText) findViewById(R.id.editTextModel);
        editTextRegNo = (EditText) findViewById(R.id.editTextRegNo);
        editTextStatus = (EditText) findViewById(R.id.editTextStatus);
        editTextStart = (EditText) findViewById(R.id.editTextStart);

        editTripId.setText(id);


        buttonCloseTrip = (Button) findViewById(R.id.buttonCloseTrip);

        getEmployee();
        //showVehicleDetails();

        buttonCloseTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateEmployee();
                startActivity(new Intent(getApplicationContext(),AdminHome.class));

            }
        });
    }


    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ManageTrip.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                HashMap<String,String> params2 = new HashMap<>();
                params2.put("id",id);

                RequestHandler rh2 = new RequestHandler();
                String res = rh2.sendPostRequest(Config.URL_GET_ACTIVE_TRIP, params2);
                return res;


            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }


    private void showVehicleDetails(){


        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
               // super.onPreExecute();
               // loading = ProgressDialog.show(ManageTrip.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
               // super.onPostExecute(s);
                //loading.dismiss();
                //Toast.makeText(ManageTrip.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("name","nag1111");

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_SAMPLE_POST, params);
                return res;

            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }


    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            Log.e("JSON onject", c.toString());

            String user_id = c.getString(Config.TAG_USER_ID);
            String hotel_id = c.getString(Config.TAG_HOTEL_ID);
            String model = c.getString(Config.TAG_MODEL);
            String reg_no = c.getString(Config.TAG_REG_NO);
            String status = c.getString(Config.TAG_STATUS);
            String start = c.getString(Config.TAG_START);
           //String sal = c.getString(Config.TAG_SAL);

            editTextUserID.setText(user_id);
            editTextHotelID.setText(hotel_id);
            editTextModel.setText(model);
            editTextRegNo.setText(reg_no);
            editTextStatus.setText(status);
            editTextStart.setText(start);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(){


        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ManageTrip.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ManageTrip.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.TAG_ID,id);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_CLOSE_TRIP,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

}
