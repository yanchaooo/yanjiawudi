package com.example.kao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Main2Activity extends AppCompatActivity {

    private ImageView imaget;
    private TextView name;
    private TextView context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name");
        String image1 = intent.getStringExtra("image");
        String context1 = intent.getStringExtra("context");
        name.setText(name1);
        context.setText(context1);
        Glide.with(Main2Activity.this).load(image1).into(imaget);
    }

    private void initView() {
        imaget = (ImageView) findViewById(R.id.imaget);
        name = (TextView) findViewById(R.id.name);
        context = (TextView) findViewById(R.id.context);
    }
}
