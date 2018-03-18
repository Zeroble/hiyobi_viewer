package com.sup3rd3v3l0p3r.zeroble.hiyobi_view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Elements title;
    Elements artist;
    Elements original;
    Elements type;
    Elements tags;
    Elements url;
    Document doc = null;
    HiyobiListAdapter adapter;
    ListView listview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new HiyobiListAdapter();

        new LoadListPage(1).execute();
    }

    public class LoadListPage extends AsyncTask {
        int page;
        public LoadListPage(int page){
            this.page = page;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                doc = Jsoup.connect("https://hiyobi.me/list/"+page).get();
                title = doc.select("div.gallery-content > span > h5 > a > b");
                artist = doc.select("div.gallery-content > span > table > tbody > tr:nth-child(1) > td:nth-child(2)");
                original = doc.select("div.gallery-content > span > table > tbody > tr:nth-child(2) > td:nth-child(2)");
                type = doc.select("div.gallery-content > span > table > tbody > tr:nth-child(3) > td:nth-child(2)");
                tags = doc.select("div.gallery-content > span > table > tbody > tr:nth-child(4)");
                url = doc.select("div.gallery-content > span > h5 > a");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            int cnt = 0;
            Toast.makeText(MainActivity.this,"Loading ok",Toast.LENGTH_SHORT).show();
            Log.i("asdf",""+artist);
            for(Element element: title) {
                adapter.addItem(
                        ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_launcher_background),
                        element.text(),
                        artist.get(cnt).text(),
                        original.get(cnt).text(),
                        type.get(cnt).text(),
                        tags.get(cnt).text().replace(" ♀","♀ ").replace(" ♂","♂ "),
                        url.get(cnt).attr("href"));
                cnt++;
            }
            listview =  findViewById(R.id.hiyobi_list);
            listview.setAdapter(adapter);
        }
    }

}

