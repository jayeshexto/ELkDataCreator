package org.amaya;

import java.text.ParseException;

import javax.swing.plaf.SliderUI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {

	
	public static void main(String[] args) throws InterruptedException {
		
		//ToDo : Was writing code for 
		/*TCPDataSenderELK tse = new TCPDataSenderELK();
		
		tse.setServerURL("127.0.0.1");
		tse.setServerPort("6663");
		
		tse.postData("This is manual data");
		*/
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("org/amaya/beans/beans.xml");
		HTTPDataSenderELK dataCreator = (HTTPDataSenderELK)applicationContext.getBean("ELKDataCreator");
		
		//VehicleDataBuilder vbuild = new VehicleDataBuilder();
		
		dataCreator.setDataTime("2015-04-14 00:13:01");
		dataCreator.setOil("375");
		dataCreator.setvNo("13512345002");
		
		try {
			System.out.println( dataCreator.postData(100,1000) );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		((ClassPathXmlApplicationContext)applicationContext).close();
}
}