package me.ryey.bodytemp.ui;

import me.ryey.bodytemp.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_info);
		Bundle data = getIntent().getExtras();
		String date, time, temperature, comment;
		date = data.getString("date", "");
		time = data.getString("time", "");
		temperature = data.getString("temperature", "");
		comment = data.getString("comment", "");
        ((EditText)findViewById(R.id.text_date)).setText(date);
        ((EditText)findViewById(R.id.text_time)).setText(time);
        ((EditText)findViewById(R.id.text_temperature)).setText(temperature);
        ((EditText)findViewById(R.id.text_comment)).setText(comment);
        ((Button)findViewById(R.id.button_confirm)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				confimInfo();
			}
        });
	}
	
	private void confimInfo() {
		String date, time, temperature, comment;
		date = ((EditText)findViewById(R.id.text_date)).getText().toString();
		time = ((EditText)findViewById(R.id.text_time)).getText().toString();
		temperature = ((EditText)findViewById(R.id.text_temperature)).getText().toString();
		comment = ((EditText)findViewById(R.id.text_comment)).getText().toString();
		Intent data = new Intent();
		Bundle info = new Bundle();
		info.putString("date", date);
		info.putString("time", time);
		info.putString("temperature", temperature);
		info.putString("comment", comment);
		data.putExtras(info);
		setResult(RESULT_OK, data);
		finish();
	}
}
