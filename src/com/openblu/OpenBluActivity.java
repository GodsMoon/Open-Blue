package com.openblu;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.GridView;

public class OpenBluActivity extends ListActivity {
    private GridView gridView;

    class GridItem{
    	String name;
    	int icon;
    }
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

    }
}