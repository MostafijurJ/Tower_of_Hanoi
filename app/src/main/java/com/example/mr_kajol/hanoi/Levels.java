package com.example.mr_kajol.hanoi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Levels extends Activity implements OnClickListener {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.levels);
		
		findViewById(R.id.easy).setOnClickListener(this);
		findViewById(R.id.medium).setOnClickListener(this);
		findViewById(R.id.hard).setOnClickListener(this);
	}


	/*1231132494
	3m8pa4*/

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Levels.this, Play.class);
		switch (v.getId()) {
			case R.id.easy:
				intent.putExtra("numofdisks", 3);
				break;

			case R.id.medium:
				intent.putExtra("numofdisks", 4);
				break;
	
			case R.id.hard:
				intent.putExtra("numofdisks", 5);
				break;
		}
		startActivity(intent);
	}
}