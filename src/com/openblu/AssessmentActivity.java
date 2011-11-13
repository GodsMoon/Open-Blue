package com.openblu;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.actionbar.R;
import com.openblu.data.RAPIDDESCRIPTIONSContentProvider;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class AssessmentActivity extends Activity {

	Cursor descCursor;
	private TextView tv;
	private ActionBar actionBar;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment);
        
        actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle(this.getTitle());
        
        //Stuff below this line is hidden in the UI
        ContentResolver contentResolver = getContentResolver();
        
        // hard coded value of 2 for testing
        descCursor = contentResolver.query(RAPIDDESCRIPTIONSContentProvider.CONTENT_URI, 
        		new String[]{RAPIDDESCRIPTIONSContentProvider._ID,
        		RAPIDDESCRIPTIONSContentProvider.RAPID_ID,
        		RAPIDDESCRIPTIONSContentProvider.SCORE,
        		RAPIDDESCRIPTIONSContentProvider.DESCRIPTION}, RAPIDDESCRIPTIONSContentProvider.RAPID_ID + " = 2" , null, RAPIDDESCRIPTIONSContentProvider._ID );
        
        SeekBar seek = (SeekBar) findViewById(R.id.seekBar1);
        tv = (TextView) findViewById(R.id.textView1);
        
        seek.setMax(20);
        
        seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				if(progress < 5)
				{
					descCursor.moveToPosition(0);					
				}
				else if(progress > 5 && progress < 10)
				{
					descCursor.moveToPosition(1);
					
				}
				else if(progress > 10 && progress < 15)
				{
					descCursor.moveToPosition(2);
				}
				else if(progress > 15 && progress < 20)
				{
					descCursor.moveToPosition(3);
				}
				
				tv.setText(descCursor.getString(descCursor.getColumnIndex(RAPIDDESCRIPTIONSContentProvider.DESCRIPTION)));
			}
		});
        
    }
	
}
