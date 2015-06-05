package org.amaya;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;


public class TCPDataSenderELK implements DataSenderELK  {

	private String serverURL;
	private String serverPort;
	
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public boolean postData(String data) {

		DataInputStream is;
		DataOutputStream os;
		boolean result = true;
		String noReset = "Could not reset.";
		String reset = "The server has been reset.";
			
		try {
			Socket socket = new Socket(InetAddress.getByName(serverURL), Integer.parseInt(serverPort));
			is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());
			PrintWriter pw = new PrintWriter(os);
			pw.println(data);
			pw.flush();
				
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			JSONObject json = new JSONObject(in.readLine());
			if(!json.has("result")) {
				System.out.println(noReset);
				result = false;
			}
			is.close();
			os.close();
			socket.close();	
		} catch (IOException e) {
			result = false;
			System.out.println(noReset);
			e.printStackTrace();
			
		} catch (JSONException e) {
			result = false;
			System.out.println(noReset);
			e.printStackTrace();
		}
		System.out.println(reset);
		return result;
	}

	public String postData() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
