package com.adwyze.adwyze.geotagging;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class GeoFriends {
    
	void readJSONFile(BufferedReader br,String filename,ArrayList<JSONObject> jsonArray){
		try {

			String sCurrentLine;
			
			br = new BufferedReader(new FileReader(filename));

			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				JSONParser parser = new JSONParser();
				JSONObject jsonObj = null;
				try {
					jsonObj = (JSONObject) parser.parse(sCurrentLine);
					jsonArray.add(jsonObj);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	HashMap<Long, String> findDistances(int radius,double adwyzeLatitude,double adwyzeLongitude,ArrayList<JSONObject> jsonArray){
		double latitudeOfficeRadian = Math.toRadians(adwyzeLatitude);
		double longitudeOfficeRadian = Math.toRadians(adwyzeLongitude);
		HashMap<Long, String> temp = new HashMap<Long, String>();
		for(int i=0;i<jsonArray.size();i++){
			double latitudeRadian = Math.toRadians(Double.parseDouble((String) jsonArray.get(i).get("latitude")));
			double longitudeRadian = Math.toRadians(Double.parseDouble((String) jsonArray.get(i).get("longitude")));
			double absoluteLatitudeDifference = Math.toRadians(Math.abs(adwyzeLatitude-Double.parseDouble((String) jsonArray.get(i).get("latitude"))));
			double absoluteLongitudeDifference = Math.toRadians(Math.abs(adwyzeLongitude-Double.parseDouble((String) jsonArray.get(i).get("longitude"))));
            double sigma = Math.sin(absoluteLatitudeDifference/2) * Math.sin(absoluteLatitudeDifference/2) +
    		        Math.cos(latitudeOfficeRadian) * Math.cos(latitudeRadian) *
    		        Math.sin(absoluteLongitudeDifference/2) * Math.sin(absoluteLongitudeDifference/2);
			double sigmaTri = 2 * Math.atan2(Math.sqrt(sigma), Math.sqrt(1-sigma));
            double distance = (radius * sigmaTri) * 0.001;
			//System.out.println(distance);
			if(distance <= 100)
				temp.put((Long)jsonArray.get(i).get("user_id"), (String)jsonArray.get(i).get("name"));
		}
		return temp;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Radians = Degrees * PI / 180
		double adwyzeLatitude = 12.9611159;
		double adwyzeLongitude = 77.6362214;
		int radius = 6371000;
		BufferedReader br = null;
		ArrayList<JSONObject> jsonArray = new ArrayList<JSONObject>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter File Path eg: D:\\go\\customers.json:");
		String filename = sc.nextLine();
		GeoFriends obj = new GeoFriends();
		obj.readJSONFile(br,filename,jsonArray);
		HashMap<Long,String> outputMap = obj.findDistances(radius,adwyzeLatitude,adwyzeLongitude,jsonArray);
		SortedSet<Long> keys = new TreeSet<Long>(outputMap.keySet());
		System.out.println("Friends near you:");
		for (Long key : keys) { 
		   String value = outputMap.get(key);
		   System.out.println("User id:"+key+" Name: "+value);
		}


	}

}
