package com.example.yashwanth.eeveadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yashwanth.eeveadmin.Config;
import com.example.yashwanth.eeveadmin.R;
import com.example.yashwanth.eeveadmin.RequestHandler;
import com.example.yashwanth.eeveadmin.helper.SQLiteHandler;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddTrip extends AppCompatActivity {


    private String email_id;
    public SQLiteHandler db;

    private String regis_no;

    private String hotel_id;
    private String user_id;

    private Spinner spinnerVehicles;
    private ArrayList<String> vehicles;

    private Button buttonScanQR;

    private EditText editTextVehicleName;
    private EditText editTextVehicleRegNo;


    private String JSON_STRING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        buttonScanQR = (Button) findViewById(R.id.buttonScanQR);
        editTextVehicleName = (EditText) findViewById(R.id.editTextVehicleName);
        editTextVehicleRegNo = (EditText) findViewById(R.id.editTextVehicleRegNo);

        final Activity activity = this;
        buttonScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent i= new Intent(getApplicationContext(), AddTrip.class);
                // startActivity(i);

                IntentIntegrator integrator = new IntentIntegrator(activity);

                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });




        db = new SQLiteHandler(getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        email_id = email;








    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                //addStatus();
            }
            else {
                //Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();

                String results = result.getContents();
                String[] resultArray = results.split("/");
                if( resultArray[0].equals("authqruser")){
                    user_id = resultArray[1];
                    addStatus();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void addStatus(){

        final String user_id = this.user_id;
        final String hotel_id_no = email_id;
        final String reg_no = editTextVehicleName.getText().toString().trim() + "-" + editTextVehicleRegNo.getText().toString().trim();
       /* final String user_id = email_id;
        final String hotel_id_no = "32458";
        final String reg_no = "TN002";*/
        //final String desg = editTextDesg.getText().toString().trim();
        //final String sal = editTextSal.getText().toString().trim();

        class AddTrips extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(com.example.yashwanth.eeveadmin.activity.AddTrip.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(com.example.yashwanth.eeveadmin.activity.AddTrip.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("hotel_id",hotel_id_no);
                params.put("reg_no",reg_no);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_TRIP, params);
                return res;

            }
        }

        AddTrips ae = new AddTrips();
        ae.execute();
    }
}
