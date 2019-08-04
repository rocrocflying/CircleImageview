package com.rocflying.circleimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {


    private CircleImageView circleImageView;
    private CircleImageView circleImageView2;
    private CircleImageView circleImageView3;
    private final String url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2906215186,4219929531&fm=26&gp=0.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleImageView = findViewById(R.id.circleiv);
        circleImageView2 = findViewById(R.id.circleiv2);
        circleImageView3 = findViewById(R.id.circleiv3);

        Glide.with(this).asBitmap().load(url).into(circleImageView);
        Glide.with(this).asBitmap().load(url).into(circleImageView2);
        Glide.with(this).asBitmap().load(url).into(circleImageView3);
    }
}
