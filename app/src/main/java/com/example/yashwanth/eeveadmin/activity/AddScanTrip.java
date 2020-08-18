package com.example.yashwanth.eeveadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yashwanth.eeveadmin.AdminHome;
import com.example.yashwanth.eeveadmin.Config;
import com.example.yashwanth.eeveadmin.R;
import com.example.yashwanth.eeveadmin.RequestHandler;
import com.example.yashwanth.eeveadmin.helper.SQLiteHandler;
import com.example.yashwanth.eeveadmin.helper.SessionManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;

public class AddScanTrip extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private String email_id;

    public SQLiteHandler db;
    public SessionManager session;

    private String regis_no;

    private String hotel_id;
    private String user_id;

    private Spinner spinnerVehicles;
    private ArrayList<String> vehicles;

    private Button buttonScanQR;
    private Button buttonAddManually;
    private Button buttonStartTrip;

    private EditText editTextVehicleName;
    private EditText editTextVehicleRegNo;
    private EditText editTextPhoneNumber;

    private View navHeader;
    private TextView textViewUser;
    private TextView textViewEmail;

    private String JSON_STRING;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scan_trip);

        buttonScanQR = (Button) findViewById(R.id.buttonScanQR);
        buttonAddManually = (Button) findViewById(R.id.buttonAddManually);
        buttonStartTrip = (Button) findViewById(R.id.buttonStartTrip);
        editTextVehicleName = (EditText) findViewById(R.id.editTextVehicleName);
        editTextVehicleRegNo = (EditText) findViewById(R.id.editTextVehicleRegNo);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);

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

        buttonAddManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPhoneNumber.setVisibility(View.VISIBLE);
                buttonStartTrip.setVisibility(View.VISIBLE);
                buttonAddManually.setVisibility(View.GONE);

            }
        });

        buttonStartTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    user_id = editTextPhoneNumber.getText().toString().trim();
                    addStatus();
            }
        });



        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();

        // session manager

        if (!session.isLoggedIn()) {
            signOut();
        }


        String name = user.get("name");
        String email = user.get("email");

        email_id = email;





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeader = navigationView.getHeaderView(0);
        textViewUser = (TextView) navHeader.findViewById(R.id.textViewUser);
        textViewEmail = (TextView) navHeader.findViewById(R.id.textViewEmail);

        textViewUser.setText(name);
        textViewEmail.setText(email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_scan_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i= new Intent(getApplicationContext(), AdminHome.class);
            startActivity(i);
        }

        else if (id == R.id.nav_hotel_profile) {
            Intent i= new Intent(getApplicationContext(), HotelProfile.class);
            startActivity(i);

        }

        else if (id == R.id.activeTrips) {
            Intent i= new Intent(getApplicationContext(), ViewTrips.class);
            startActivity(i);
        }

        else if (id == R.id.TripHistory1) {
            Intent i= new Intent(getApplicationContext(), TripHistory.class);
            startActivity(i);
        }
        else if (id == R.id.viewClients) {
            Intent i= new Intent(getApplicationContext(), ViewClients.class);
            startActivity(i);
        }

        else if (id == R.id.nav_logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {

        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent i= new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
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

        class AddTrips extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(com.example.yashwanth.eeveadmin.activity.AddScanTrip.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(com.example.yashwanth.eeveadmin.activity.AddScanTrip.this,s,Toast.LENGTH_LONG).show();
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
