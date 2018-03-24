package com.sup3rd3v3l0p3r.zeroble.hiyobi_view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

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
    ListView listview;
    ImageView loadingImg;
    TextView[] footer_buttons = new TextView[12];
    int nowPage_10 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.hiyobi_list);
        listview.addFooterView(getLayoutInflater().inflate(R.layout.footer, null, false));

        footer_buttons[0] = findViewById(R.id.footer_previous);
        footer_buttons[1] = findViewById(R.id.footer_1);
        footer_buttons[2] = findViewById(R.id.footer_2);
        footer_buttons[3] = findViewById(R.id.footer_3);
        footer_buttons[4] = findViewById(R.id.footer_4);
        footer_buttons[5] = findViewById(R.id.footer_5);
        footer_buttons[6] = findViewById(R.id.footer_6);
        footer_buttons[7] = findViewById(R.id.footer_7);
        footer_buttons[8] = findViewById(R.id.footer_8);
        footer_buttons[9] = findViewById(R.id.footer_9);
        footer_buttons[10] = findViewById(R.id.footer_10);
        footer_buttons[11] = findViewById(R.id.footer_next);

        for(int i = 1;i<=10;i++) {
            final int n = i;
            footer_buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new LoadListPage(n+nowPage_10).execute();
                }
            });
        }
        footer_buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowPage_10!=0) {
                    nowPage_10 -= 10;
                    new LoadListPage(1+nowPage_10).execute();
                }
            }
        });

        footer_buttons[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPage_10+=10;
                new LoadListPage(1+nowPage_10).execute();
            }
        });

        loadingImg = findViewById(R.id.main_loading);
        Glide.with(this).load(R.drawable.giphy).into(new GlideDrawableImageViewTarget(loadingImg));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HiyobiListItem item = (HiyobiListItem) parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), item.getUrlStr(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Viewer.class);
                intent.putExtra("URL", item.getUrlStr());
                startActivity(intent);
            }
        });

        adapter = new HiyobiListAdapter();

        new LoadListPage(1).execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    public class LoadListPage extends AsyncTask {
        int page;

        public LoadListPage(int page) {
            this.page = page;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                doc = Jsoup.connect("https://hiyobi.me/list/" + page).get();
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
            adapter.listViewItemList.clear();
            for (Element element : title) {
                adapter.addItem(
                        img.get(cnt).attr("src"),
                        "제목 : " + element.text(),
                        "작가 : " + artist.get(cnt).text(),
                        "시리즈 : " + original.get(cnt).text(),
                        "종류 : " + type.get(cnt).text(),
                        tags.get(cnt).text().replace(" ♀", "♀ ").replace(" ♂", "♂ "),
                        url.get(cnt).attr("href"));
                cnt++;
            }
            for(int i = 1;i<=10;i++) {
                footer_buttons[i].setBackground(getResources().getDrawable(R.drawable.footer_button_center));
                footer_buttons[i].setText(i+nowPage_10+"");
            }
            if(page%10 == 0)
                footer_buttons[10].setBackground(getResources().getDrawable(R.drawable.footer_button_selected));
            else
                footer_buttons[page%10].setBackground(getResources().getDrawable(R.drawable.footer_button_selected));

            listview.setAdapter(adapter);
            listview.setVisibility(View.VISIBLE);
            loadingImg.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "page : "+page, Toast.LENGTH_SHORT).show();
        }
    }


}

