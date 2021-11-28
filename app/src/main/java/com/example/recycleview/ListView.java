package com.example.recycleview;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleview.databinding.ActivityMainBinding;
import com.example.recycleview.databinding.ActivitySecondBinding;
import com.example.recycleview.models.BookModel;
import com.example.recycleview.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ListView extends RecyclerView.Adapter<ListView.ViewHolder>{
    private final ArrayList<String> data;
    private final Gson gson = new Gson();
    private final String BOOK_FILENAME = "books.json";
    private final String saveFileName = "memo.txt";
    private BookModel item;

    public ListView(ArrayList<String> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ActivitySecondBinding binding = ActivitySecondBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String message = data.get(position);
        holder.binding.textDescription.setText(message);
        String mess = data.get(position);
        holder.binding.textAuthor.setText(mess);
        String mes = data.get(position);
        holder.binding.textPrice.setText(mes);
        String me = data.get(position);
        holder.binding.textPrint.setText(me);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final ActivitySecondBinding binding;

        public ViewHolder(ActivitySecondBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
