package com.example.dreamer.etelbarclient;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleFoodActivity extends AppCompatActivity {

    private String food_key = null;
    private String food_name, food_category, food_desc,food_image;
    private int food_price;
    private DatabaseReference mDatabase,userData;
    private TextView singleFoodTitle, singleFoodCategory, singleFoodDesc, singleFoodPrice;
    private CircleImageView singleFoodImage;
    private FirebaseAuth mAuth;
    private FirebaseUser current_user;
    private DatabaseReference mRef;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);

        food_key = getIntent().getExtras().getString("FoodId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");

        singleFoodTitle = (TextView) findViewById(R.id.singleTitle);
        singleFoodCategory = (TextView) findViewById(R.id.singleCategory);
        singleFoodDesc  = (TextView) findViewById(R.id.singleDesc);
        singleFoodImage =  findViewById(R.id.singleImageView);
        singleFoodPrice = (TextView) findViewById(R.id.singlePrice);

        mAuth = FirebaseAuth.getInstance();

        mDatabase.child(food_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food_name = (String) dataSnapshot.child("name").getValue();
                food_category = (String) dataSnapshot.child("category").getValue();
                food_desc = (String) dataSnapshot.child("desc").getValue();
                food_image = (String) dataSnapshot.child("image").getValue();
                food_price = (Integer) dataSnapshot.child("price").getValue(Integer.class);

                singleFoodTitle.setText(food_name);
                singleFoodCategory.setText(food_category);
                singleFoodDesc.setText(food_desc);
                singleFoodPrice.setText(String.valueOf(food_price)+"Ft");
                Picasso.with(SingleFoodActivity.this).load(food_image).into(singleFoodImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addToCartClicked(View view) {
        view.startAnimation(buttonClick);
        CartItem cartItem = new CartItem(food_image,food_name,food_price);
        CartActivity.cart.add(cartItem);
        Toast.makeText(SingleFoodActivity.this,"Kosárhoz adva!",Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_etlap_cart,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_cart:
                startActivity(new Intent(this, CartActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
