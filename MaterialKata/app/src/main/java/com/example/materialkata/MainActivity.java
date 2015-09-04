package com.example.materialkata;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int mutedColour;
    private NavigationView navdrawer;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define Header Toolbar
        final Toolbar headerToolbar = (Toolbar)findViewById(R.id.header_toolbar);
        setSupportActionBar(headerToolbar);

        final Toolbar footerToolbar = (Toolbar)findViewById(R.id.footer_toolbar);
        footerToolbar.inflateMenu(R.menu.menu_footer);

        // Initialise RecyclerView and apply String-array
        final String[] dow = getResources().getStringArray(R.array.days_of_week);
        final SimpleAdapter adapter = new SimpleAdapter(dow);
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Generate palette
        final CollapsingToolbarLayout ctb = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        final Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        Palette.from(bmp).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(final Palette palette) {
                mutedColour = palette.getMutedColor(R.attr.colorPrimary);
                ctb.setContentScrimColor(mutedColour);
                footerToolbar.setBackgroundColor(mutedColour);
            }
        });

        // Apply navigation click-events
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        navdrawer = (NavigationView)findViewById(R.id.nav_drawer);

        headerToolbar.setNavigationIcon(R.mipmap.ic_menu_24dp);
        headerToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //
        // onItemSelected
        navdrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
                // TODO handle navigation
                drawerLayout.closeDrawers();
                return true;
            }
        });
        //
        // FAB
        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, R.string.salutations, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
