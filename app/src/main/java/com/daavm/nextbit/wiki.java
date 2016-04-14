package com.daavm.nextbit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.WebView;

public class wiki extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    
    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }
    public void FAQ(View view)
    {
        Intent intent = new Intent(wiki.this,wikiFAQ.class);
        startActivity(intent);
    }
    public void Hardware(View view)
    {
        Intent intent = new Intent(wiki.this,wikiHardware.class);
        startActivity(intent);
    }
    public void home(View view)
    {
        Intent intent = new Intent(wiki.this,MainActivity.class);
        startActivity(intent);
    }
    public void AboutN(View view)
    {
        Intent intent = new Intent(wiki.this,wikiAbout.class);
        startActivity(intent);
    }
    public void history(View view)
    {
        Intent intent = new Intent(wiki.this,wikiHistory.class);
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
        if (isFirstTime()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Welcome to the official Wiki!");
            alertDialog.setMessage("Hi, here you'll find all info related to Nextbit's Robin. That's the official wiki made by community, and now I bring it to you adapted on an Android app!");
            alertDialog.setButton("Nice, thanks!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.setIcon(R.drawable.wikialert);
            alertDialog.show();
        }
        setContentView(R.layout.activity_wiki);
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
            drawer.openDrawer(GravityCompat.START);
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
            Intent intent2 = new Intent(wiki.this,wiki.class);
            startActivity(intent2);
        } else if (id == R.id.nav_discover) {
            Uri uri = Uri.parse("https://nextbit.com"); // missing 'http://' will cause crash
            Intent intent5 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent5);
        } else if (id == R.id.nav_themes) {
            Intent intent2 = new Intent(wiki.this,Preferences.class);
            startActivity(intent2);
        } else if (id == R.id.Community) {
            Intent intent2 = new Intent(wiki.this,MainActivity.class);
            startActivity(intent2);
        }  else if (id == R.id.messages) {
            Intent intent2 = new Intent(wiki.this,Messages.class);
            startActivity(intent2);
        }  else if (id == R.id.notifications) {
            Intent intent2 = new Intent(wiki.this,Notifications.class);
            startActivity(intent2);
        }  else if (id == R.id.signin) {
            Intent intent2 = new Intent(wiki.this,Login.class);
            startActivity(intent2);;
        }   else if (id == R.id.forumsettings) {
            Intent intent2 = new Intent(wiki.this,forumsettings.class);
            startActivity(intent2);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
