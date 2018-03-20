package com.sup3rd3v3l0p3r.zeroble.hiyobi_view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Viewer extends AppCompatActivity {
    ViewerAdapter adapter;
    ListView listview ;
    String num;
    Element doc;
    Elements images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        Intent intent = getIntent();
        num = intent.getStringExtra("URL");
        listview =  findViewById(R.id.images);
        adapter = new ViewerAdapter();
        new Loading().execute();
    }

    public class Loading extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                doc = Jsoup.connect("https://hiyobi.me"+num).get();
                images = doc.select("body > div.img-url");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            int cnt = 0;
            Toast.makeText(Viewer.this,"Loading ok",Toast.LENGTH_SHORT).show();
            for(Element element: images) {
                adapter.addItem(element.text());
                listview.setAdapter(adapter);
                cnt++;
            }
        }
    }

}

