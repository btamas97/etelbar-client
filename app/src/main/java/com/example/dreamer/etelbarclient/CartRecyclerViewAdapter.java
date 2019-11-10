package com.example.dreamer.etelbarclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public CartRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart_item, parent, false);
        ViewHolder holder = new ViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(mContext).load(CartActivity.cart.get(position).getImage()).into(holder.image);
        holder.name.setText(CartActivity.cart.get(position).getName());
        holder.price.setText(String.valueOf(CartActivity.cart.get(position).getPrice()+"Ft"));
    }

    @Override
    public int getItemCount() {
        return CartActivity.cart.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;
        TextView price;
        ImageView remove;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.cart_food_image);
            name = itemView.findViewById(R.id.cart_food_name);
            price = itemView.findViewById(R.id.cart_food_price);
            remove = itemView.findViewById(R.id.cart_item_delete);
            parentLayout = itemView.findViewById(R.id.cart_item_layout);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                    {
                        listener.onDeleteClick(position);
                        notifyDataSetChanged();
                        notifyItemChanged(position);
                    }

                }
            });
        }
    }
}
