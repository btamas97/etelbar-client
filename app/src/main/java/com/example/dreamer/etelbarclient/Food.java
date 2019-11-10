package com.example.dreamer.etelbarclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Food {
    private String name,desc,category;
    private String image;
    private int price;
    public Food(){

    }
    public Food (String name,String category, String desc, int price, String image  ){

        this.name = name;
        this.image = image;
        this.desc = desc;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice(){
        return price;
    }

    public String getCategory() {
        return category;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name){
            TextView food_name =(TextView) mView.findViewById(R.id.foodName);
            food_name.setText(name);
        }

        public void setImage(Context ctx, String image){
            CircleImageView food_image =  mView.findViewById(R.id.foodImage);
            Picasso.with(ctx).load(image).into(food_image);
        }

        public void setPrice(int price){
            TextView food_price =(TextView) mView.findViewById(R.id.foodPrice);
            food_price.setText(String.valueOf(price)+"Ft");
        }
    }
}
