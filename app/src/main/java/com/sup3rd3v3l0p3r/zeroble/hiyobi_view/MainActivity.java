package com.sup3rd3v3l0p3r.zeroble.hiyobi_view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
    Elements img;
    Document doc = null;
    HiyobiListAdapter adapter;
    ListView listview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview =  findViewById(R.id.hiyobi_list);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HiyobiListItem item = (HiyobiListItem) parent.getItemAtPosition(position) ;

                Toast.makeText(getApplicationContext(),item.getUrlStr() ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Viewer.class);
                intent.putExtra("URL",item.getUrlStr());
                startActivity(intent);
            }
        });

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
                title = doc.select("div.gallery-content > span > h5 > a > b");//aaa
                artist = doc.select("div.gallery-content > span > table > tbody > tr:nth-child(1) > td:nth-child(2)");
                original = doc.select("div.gallery-content > span > table > tbody > tr:nth-child(2) > td:nth-child(2)");
                type = doc.select("div.gallery-content > span > table > tbody > tr:nth-child(3) > td:nth-child(2)");
                tags = doc.select("div.gallery-content > span > table > tbody > tr:nth-child(4)");
                url = doc.select("div.gallery-content > span > h5 > a");//aaa
                img = doc.select("div.gallery-content > a > img");
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
            for(Element element: title) {
                adapter.addItem(
                        img.get(cnt).attr("src"),
                        "제목 : "+element.text(),
                        "작가 : "+artist.get(cnt).text(),
                        "시리즈 : "+original.get(cnt).text(),
                        "종류 : "+type.get(cnt).text(),
                        tags.get(cnt).text().replace(" ♀","♀ ").replace(" ♂","♂ "),
                        url.get(cnt).attr("href"));
                cnt++;
            }
            listview.setAdapter(adapter);
        }
    }

}

