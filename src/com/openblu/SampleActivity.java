package com.openblu;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.actionbar.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class SampleActivity extends Activity {

	private Context context;
	private ActionBar actionBar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample1);

		actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle(this.getTitle());
        
        context = this;
        
        RelativeLayout assessment = (RelativeLayout)findViewById(R.id.assessment);
        RelativeLayout biological = (RelativeLayout)findViewById(R.id.biological);
        RelativeLayout physical = (RelativeLayout)findViewById(R.id.physical);
        RelativeLayout chemical = (RelativeLayout)findViewById(R.id.chemical);
        
        ProgressBar assessmentPB = (ProgressBar)findViewById(R.id.assessmentPB);
        ProgressBar biologicalPB = (ProgressBar)findViewById(R.id.biologicalPB);
        ProgressBar physicalPB = (ProgressBar)findViewById(R.id.physicalPB);
        ProgressBar chemicalPB = (ProgressBar)findViewById(R.id.chemicalBP);
        
        assessment.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AssessmentActivity.class);
				startActivity(intent);	
				
			}
		});
        
        biological.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, BiologicalActivity.class);
				startActivity(intent);	
				
			}
		});
 
        physical.setOnClickListener(new OnClickListener() {
 	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PhysicalActivity.class);
				startActivity(intent);	
				
			}
        });
 
        chemical.setOnClickListener(new OnClickListener() {
 	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ChemicalActivity.class);
				startActivity(intent);	
				
			}
        });
	}
}
