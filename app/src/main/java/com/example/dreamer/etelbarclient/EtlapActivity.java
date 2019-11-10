package com.example.dreamer.etelbarclient;

import android.content.Intent;
import android.media.MediaCas;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EtlapActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etlap);

        mAuth = FirebaseAuth.getInstance();
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentEloetel(),"");
        adapter.AddFragment(new FragmentFoetel(),"");
        adapter.AddFragment(new FragmentDesszert(),"");
        adapter.AddFragment(new FragmentItal(),"");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_apetizer_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_maindish_24dp);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_dessert_24dp);
        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_drink_white_24dp);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            Intent nouser = new Intent(EtlapActivity.this,LoginActivity.class);
            startActivity(nouser);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_etlap_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
                startActivity(new Intent(this, CartActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
