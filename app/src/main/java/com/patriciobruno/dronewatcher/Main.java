package com.patriciobruno.dronewatcher;

import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navView;
    DrawerLayout drawerLayout;
    LinearLayout content;
    TextView time;
    RelativeLayout navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*navHeader = ( RelativeLayout ) findViewById( R.id.headerBackground);*/
        time = (TextView ) findViewById( R.id.timeView);
        SetToolbar();
        SetNavDrawer();
        setHourView();
        /*updateHeaderBackground();*/
    }

    @Override
    protected void onResume() {
        setHourView();
//        updateHeaderBackground();
        super.onResume();
    }

    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void SetNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content = (LinearLayout)findViewById( R.id.content);
        navView = (NavigationView) findViewById(R.id.navigation);
        navView.getHeaderView(0).setBackgroundResource(updateHeaderBackground());
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked( true );
                Snackbar.make(content, item.getTitle()+"pressed", Snackbar.LENGTH_LONG).show();
                //onNavDrawerItemSelected(item);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void onNavDrawerItemSelected(MenuItem item ) {
        switch (item.getItemId()) {
        }
    }

    public int updateHeaderBackground () {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String now = dateFormat_hora.format(data_atual);
        int time = Integer.parseInt( now );

        if ( time <= 12 ) {
             return R.drawable.day_wallpaper;
        } else {
            if ( time < 18) {
                return R.drawable.afternoon_wallpaper;
            } else return R.drawable.night_wallpaper;
        }
    }

    public void updateHour () {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String now = dateFormat_hora.format(data_atual);
        time.setText(now.toString());
    }

    public void setHourView () {
        final Handler h = new Handler();
        final int delay = 1000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                updateHour();
                h.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if ( drawerLayout != null) {
                    drawerLayout.openDrawer( GravityCompat.START );
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
