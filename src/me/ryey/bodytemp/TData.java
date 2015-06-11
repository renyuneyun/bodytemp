package me.ryey.bodytemp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TData {
	List<String> data;
	public TData() {
		this.data = new ArrayList<String>();
	}
	
	@Override
	public String toString() {
		String str = "";
		for (String item : data) {
			str += item + "\n";
		}
		return str;
	}
	public String[] toStringArray() {
		String[] strs = data.toArray(new String[0]);
		return strs;
	}
	public void readData(InputStream in) {
		List<String> strs = new ArrayList<String>();
		String str;
		try {
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			while ((str = br.readLine()) != null) {
				strs.add(str);
			}
			parse(strs);
			br.close();
			isr.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addItem(String date, String time, String temperature, String comment) {
		String new_data = date + " " + time + " " + temperature;
		if (!comment.isEmpty()) {
			new_data = new_data + " #" + comment;
		}
		this.data.add(new_data);
	}
	public String getItem(int index) {
		return data.get(index);
	}
	private void parse(List<String> raw_data) {
		List<String> validdata = new ArrayList<String>();
		for (String str : raw_data) {
			str.replace("^\\s+", "");
			if (str.isEmpty())
				continue;
			validdata.add(str);
		}
		this.data = validdata;
	}
}