package com.example.dreamer.etelbarclient;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    View v;
    private RecyclerView myrecyclerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference storageReference;
    private ActionBar actionBar;
    CartRecyclerViewAdapter adapter;
    private int totalPrice;
    private TextView totalPriceText;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    public static List<CartItem> cart = new ArrayList<>();

    public CartActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        initRecyclerView();

        storageReference = FirebaseStorage.getInstance().getReference("Order");
        mDatabase = FirebaseDatabase.getInstance().getReference("Order");

        totalPrice = calculateTotal();
        totalPriceText = findViewById(R.id.totalPrice);
        totalPriceText.setText(totalPrice+"Ft");
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            Intent nouser = new Intent(CartActivity.this,LoginActivity.class);
            startActivity(nouser);
            finish();
        }
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.cart_recycler_view);
        adapter = new CartRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new CartRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeCartItem(position);
            }
        });
    }

    public void removeCartItem(int position) {
        cart.remove(position);
        totalPrice = calculateTotal();
        totalPriceText.setText(totalPrice+"Ft");
    }



    public void orderCartItems(View view) {
        //TODO send cartitemlist to database with username and profilepic
        view.startAnimation(buttonClick);
        if(!cart.isEmpty()) {
            final DatabaseReference newPost = mDatabase.push();
            newPost.child("name").setValue(mAuth.getCurrentUser().getDisplayName());
            newPost.child("profilephoto").setValue("https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large");
            newPost.child("tablenumber").setValue(MainActivity.getTableViewNumber());
            newPost.child("orders").setValue(cart);
            newPost.child("status").setValue("open");
            newPost.child("totalprice").setValue(totalPrice);
            Toast.makeText(CartActivity.this,"Sikeres rendelés",Toast.LENGTH_LONG).show();
            cart.clear();
            startActivity(new Intent(CartActivity.this,GoodByeActivity.class));
        }
        else{
            Toast.makeText(CartActivity.this,"A kosár üres",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int calculateTotal(){
        int total= 0;
        for (CartItem cartItem: cart){
            total+=cartItem.getPrice();
        }
        return total;
    }

}
