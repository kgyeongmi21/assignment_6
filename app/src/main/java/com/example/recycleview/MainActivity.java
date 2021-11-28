package com.example.recycleview;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.recycleview.databinding.ActivityMainBinding;
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

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BookModel item;


    private final Gson gson = new Gson();
    private final String BOOK_FILENAME = "books.json";
    private final String saveFileName = "memo.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<String> data = new ArrayList<>();
        String booksJson = FileUtils.readFromAssets(this, "books.json");
        List<BookModel> array = gson.fromJson(booksJson, new TypeToken<List<BookModel>>() {
        }.getType());

        ListView adapter = new ListView(data);
        binding.recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

        binding.textPrint.setMovementMethod(new ScrollingMovementMethod());
        binding.textPrint.setMovementMethod(LinkMovementMethod.getInstance());


        File book = new File(getFilesDir(), BOOK_FILENAME);
        if (!book.exists()) {
            String bookJson = FileUtils.readFromAssets(this, "books.json");
            FileUtils.writeFile(this, BOOK_FILENAME, bookJson);
        }
        load();
    }

    public void save() {
            String jsonString = gson.toJson(this.item);
            FileUtils.writeFile(this, "books.json", jsonString);
    }


    public void load() {
        String bookJson = FileUtils.readFile(this, "books.json");
        this.item = gson.fromJson(bookJson, BookModel.class);
        updateUI();
    }

    public void updateUI() {

        String text = "제목: " + item.getTitle() + ", 가격: " + item.getPrice();
        binding.textPrint.setText(text);
        binding.textAuthor.setText(item.getAuthor());
        binding.textPrice.setText(String.format("%,d원", item.getPrice()));
        binding.textDescription.setText(Html.fromHtml(item.getDescription()));

        Glide.with(this)
                .load(item.getImage())
                .into(binding.imageBook);

        String testData = "<b>해시태그를 적용시켜 줍니다</b> 이렇게요!";
        binding.textDescription.setText(Html.fromHtml(testData));

    }

    public static String readFile(Context context, String filename) throws FileNotFoundException {
        FileInputStream fis = context.openFileInput(filename);
        String contents = "";
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line =reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
        } finally {
            contents = stringBuilder.toString().trim();
        }
        return stringBuilder.toString().trim();
    }


    public String readFromAssets(String name) throws IOException {
        InputStream inputStream = getAssets().open(name);
        return FileUtils.readStream(inputStream);
    }



}