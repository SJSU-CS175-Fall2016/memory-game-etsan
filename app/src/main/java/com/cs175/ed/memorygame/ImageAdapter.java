package com.cs175.ed.memorygame;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageAdapter extends BaseAdapter implements Serializable {
    private Context mContext;
    private ArrayList<Integer> mThumbIds;
    private List<ImageView> imgViews;
    private ArrayList<Integer> clearedList;

    public ImageAdapter(Context c) {
        mContext = c;
        mThumbIds = new ArrayList<Integer>();
        imgViews = new ArrayList<ImageView>();
        setImages();
    }

    public ImageAdapter(Context c, ArrayList<Integer> positions, ArrayList<Integer> clearedList) {
        mContext = c;
        mThumbIds = positions;
        imgViews = new ArrayList<ImageView>();
        this.clearedList = clearedList;
    }

    public ArrayList<Integer> getIDs() {
        return mThumbIds;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
        } else {
            imageView = (ImageView) convertView;
        }
        if (clearedList != null && clearedList.contains(mThumbIds.get(position))) {
            imageView.setImageResource(R.drawable.checked);
            imageView.setOnClickListener(null);
        } else {
            imageView.setImageResource(R.drawable.question);
        }
        imageView.setId(mThumbIds.get(position));
        imageView.setTag(mContext.getResources().getResourceEntryName(mThumbIds.get(position)));
        imgViews.add(imageView);
        return imageView;
    }

    public void setImages() {
        mThumbIds.add(R.drawable.ayy);
        mThumbIds.add(R.drawable.boom);
        mThumbIds.add(R.drawable.eggplant);
        mThumbIds.add(R.drawable.hundred);
        mThumbIds.add(R.drawable.lmao);
        mThumbIds.add(R.drawable.okhand);
        mThumbIds.add(R.drawable.money);
        mThumbIds.add(R.drawable.peach);
        mThumbIds.add(R.drawable.think);
        mThumbIds.add(R.drawable.sweat);
        mThumbIds.add(R.drawable.ayy1);
        mThumbIds.add(R.drawable.boom1);
        mThumbIds.add(R.drawable.eggplant1);
        mThumbIds.add(R.drawable.hundred1);
        mThumbIds.add(R.drawable.lmao1);
        mThumbIds.add(R.drawable.okhand1);
        mThumbIds.add(R.drawable.money1);
        mThumbIds.add(R.drawable.peach1);
        mThumbIds.add(R.drawable.think1);
        mThumbIds.add(R.drawable.sweat1);
        Collections.shuffle(mThumbIds);
    }
}