package me.ryey.bodytemp.ui;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import me.ryey.bodytemp.R;
import me.ryey.bodytemp.TData;
import android.os.Bundle;
import android.app.ListFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BodyTempFragment extends ListFragment {
	final String filename = "temperature.data";
	TData data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_body_temp, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.data = new TData();
		readFromFile(filename);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			FileOutputStream fin = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
			OutputStreamWriter isr = new OutputStreamWriter(fin);
			BufferedWriter br = new BufferedWriter(isr);
			br.write(data.toString());
			br.close();
			isr.close();
			fin.close();
		}
		catch (FileNotFoundException e) {
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Toast.makeText(getActivity(), "" + data.getItem(position), Toast.LENGTH_SHORT).show();
	}

	private void setUpList() {
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, data.toStringArray()));
	}
	
	void readFromFile(String filename) {
		try {
			FileInputStream in = getActivity().openFileInput(filename);
			data.readData(in);
			setUpList();
			in.close();
		}
		catch (FileNotFoundException e) {
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	void readFromString(String origin_str) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(origin_str.getBytes());
			data.readData(in);
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void add(String date, String time, String temperature, String comment) {
		data.addItem(date, time, temperature, comment);
		setUpList();
	}
	
	String exportData() {
		return data.toString();
	}
}
