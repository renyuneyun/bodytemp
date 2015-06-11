package me.ryey.bodytemp.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.ryey.bodytemp.R;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	final static int REQUEST_ADD = 0;
	final static int REQUEST_IMPORT = 1;
	final static int REQUEST_EDIT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add) {
			addItem();
			return true;
		} else if (id == R.id.action_import) {
			handleImport();
			return true;
		} else if (id == R.id.action_export) {
			handleExport();
		} else if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_ADD:
				String date, time, temperature, comment;
				date = data.getStringExtra("date");
				time = data.getStringExtra("time");
				temperature = data.getStringExtra("temperature");
				comment = data.getStringExtra("comment");
				((BodyTempFragment)getFragmentManager().findFragmentById(R.id.fragment1)).add(date, time, temperature, comment);
				break;
			case REQUEST_EDIT:
				break;
			case REQUEST_IMPORT:
				break;
			default:
				break;
			}
		}
	}
	
	private void addItem() {
		Intent intent = new Intent(this,EditInfoActivity.class);
		Date now = new Date();
		SimpleDateFormat f_date = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat f_time = new SimpleDateFormat("HH:mm");
		String date = f_date.format(now);
		String time = f_time.format(now);
		intent.putExtra("date", date);
		intent.putExtra("time", time);
		startActivityForResult(intent, REQUEST_ADD);
	}
	private void handleImport() {
		ClipboardManager cm = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		ClipData data = cm.getPrimaryClip();
		if (cm.hasPrimaryClip()) {
			Item item = data.getItemAt(0);
			CharSequence ch = item.getText();
			((BodyTempFragment)getFragmentManager().findFragmentById(R.id.fragment1)).readFromString(ch.toString());
		}
	}
	private void handleExport() {
		ClipboardManager cm = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		String str = ((BodyTempFragment)getFragmentManager().findFragmentById(R.id.fragment1)).exportData();
		ClipData data = ClipData.newPlainText("label1", str);
		cm.setPrimaryClip(data);
	}
}
