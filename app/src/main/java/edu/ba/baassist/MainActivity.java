package edu.ba.baassist;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import edu.ba.fragments.AboutFragment;
import edu.ba.fragments.MainFragment;
import edu.ba.fragments.SettingsFragment;
import edu.ba.fragments.StatusFragment;
import edu.ba.fragments.WikiFragment;

/**
 *Main activity which provides NavigationDrawer and changes fragments on demand.
 * Only running activity after LoginActivity is closed.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Set NavigationDrawer,Toolbar,FragmentManager on create.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
    }

    //Handling back-button of the phone to get back to the MainFragment.
    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Fragment manager which changes the fragments.
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_timetable) {
                fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
        } else if (id == R.id.nav_wiki) {
                fm.beginTransaction().replace(R.id.content_frame, new WikiFragment()).commit();
        } else if (id == R.id.nav_status) {
                fm.beginTransaction().replace(R.id.content_frame, new StatusFragment()).commit();
        } else if (id == R.id.nav_ueber) {
                fm.beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
        } else if (id == R.id.nav_settings) {
                fm.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Button in a fragment needs to be initialized in the MainActivity.
    public static void ButtonOnClick(View v) {
        new SettingsFragment().buttonOnClick(v);
    }
}
