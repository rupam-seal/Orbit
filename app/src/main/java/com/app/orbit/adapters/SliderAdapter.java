package com.app.orbit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.app.orbit.R;

public class SliderAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.mContext = context;
    }

    public int[] slide_images = {
            R.drawable.discover,
            R.drawable.create,
            R.drawable.share,
            R.drawable.manage
    };

    public String[] slide_titles = {
            "Discover",
            "Create",
            "Share",
            "Manage"
    };

    public String[] slide_description = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"
    };

    @Override
    public int getCount() {
        return slide_titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slideImg);
        TextView slideTitle = view.findViewById(R.id.slideTitle);
        TextView slideDescription = view.findViewById(R.id.slideDescription);

        slideImageView.setImageResource(slide_images[position]);
        slideTitle.setText(slide_titles[position]);
        slideDescription.setText(slide_description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
