package com.sup3rd3v3l0p3r.zeroble.hiyobi_view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
    int MAX_PAGE=3;                         //View Pager의 총 페이지 갯수를 나타내는 변수 선언
    Fragment cur_fragment=new Fragment();   //현재 Viewpager가 가리키는 Fragment를 받을 변수 선언

    private class adapter extends FragmentPagerAdapter {                    //adapter클래스
        public adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position<0 || MAX_PAGE<=position)        //가리키는 페이지가 0 이하거나 MAX_PAGE보다 많을 시 null로 리턴
                return null;
            switch (position){              //포지션에 맞는 Fragment찾아서 cur_fragment변수에 대입
                case 0:
                    cur_fragment=new ImageFragment();
                    break;

                case 1:
                    cur_fragment=new page_2();
                    break;

                case 2:
                    cur_fragment=new page_3();
                    break;
            }

            return cur_fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }
    }
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indexSize == cnt)
                    Toast.makeText(getApplicationContext(),"마지막 페이지 입니다.",Toast.LENGTH_SHORT).show();
                else
                    Glide.with(getApplicationContext()).load(images.get(cnt++).text()).override(540,960).into(imageView);
            }
        });

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewPager);        //Viewpager 선언 및 초기화
        viewPager.setAdapter(new adapter(getSupportFragmentManager()));     //선언한 viewpager에 adapter를 연결

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
//            for(Element element: images) {
//                adapter.addItem(element.text());
//                listview.setAdapter(adapter);
//            }
//            listview.setVisibility(View.VISIBLE);
//            loadingImg.setVisibility(View.GONE);
            Glide.with(getApplicationContext()).load(images.get(cnt).text()).into(imageView);
            cnt++;
            imageView.setVisibility(View.VISIBLE);
            loadingImg.setVisibility(View.GONE);
            indexSize = images.size();
        }
    }

}

