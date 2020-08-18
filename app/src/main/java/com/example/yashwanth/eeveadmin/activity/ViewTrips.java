package com.example.yashwanth.eeveadmin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.yashwanth.eeveadmin.AdminHome;
import com.example.yashwanth.eeveadmin.Config;
import com.example.yashwanth.eeveadmin.R;
import com.example.yashwanth.eeveadmin.RequestHandler;
import com.example.yashwanth.eeveadmin.helper.SQLiteHandler;
import com.example.yashwanth.eeveadmin.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewTrips extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListView.OnItemClickListener {

    public SQLiteHandler db;
    public SessionManager session;
    private ListView listViewVehicle;
    private String JSON_STRING;
    private String email_id;


    private View navHeader;
    private TextView textViewUser;
    private TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        listViewVehicle = (ListView) findViewById(R.id.listViewTrips);
        listViewVehicle.setOnItemClickListener(this);




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

        getJSON();
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
        getMenuInflater().inflate(R.menu.view_trips, menu);
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
        } else if (id == R.id.nav_hotel_profile) {
            Intent i= new Intent(getApplicationContext(), HotelProfile.class);
            startActivity(i);

        }

        else if (id == R.id.addTrip) {
            Intent i= new Intent(getApplicationContext(), AddScanTrip.class);
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

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String trip_id = jo.getString(Config.TAG_TRIP_ID);
                String reg_no = jo.getString(Config.TAG_REG_NO);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_TRIP_ID,trip_id);
                employees.put(Config.TAG_REG_NO,reg_no);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewTrips.this, list, R.layout.list_item,
                new String[]{Config.TAG_TRIP_ID,Config.TAG_REG_NO},
                new int[]{R.id.id, R.id.name});

        listViewVehicle.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewTrips.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                //String s = rh.sendGetRequest(Config.URL_GET_ALL_ACTIVE_TRIPS+email_id+"\"");

                final String email = email_id;

                HashMap<String,String> params2 = new HashMap<>();
                params2.put("name",email);
                params2.put("email",email);

                String res = rh.sendPostRequest(Config.URL_GET_ALL_ACTIVE_TRIPS, params2);
                return res;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ManageTrip.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String tripId = map.get(Config.TAG_TRIP_ID).toString();
        intent.putExtra(Config.TAG_TRIP_ID,tripId);
        Log.e("Trip id", tripId);
        startActivity(intent);
    }
}
