package com.app.orbit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.orbit.R;
import com.app.orbit.adapters.SliderAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager mSlideViewPager;
    LinearLayout mDotLayout;

    AppCompatButton mSignupBtn, mLoginButton;
    TextView mTermsTxt;

    SliderAdapter sliderAdapter;

    TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        mSlideViewPager = findViewById(R.id.sliderViewpager);
        mDotLayout = findViewById(R.id.dotsLayout);
        mSignupBtn = findViewById(R.id.btn_signup);
        mLoginButton = findViewById(R.id.btn_login);
        mTermsTxt = findViewById(R.id.txt_term);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicators(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mLoginButton.setOnClickListener(v -> {
            loginIntent();
        });

        mSignupBtn.setOnClickListener(v -> {
            signupIntent();
        });

    }

    private void loginIntent() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    private void signupIntent() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

//        Pair[] pairs = new Pair[1];
//        pairs[0] = new Pair<View, String>(findViewById(R.id.loginBtn), "signup_transition");
//
//        ActivityOptions options = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            options = ActivityOptions.makeSceneTransitionAnimation(OnBoardingActivity.this, pairs);
//            startActivity(i, options.toBundle());
//        }else{
//            startActivity(i);
//        }
    }

    public void addDotsIndicators(int position){
        mDots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.lightGray));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.mainTextColor));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicators(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent mainIntent = new Intent(OnBoardingActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}