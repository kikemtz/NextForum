package com.daavm.nextbit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class wikiHardware extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public void home(View view)
    {
        Intent intent = new Intent(wikiHardware.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean midnightTheme = preferences.getBoolean("midnightTheme", false);
        boolean mintTheme = preferences.getBoolean("mintTheme", false);
        boolean electricTheme = preferences.getBoolean("electricTheme", false);

        if(midnightTheme) {
            setTheme(R.style.Midnight);
        }
        else if(mintTheme) {
            setTheme(R.style.Mint);
        }
        else if(electricTheme) {
            setTheme(R.style.Electric);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_hardware);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView2 = (NavigationView) findViewById(R.id.NavigationView);
        navigationView2.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks herex.
        int id = item.getItemId();

        if (id == R.id.store) {
            Uri uri = Uri.parse("https://www.nextbit.com/collections/all"); // missing 'http://' will cause crashed
            Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent1);
        } else if (id == R.id.nav_wiki) {
            Intent intent2 = new Intent(wikiHardware.this,wiki.class);
            startActivity(intent2);
        } else if (id == R.id.nav_discover) {
            Uri uri = Uri.parse("https://nextbit.com"); // missing 'http://' will cause crash
            Intent intent5 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent5);
        } else if (id == R.id.nav_themes) {
            Intent intent2 = new Intent(wikiHardware.this,Preferences.class);
            startActivity(intent2);
        } else if (id == R.id.Community) {
            Intent intent2 = new Intent(wikiHardware.this,MainActivity.class);
            startActivity(intent2);
        }  else if (id == R.id.messages) {
            Intent intent2 = new Intent(wikiHardware.this,Messages.class);
            startActivity(intent2);
        }  else if (id == R.id.notifications) {
            Intent intent2 = new Intent(wikiHardware.this,Notifications.class);
            startActivity(intent2);
        }  else if (id == R.id.signin) {
            Intent intent2 = new Intent(wikiHardware.this,Login.class);
            startActivity(intent2);;
        }   else if (id == R.id.forumsettings) {
            Intent intent2 = new Intent(wikiHardware.this,forumsettings.class);
            startActivity(intent2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

