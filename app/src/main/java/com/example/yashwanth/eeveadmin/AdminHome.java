package com.example.yashwanth.eeveadmin;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.yashwanth.eeveadmin.activity.AddScanTrip;
import com.example.yashwanth.eeveadmin.activity.AddTrip;
import com.example.yashwanth.eeveadmin.activity.HotelProfile;
import com.example.yashwanth.eeveadmin.activity.LoginActivity;
import com.example.yashwanth.eeveadmin.activity.MainActivity;
import com.example.yashwanth.eeveadmin.activity.TripHistory;
import com.example.yashwanth.eeveadmin.activity.ViewClients;
import com.example.yashwanth.eeveadmin.activity.ViewTrips;
import com.example.yashwanth.eeveadmin.helper.SQLiteHandler;
import com.example.yashwanth.eeveadmin.helper.SessionManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;

public class AdminHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public SQLiteHandler db;
    public SessionManager session;
    private Button buttonAddtrip;
    private Button buttonCloseTrip;
    private Button buttonViewClients;
    private Button buttonTripHistory;

    private View navHeader;
    private TextView textViewUser;
    private TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        buttonAddtrip = (Button) findViewById(R.id.buttonAddtrip);
        buttonCloseTrip = (Button) findViewById(R.id.buttonCloseTrip);
        buttonViewClients = (Button) findViewById(R.id.buttonViewClients);
        buttonTripHistory = (Button) findViewById(R.id.buttonTripHistory);

        buttonAddtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getApplicationContext(), AddScanTrip.class);
                startActivity(i);

            }
        });

        buttonCloseTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getApplicationContext(), ViewTrips.class);
                startActivity(i);
            }
        });

        buttonViewClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getApplicationContext(), ViewClients.class);
                startActivity(i);
            }
        });

        buttonTripHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getApplicationContext(), TripHistory.class);
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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


        //NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);




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
        getMenuInflater().inflate(R.menu.admin_home, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }else if (id == R.id.nav_hotel_profile) {
            Intent i= new Intent(getApplicationContext(), HotelProfile.class);
            startActivity(i);

        }

        else if (id == R.id.addTrip) {
            Intent i= new Intent(getApplicationContext(), AddScanTrip.class);
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

}
