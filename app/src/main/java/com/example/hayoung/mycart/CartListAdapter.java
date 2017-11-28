package com.example.hayoung.mycart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hayoung.mycart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hayoung on 2017. 9. 13..
 */

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private List<CartItem> items = new ArrayList<>();
    private CartListView cartListView;

    public CartListAdapter(CartListView view) {
        this.cartListView = view;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public List<CartItem> items;
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(final CartListView cartListView, final View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.captureImage);
            textView = (TextView) itemView.findViewById(R.id.itemName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartListView.goToUri(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    cartListView.deleteItem(getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public List<CartItem> getItems() {
        return items;
    }

    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(cartListView, view);
    }

    @Override
    public void onBindViewHolder(CartListAdapter.ViewHolder holder, int position) {
        CartItem item = items.get(position);

        holder.items = items;
        holder.textView.setText(item.getDescription().replace(" ", "\u00A0"));

        Glide.with(holder.imageView)
                .load(item.getImagePath())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}
