package com.sup3rd3v3l0p3r.zeroble.hiyobi_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HiyobiListAdapter extends BaseAdapter{
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public ArrayList<HiyobiListItem> listViewItemList = new ArrayList<HiyobiListItem>() ;

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hiyobi_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView artist = (TextView) convertView.findViewById(R.id.textView2) ;
        TextView original = (TextView) convertView.findViewById(R.id.textView3) ;
        TextView type = (TextView) convertView.findViewById(R.id.textView4) ;
        TextView tags = (TextView) convertView.findViewById(R.id.textView5) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        HiyobiListItem hiyobiListItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(context).load(hiyobiListItem.getIconDrawable()).into(iconImageView);
        titleTextView.setText(hiyobiListItem.getTitleStr());
        artist.setText(hiyobiListItem.getArtistStr());
        original.setText(hiyobiListItem.getOriginalStr());
        type.setText(hiyobiListItem.getTypeStr());
        tags.setText(hiyobiListItem.getTagsStr());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String icon, String title, String artist, String original, String type, String tags, String url) {
        HiyobiListItem item = new HiyobiListItem();

        item.setIconDrawable(icon);
        item.setArtistStr(artist);
        item.setOriginalStr(original);
        item.setTagsStr(tags);
        item.setTitleStr(title);
        item.setTypeStr(type);
        item.setUrlStr(url);
        listViewItemList.add(item);
    }

}
