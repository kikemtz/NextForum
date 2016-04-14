package com.daavm.nextbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.pushbots.push.Pushbots;

public class Notifications extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        Pushbots.sharedInstance().init(this);

        setContentView(R.layout.activity_main);
        WebView myWebView = (WebView) this.findViewById(R.id.webView);

        WebSettings webSettings = myWebView.getSettings();

        myWebView.setWebViewClient(new WebViewClient ());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setBuiltInZoomControls(true);
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        myWebView.getSettings().setUserAgentString(newUA);
        final Activity activity = this;
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        myWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);
                if(progress == 100)
                    activity.setTitle("Forum");
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myWebView.setVisibility(View.INVISIBLE);
        myWebView.setWebViewClient(new WebViewClient() {
            final WebView myWebView = (WebView)findViewById(R.id.webView);
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.equals("https://community.nextbit.com")){
                    //initial url load, ignoring.
                    myWebView.setVisibility(View.INVISIBLE);

                }
                else {
                    //url was navigated to - do something
                    myWebView.setVisibility(View.INVISIBLE);

                }

                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageFinished(WebView view, String url){
                myWebView.loadUrl("javascript:if (typeof(document.getElementsByClassName('lia-quilt-row-main-header')[0]) != 'undefined' && document.getElementsByClassName('lia-quilt-row-main-header')[0] != null){document.getElementsByClassName('lia-quilt-row-main-header')[0].style.display = 'none';} void 0");
                myWebView.loadUrl("javascript:if (typeof(document.getElementsByClassName('nav-mobile')[0]) != 'undefined' && document.getElementsByClassName('nav-mobile')[0] != null){document.getElementsByClassName('nav-mobile')[0].style.display = 'none';} void 0");
                myWebView.loadUrl("javascript:if (typeof(document.getElementsByClassName('lia-quilt-row-hero')[0]) != 'undefined' && document.getElementsByClassName('lia-quilt-row-hero')[0] != null){document.getElementsByClassName('lia-quilt-row-hero')[0].style.display = 'none';} void 0");
                myWebView.loadUrl("javascript:if (typeof(document.getElementsByClassName('nav-links-wrapper')[0]) != 'undefined' && document.getElementsByClassName('nav-links-wrapper')[0] != null){document.getElementsByClassName('nav-links-wrapper')[0].style.display = 'none';} void 0");
                myWebView.setVisibility(View.VISIBLE);
            }
        });
        myWebView.loadUrl("https://community.nextbit.com/t5/notificationfeed/page");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView myWebView = (WebView) this.findViewById(R.id.webView);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                    } else {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        } else {
                            drawer.openDrawer(GravityCompat.START);
                        }
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            finish();
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }
    //WIKI PART
    public void FAQ(View view)
    {
        setContentView(R.layout.activity_wiki_faq);
    }
    public void Hardware(View view)
    {
        setContentView(R.layout.activity_wiki_faq_hardware);

    }
    public void home(View view)
    {
        setContentView(R.layout.activity_wiki);

    }
    public void AboutN(View view)
    {
        setContentView(R.layout.activity_wiki_about);

    }
    public void history(View view)
    {
        setContentView(R.layout.activity_wiki_history);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.store) {
            Uri uri = Uri.parse("https://www.nextbit.com/collections/all"); // missing 'http://' will cause crashed
            Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent1);
        } else if (id == R.id.nav_wiki) {
            Intent intent2 = new Intent(Notifications.this,wiki.class);
            startActivity(intent2);
        } else if (id == R.id.nav_discover) {
            Uri uri = Uri.parse("https://nextbit.com"); // missing 'http://' will cause crash
            Intent intent5 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent5);
        } else if (id == R.id.nav_themes) {
            Intent intent2 = new Intent(Notifications.this,Preferences.class);
            startActivity(intent2);
        } else if (id == R.id.Community) {
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.setVisibility(View.INVISIBLE);
            myWebView.loadUrl("https://community.nextbit.com");
        }  else if (id == R.id.messages) {
            Intent intent2 = new Intent(Notifications.this,Messages.class);
            startActivity(intent2);
        }  else if (id == R.id.notifications) {
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.setVisibility(View.INVISIBLE);
            myWebView.loadUrl("https://community.nextbit.com/t5/notificationfeed/page");
        }  else if (id == R.id.signin) {
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.setVisibility(View.INVISIBLE);
            myWebView.loadUrl("https://community.nextbit.com/t5/user/userloginpage?dest_url=https%3A%2F%2Fcommunity.nextbit.com%2F");
        }   else if (id == R.id.forumsettings) {
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.setVisibility(View.INVISIBLE);
            myWebView.loadUrl("https://community.nextbit.com/t5/user/myprofilepage/tab/personal-profile");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}