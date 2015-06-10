package me.ryey.bodytemp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

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
	List<String> all_data;
	String []bodyTempData;

	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {          
		return inflater.inflate(R.layout.fragment_body_temp, container, false);  
	}  

	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		this.all_data = new ArrayList<String>();
		List<String> strs = new ArrayList<String>();
		String str=""; 
		try {   
			FileInputStream fin = getActivity().openFileInput(filename);   
			InputStreamReader isr = new InputStreamReader(fin);
			BufferedReader br = new BufferedReader(isr);
			while ((str = br.readLine()) != null) {
				strs.add(str);
			}
			parse(strs);
			setUpList();
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
	public void onDestroy() {
		super.onDestroy();
		try {   
			FileOutputStream fin = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);   
			OutputStreamWriter isr = new OutputStreamWriter(fin);
			BufferedWriter br = new BufferedWriter(isr);
			for (String str : all_data) {
				br.write(str);
				br.newLine();
			}
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

	public void onListItemClick(ListView parent, View v, int position, long id) {            
		Toast.makeText(getActivity(), "" + bodyTempData[position], Toast.LENGTH_SHORT).show();
	}    

	private void setUpList() {
		this.bodyTempData = this.all_data.toArray(new String[0]);
		setListAdapter(new ArrayAdapter<String>(getActivity(),  
				android.R.layout.simple_list_item_1, bodyTempData));  
	}

	void readFromString(String origin_str) {
		List<String> strs = new ArrayList<String>();
		String str;
		try {   
			ByteArrayInputStream in = new ByteArrayInputStream(origin_str.getBytes());
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			while ((str = br.readLine()) != null) {
				strs.add(str);
			}
			parse(strs);
			setUpList();
			br.close();
			isr.close();
			in.close();       
		}   
		catch (IOException e) {
			e.printStackTrace();   
		}
	}
	void parse(List<String> data) {
		List<String> validdata = new ArrayList<String>();
		for (String str : data) {
			str.replace("^\\s+", "");
			if (str.isEmpty())
				continue;
			validdata.add(str);
		}
		this.all_data = validdata;
	}
	void add(String date, String time, String temperature, String comment) {
		String new_data = date + " " + time + " " + temperature;
		if (!comment.isEmpty()) {
			new_data = new_data + " #" + comment;
		}
		all_data.add(new_data);
		setUpList();
	}
	String exportData() {
		String str = "";
		for (String tmp : all_data) {
			str += tmp + "\n";
		}
		return str;
	}
}  