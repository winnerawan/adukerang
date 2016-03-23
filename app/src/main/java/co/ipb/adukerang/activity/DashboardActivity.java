package co.ipb.adukerang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;

import co.ipb.adukerang.R;
import co.ipb.adukerang.handler.SQLiteHandler;
import co.ipb.adukerang.handler.SessionManager;

public class DashboardActivity extends AppCompatActivity {

    private Drawer mDrawer;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        String email = user.get("email");
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AccountHeader header = new AccountHeaderBuilder().withActivity(this).withHeaderBackground(R.drawable.side_nav_bar)
                .addProfiles(new ProfileDrawerItem().withEmail(email).withName(name).withSelectable(false)).build();

        PrimaryDrawerItem profile = new PrimaryDrawerItem().withName(R.string.drawer_profile)
                .withIcon(R.drawable.ic_menu_profile).withSelectable(false);
        PrimaryDrawerItem complaints = new PrimaryDrawerItem().withName(R.string.drawer_complaints)
                .withIcon(R.drawable.ic_menu_cekstatus).withSelectable(false);
        PrimaryDrawerItem setting = new PrimaryDrawerItem().withName(R.string.drawer_setting)
                .withIcon(R.drawable.ic_menu_setting).withSelectable(false);

        mDrawer = new DrawerBuilder().withActivity(this).withAccountHeader(header)
                .addDrawerItems(new DividerDrawerItem(),
                        profile,complaints,setting).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 2:
                                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
                                break;
                            case 4:
                                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
                        }
                        return true;
                    }
                }).build();
    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
