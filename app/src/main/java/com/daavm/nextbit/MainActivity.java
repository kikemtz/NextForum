package com.daavm.nextbit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.pushbots.push.Pushbots;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
        if (isFirstTime()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Welcome to my app!");
            alertDialog.setMessage("Hi! Thanks for testing my app. I'm still working on it, and more things have to be added and fixed. All feedback is welcomed!");
            alertDialog.setButton("I got it!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.setIcon(R.drawable.sheepalert);
            alertDialog.show();
        }
        setContentView(R.layout.activity_main);
        WebView myWebView = (WebView) this.findViewById(R.id.webView);

        WebSettings webSettings = myWebView.getSettings();
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.setWebViewClient(new WebViewClient ());
        myWebView.getSettings().setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setBuiltInZoomControls(true);
        final Activity activity = this;
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

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
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        myWebView.getSettings().setUserAgentString(newUA);
        myWebView.setVisibility(View.INVISIBLE);
        myWebView.setWebViewClient(new WebViewClient() {
        final WebView myWebView = (WebView)findViewById(R.id.webView);
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String webUrl = myWebView.getUrl();
                if(url.equals(webUrl)){
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
                myWebView.loadUrl("javascript:if (typeof(document.getElementsByClassName('nav-mobile')[0]) != 'undefined' && document.getElementsByClassName('nav-mobile')[0] != null){document.getElementsByClassName('nav-mobile')[0].style.display = 'none';} void 0");
                myWebView.loadUrl("javascript:if (typeof(document.getElementsByClassName('nav-links-wrapper')[0]) != 'undefined' && document.getElementsByClassName('nav-links-wrapper')[0] != null){document.getElementsByClassName('nav-links-wrapper')[0].style.display = 'none';} void 0");
                myWebView.setVisibility(View.VISIBLE);
            }
        });
        myWebView.loadUrl("https://community.nextbit.com");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(this);


        //toggle.setDrawerIndicatorEnabled(false); //disable "hamburger to arrow" drawable
        //toggle.setHomeAsUpIndicator(R.drawable.ic_drawer); //
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView myWebView = (WebView) this.findViewById(R.id.webView);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.setVisibility(View.INVISIBLE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                WebView myWebView = (WebView) this.findViewById(R.id.webView);
                myWebView.setVisibility(View.INVISIBLE);
                myWebView.reload();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.store) {
            Uri uri = Uri.parse("https://www.nextbit.com/collections/all"); // missing 'http://' will cause crashed
            Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent1);
        } else if (id == R.id.nav_wiki) {
            Intent intent2 = new Intent(this,wiki.class);
            startActivity(intent2);
        } else if (id == R.id.nav_discover) {
            Uri uri = Uri.parse("https://nextbit.com"); // missing 'http://' will cause crash
            Intent intent5 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent5);
        } else if (id == R.id.nav_themes) {
            Intent intent2 = new Intent(MainActivity.this,Preferences.class);
            startActivity(intent2);
        } else if (id == R.id.Community) {
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.setVisibility(View.INVISIBLE);
            myWebView.loadUrl("https://community.nextbit.com");
        }  else if (id == R.id.messages) {
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.setVisibility(View.INVISIBLE);
            myWebView.loadUrl("https://community.nextbit.com/t5/notes/privatenotespage");
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
