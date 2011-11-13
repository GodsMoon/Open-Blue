package com.openblu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class OpenBluActivity extends Activity {

	private Context context;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

        context = this;

		RelativeLayout sample = (RelativeLayout) findViewById(R.id.sample_area);
		RelativeLayout map = (RelativeLayout) findViewById(R.id.map_area);
		RelativeLayout news = (RelativeLayout) findViewById(R.id.news_area);
		RelativeLayout about = (RelativeLayout) findViewById(R.id.about_area);

		sample.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,SampleActivity.class);
				startActivity(intent);				
			}
		});

		map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,MapActivity.class);
				startActivity(intent);				
			}
		});

		news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,NewsActivity.class);
				startActivity(intent);				
			}
		});

		about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,AboutActivity.class);
				startActivity(intent);				
			}
		});
    }
}