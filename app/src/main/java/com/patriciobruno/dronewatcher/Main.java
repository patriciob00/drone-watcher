package com.patriciobruno.dronewatcher;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patriciobruno.dronewatcher.fragments.flights;
import com.patriciobruno.dronewatcher.fragments.friends;
import com.patriciobruno.dronewatcher.fragments.localsFrag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navView;
    DrawerLayout drawerLayout;
    CoordinatorLayout content;
    TextView time;
    RelativeLayout navHeader;
    Intent data;
    TabLayout tab;
    ViewPager pager;
    PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SetToolbar();
        SetupTabs();
        SetNavDrawer();
    }

    @Override
    protected void onResume() {
//        updateHeaderBackground();
        super.onResume();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupViewPager( ViewPager viewPager ) {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new flights(), "VÔOS");
        adapter.addFrag(new localsFrag(), "LOCAIS");
        adapter.addFrag(new friends(), "AMIGOS");
        viewPager.setAdapter( adapter );
    }

    public void SetupTabs() {
        tab = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        setupViewPager( pager );
        tab.setupWithViewPager( pager );

    }


    public void SetNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content = (CoordinatorLayout) findViewById(R.id.content);
        navView = (NavigationView) findViewById(R.id.navigation);
        navView.getHeaderView(0).setBackgroundResource(updateHeaderBackground());
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MyDrones:
                        item.setChecked(true);
                        data = new Intent(Main.this, drones.class);
                        startActivity(data);
                        break;
                    case R.id.weather:
                        item.setChecked(true);
                        data = new Intent(Main.this, map.class);
                        data.putExtra("title", "Tempo");
                        startActivity(data);
                        break;
                    case R.id.places:
                        item.setChecked(true);
                        break;
                    case R.id.messages:
                        item.setChecked(true);
                        break;
                    case R.id.friends:
                        item.setChecked(true);
                        break;
                    case R.id.MyProfile:
                        item.setChecked(true);
                        break;
                    case R.id.settings:
                        item.setChecked(true);
                        break;
                }
                Snackbar.make(content, item.getItemId() + "pressed", Snackbar.LENGTH_LONG).show();
                //onNavDrawerItemSelected(item);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public int updateHeaderBackground() {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String now = dateFormat_hora.format(data_atual);
        int time = Integer.parseInt(now);

        if (time >= 5 && time <= 12) {
            return R.drawable.day_wallpaper;
        } else {
            if (time > 12 && time < 18) {
                return R.drawable.afternoon_wallpaper;
            } else return R.drawable.night_wallpaper;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        return true;
    }
}
