package org.amaya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Component
public class HTTPDataSenderELK implements DataSenderELK {

	// public static String DateFormat = "yyyy-MM-dd hh:mm:ss";
	private String dataTime;
	private String latitude;
	private String longitude;
	private String postURL;
	private String oil;
	private String vNo;

	
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setOil(String oil) {
		this.oil = oil;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setPostURL(String postURL) {
		this.postURL = postURL;
	}

	
	
	public void setvNo(String vNo) {
		this.vNo = vNo;
	}

	/*
	 * post data of noofPosts each post at an interval of interval
	 * 
	 * @param noOfPosts no of posts
	 * @param interval post frequency , to ensure some kind of realtime simulation
	 * @throws InterruptedException if the specified collection is null
	 */
	public List<String> postData(int noOfPosts, int interval)
			throws InterruptedException, ParseException {

		List<String> returnArray = new ArrayList<String>();
		
		  	SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
			Date date = sdf.parse(this.dataTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
		
		for (int i = 0; i < noOfPosts; i++) {
			calendar.add(Calendar.SECOND, interval);
			this.dataTime = sdf.format(calendar.getTime());
			returnArray.add(this.postData());
		}

		return returnArray;

	}


	/*
	 * Send Data to HTTP server 
	 * (non-Javadoc)
	 * @see org.amaya.DataSenderELK#postData()
	 */
	public String postData() {

		String output = new String();
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(this.postURL);

			StringEntity input = new StringEntity("{\"VNo\":\"" + this.vNo + "\","
					+ "\"Time\":\"" + this.dataTime + "\",\"Acc\":\"On\""
					+ ",\"Lat\":23.25616,\"" + "Lon\":113.2564,\""
					+ "Speed\":50,\"" + "Angle\":120,\"" + "Locate\":\"V\",\""
					+ "Oil\":" + this.oil + "," + "\"Mile\":152}");
			
			
			input.setContentType("application/json");
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode()
						+ response.getStatusLine());

			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			
			System.out.println("Output from Server .... ::::");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return output;

	}

}
