package com.sup3rd3v3l0p3r.zeroble.hiyobi_view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Viewer extends AppCompatActivity {
    ViewerAdapter adapter;
    ListView listview ;
    ImageView imageView;
    String num;
    Element doc;
    Elements images;
    ImageView loadingImg;
    int cnt = 0;
    int indexSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        loadingImg = findViewById(R.id.viewer_loading);
        Glide.with(this).load(R.drawable.giphy).into(new GlideDrawableImageViewTarget(loadingImg));

        Intent intent = getIntent();
        num = intent.getStringExtra("URL");
        listview =  findViewById(R.id.scroll_images);
        imageView = findViewById(R.id.tap_images);

        adapter = new ViewerAdapter();
        new Loading().execute();

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(indexSize == cnt)
//                    Toast.makeText(getApplicationContext(),"마지막 페이지 입니다.",Toast.LENGTH_SHORT).show();
//                else
//                    Glide.with(getApplicationContext()).load(images.get(cnt++).text()).override(540,960).into(imageView);
//            }
//        });
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
            Toast.makeText(Viewer.this,"Loading ok",Toast.LENGTH_SHORT).show();
            for(Element element: images) {
                adapter.addItem(element.text());
                listview.setAdapter(adapter);
            }
            listview.setVisibility(View.VISIBLE);
            loadingImg.setVisibility(View.GONE);

//            Glide.with(getApplicationContext()).load(images.get(cnt).text()).into(imageView);
//            cnt++;
//            imageView.setVisibility(View.VISIBLE);
//            loadingImg.setVisibility(View.GONE);
//            indexSize = images.size();
        }
    }

}

