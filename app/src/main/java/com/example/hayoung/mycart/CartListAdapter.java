package com.example.hayoung.mycart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hayoung.mycart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hayoung on 2017. 9. 13..
 */

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {


    private List<CartItem> items = new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public List<CartItem> items;
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(final View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.captureImage);
            textView = (TextView) itemView.findViewById(R.id.itemName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = items.get(getAdapterPosition()).getDescription();
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    viewIntent.setData(Uri.parse(url));
                    v.getContext().startActivity(viewIntent);
                }
            });

        }
    }

//    private void showDeleteAlertDialog() {
//        new AlertDialog.Builder(context.getApplicationContext())
//                .setTitle("안내")
//                .setMessage("해당 목록을 삭제하시겠습니까?")
//                .setCancelable(false)
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setNegativeButton("취소", null)
//                .show();
//
//
//    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartListAdapter.ViewHolder holder, int position) {
        CartItem item = items.get(position);

        holder.items = items;
        holder.imageView.setImageURI(Uri.parse(item.getImagePath()));
        holder.textView.setText(item.getDescription().replace(" ", "\u00A0"));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
