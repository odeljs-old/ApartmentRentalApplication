import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.*;
import java.util.*;
import java.time.*;

public class Project2 {

	public static void main(String[] args) throws FileNotFoundException, SQLException {
		
		Connection conn = null;	
	
		conn = (Connection) DriverManager.getConnection("jdbc:mysql://cisvm-winsrv-mysql1.unfcsd.unf.edu:3306/group4","n00869439","Group4Password");
		if(conn!=null) {
			System.out.println("Successfully connected to Project Database");
			System.out.println("");	
			Scanner input = new Scanner(System.in);
		
			int choice=0;
			while (choice!=2) {	
				choice=showMenu(input);
				if (choice==1) {								
					System.out.println("Sign in"); 
					runApp(conn);
				}
			
				if (choice==2) {
					System.out.println("Have a good day!");
				}
			}
			conn.close();
		}
	}
	
	public static int  showMenu(Scanner input) {
		/**
		 * This method shows the menu of available options. Try-Catch logic was implemented to ensure any errors
		 * are handled gracefully and allow the user to re-enter the information. The method's parameter (noted as "input") is
		 * a scanner class object. This allows the program to utilize information entered by the user to execute the desired option.
		 * The return variable is an integer that later indicates which method should be executed. 
		 * 
		 * @param "Input", a scanner class object, which allows the program to use information entered by the user.
		 * @return An integer, named "Choice", which allows the main method to know which option to execute.
		 */
		int choice =-1;									
		
		System.out.println("********************************************");
		System.out.println("Please choose one of the following options:");
		System.out.println("1. \tSign in");
		System.out.println("2. \tQuit");
		System.out.println("********************************************");
		while (choice==-1) {
			String typedInput=input.next();
		try {
			choice = Integer.valueOf(typedInput);							
		} catch (Exception error) {											
			System.out.println("Invalid input." + error.getMessage());		
			choice =-1;
			break;															
			}
		}
		return choice;
	}
	
	public static void runApp(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your username");
		String userName=input.nextLine();
		System.out.println("Enter your password");
		String password=input.nextLine();
		Statement statement = null;
		ResultSet resultSet = null;
		statement=conn.createStatement();
		String sql ="select * from group4.users U where U.username = '"+userName+"' and U.userpassword = '"+password+"'";
		resultSet = statement.executeQuery(sql);
		int userID = 0;
		int userTypeID=0;
		if(resultSet.next()) {
			do {
				String name = resultSet.getString("U.username");
				System.out.print("Welcome, " + name);
				System.out.print("\n");
				userID=resultSet.getInt("U.userID");
				userTypeID=resultSet.getInt("U.userTypeID");
			}
			while(resultSet.next());
			if(1==userTypeID) {
				admin(conn, userID);
			}	
			else {
				user(conn, userID);
			}
	}
		else{
			System.out.println("Sorry, that didn't match our records. Please try again.");
			}
		}
	
	public static void admin(Connection conn, int userID)throws SQLException{
		Scanner input = new Scanner(System.in);
		int choice=0;
		while (choice!=6) {	
			choice=adminMenu(conn);
			if (choice==1) {								
				System.out.println("Create Lease"); 
				adminCreateLeaseContract(conn, userID);
			}
			if (choice==2) {								
				System.out.println("History"); 
				adminCheckHistoryAndBalances(conn, userID);
			}
			if (choice==3) {								
				System.out.println("Fees and Terminations"); 
				adminAddLateFeesAndTerminations(conn, userID);
			}
			if (choice==4) {								
				System.out.println("Update Inventory"); 
				adminUpdateInventory(conn, userID);
			}
			if (choice==5) {								
				System.out.println("Update Report"); 
				adminUpdateReport(conn, userID);
			}
			
			if (choice==6) {
				System.out.println("Sign Out");
			}
		}
	}
	
	public static void user(Connection conn, int userID)throws SQLException{
		Scanner input = new Scanner(System.in);
		int choice=0;
		while (choice!=7) {	
			choice=userMenu(conn);
			if (choice==1) {								
				System.out.println("Search Options"); 
				userSearchOptions(conn,userID);
			}
			if (choice==2) {								
				System.out.println("Property Recommendations"); 
				userPropertyRecommendations(conn, userID);
			}
			if (choice==3) {								
				System.out.println("Request Lease"); 
				userRequestLease(conn, userID);
			}
			if (choice==4) {								
				System.out.println("Dues"); 
				userDues(conn, userID);
			}
			if (choice==5) {								
				System.out.println("Recommendations"); 
				userRecommendations(conn, userID);
			}
			if (choice==6) {								
				System.out.println("Request Terminations"); 
				userRequestTerminations(conn, userID);
			}
			
			if (choice==7) {
				System.out.println("Sign Out");
			}
		}
	}
	
	public static int adminMenu(Connection conn) throws SQLException{
		Scanner input = new Scanner(System.in);
		int choice =-1;									
		System.out.println("********************************************");
		System.out.println("Please choose one of the following options:");
		System.out.println("1. \tCreate Contract");
		System.out.println("2. \tHistory");
		System.out.println("3. \tFees and Terminations");
		System.out.println("4. \tUpdate Inventory");
		System.out.println("5. \tUpdate Report");
		System.out.println("6. \tSign Out");
		System.out.println("********************************************");
		while (choice==-1) {
			String typedInput=input.next();
		try {
			choice = Integer.valueOf(typedInput);							
		} catch (Exception error) {											
			System.out.println("Invalid input." + error.getMessage());		
			choice =-1;
			break;															
			}
		}
		return choice;
	}
	
	public static int userMenu(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		int choice =-1;									
		System.out.println("********************************************");
		System.out.println("Please choose one of the following options:");
		System.out.println("1. \tSearch Options");
		System.out.println("2. \tProperty Recommendations");
		System.out.println("3. \tRequest Lease");
		System.out.println("4. \tDues");
		System.out.println("5. \tRecommendations");
		System.out.println("6. \tRequest Terminations");
		System.out.println("7. \tSign Out");
		System.out.println("********************************************");
		while (choice==-1) {
			String typedInput=input.next();
		try {
			choice = Integer.valueOf(typedInput);							
		} catch (Exception error) {											
			System.out.println("Invalid input." + error.getMessage());		
			choice =-1;
			break;															
			}
		}
		return choice;
	}
	
	public static void userSearchOptions(Connection conn, int userID) throws SQLException {
		Scanner input = new Scanner(System.in);
		int numberOfBedrooms=0;
		int numberOfBathrooms=0;
		int price=0;
		String address = null;
		String city = null;
		String state = null;
		int zip = 0;
		String amenity=null;
		
			
		System.out.println("What kind of search would you like to do?");
		System.out.println("1. \tManual search based on criteria I select");
		System.out.println("2. \tAutomated search based on my previous history");
		try {
		int searchChoice=input.nextInt();
		if (searchChoice==1) {
			try {
		System.out.println("Please choose the criteria you want to search by:");
		System.out.println("********************************************");
		System.out.println("1. \tNumber of Bedrooms and Bathrooms, Price, Location, & Amenity");
		System.out.println("2. \tNumber of Bedrooms and Bathrooms, Price, & Location");
		System.out.println("3. \tNumber of Bedrooms and Bathrooms, Price, & Amenity");
		System.out.println("4. \tNumber of Bedrooms and Bathrooms, Location, & Amenity");

		System.out.println("5 \tNumber of Bedrooms and Bathrooms & Price");
		System.out.println("6. \tNumber of Bedrooms and Bathrooms & Location");
		System.out.println("7. \tNumber of Bedrooms and Bathrooms & Amenity");
		System.out.println("8. \tPrice, Location, & Amenity");
		System.out.println("9. \tPrice & Location");
		System.out.println("10. \tPrice & Amenity");
		System.out.println("11. \tLocation, & Amenity");
		System.out.println("12. \tNumber of Bedrooms and Bathrooms");
		System.out.println("13. \tPrice");
		System.out.println("14. \tLocation");
		System.out.println("15. \tAmenity");
		System.out.println("********************************************");
		
		int choice = input.nextInt();
			switch(choice) {
				case 1: {
					System.out.println("Number of bedrooms:");
					numberOfBedrooms=input.nextInt();
					System.out.println("Number of bathrooms:");
					numberOfBathrooms=input.nextInt();
					
					System.out.println("What is the highest you'd be willing to pay?");
					price=input.nextInt();
					input.nextLine();
					System.out.println("street address:");
					address = input.nextLine();
					System.out.println("city:");
					city = input.nextLine();
					System.out.println("state:");
					state = input.nextLine();

					System.out.println("zip:");
					zip = input.nextInt();

					
					System.out.println("Amenity:");
					System.out.println("As a reminder, these are the amenities you can search by. Please choose the number of the desired amenity:");
					Statement amenitiesStatement = null;
					ResultSet amenitiesResultSet = null;
					amenitiesStatement=conn.createStatement();
					String amenitiesSQL ="select * from group4.amenities a";
					amenitiesResultSet = amenitiesStatement.executeQuery(amenitiesSQL);
					if(amenitiesResultSet.next()) {
						do {
							int amenityOption = amenitiesResultSet.getInt("a.amenityID");
							System.out.print("\n");
							amenity=amenitiesResultSet.getString("a.amenity");
							System.out.println(amenityOption +". " + amenity);
						}
						while(amenitiesResultSet.next());
						
				}
					int amenitySelection=input.nextInt();
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"a.amenity,a.amenityID, c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID " + 
							"join group4.amenities a on a.amenityID=pa.amenityID   " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pu.numberOfBedrooms= '"+numberOfBedrooms+"' and pu.numberOfBathrooms= '"+numberOfBathrooms+"' " + 
							"and c.monthlyCost<='"+price+"' and p.address='"+address+"' " + 
							"and p.city ='"+city+"' and p.state='"+state+"' and p.zip='"+zip+"' " + 
							"and exists ( " + 
							"select * " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID  " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pa.amenityID='"+amenitySelection+"') ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							int resultAmenityID=searchResultSet.getInt("a.amenityID");
							String resultAmenity = searchResultSet.getString("a.amenity");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");
							if(resultAmenityID==amenitySelection) {
								System.out.print("\n");
								System.out.println("Property Unit ID: " + resultPropertyUnitID);
								System.out.println("Property ID: " + resultPropertyID);
								System.out.println("Property Name: " + resultPropertyName);
								System.out.println("Address: " + resultAddress);
								System.out.println("City: " + resultCity);
								System.out.println("State: " + resultState);
								System.out.println("Zip: " + resultZip);
								System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
								System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
								System.out.println("Monthly Cost: " + resultPrice);
								System.out.println("Duration (in months): " + resultDuration);
								System.out.println("Amenity: " + resultAmenity);
								System.out.println("BT bus lines: " + resultBTBusLines);
								System.out.println("Crime rate: " + resultCrimeRate);
								System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
								System.out.print("\n");
								
							}

						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;
					
				}
				case 2: {
				
					System.out.println("Number of bedrooms:");
					numberOfBedrooms=input.nextInt();
					System.out.println("Number of bathrooms:");
					numberOfBathrooms=input.nextInt();
					
					System.out.println("What is the highest you'd be willing to pay?");
					price=input.nextInt();
					input.nextLine();
					System.out.println("street address:");
					address = input.nextLine();
					System.out.println("city:");
					city = input.nextLine();
					System.out.println("state:");
					state = input.nextLine();

					System.out.println("zip:");
					zip = input.nextInt();

					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pu.numberOfBedrooms= '"+numberOfBedrooms+"' and pu.numberOfBathrooms= '"+numberOfBathrooms+"' " + 
							"and c.monthlyCost<='"+price+"' and p.address='"+address+"' " + 
							"and p.city ='"+city+"' and p.state='"+state+"' and p.zip='"+zip+"'";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							System.out.print("\n");
							System.out.println("Property Unit ID: " + resultPropertyUnitID);
							System.out.println("Property ID: " + resultPropertyID);
							System.out.println("Property Name: " + resultPropertyName);
							System.out.println("Address: " + resultAddress);
							System.out.println("City: " + resultCity);
							System.out.println("State: " + resultState);
							System.out.println("Zip: " + resultZip);
							System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
							System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
							System.out.println("Monthly Cost: " + resultPrice);
							System.out.println("Duration (in months): " + resultDuration);
							System.out.println("BT bus lines: " + resultBTBusLines);
							System.out.println("Crime rate: " + resultCrimeRate);
							System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
							System.out.print("\n");
							

						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				}
				case 3: {
					System.out.println("Number of bedrooms:");
					numberOfBedrooms=input.nextInt();
					System.out.println("Number of bathrooms:");
					numberOfBathrooms=input.nextInt();
					
					System.out.println("What is the highest you'd be willing to pay?");
					price=input.nextInt();
					input.nextLine();
					
					System.out.println("Amenity:");
					System.out.println("As a reminder, these are the amenities you can search by. Please choose the number of the desired amenity:");
					Statement amenitiesStatement = null;
					ResultSet amenitiesResultSet = null;
					amenitiesStatement=conn.createStatement();
					String amenitiesSQL ="select * from group4.amenities a";
					amenitiesResultSet = amenitiesStatement.executeQuery(amenitiesSQL);
					if(amenitiesResultSet.next()) {
						do {
							int amenityOption = amenitiesResultSet.getInt("a.amenityID");
							System.out.print("\n");
							amenity=amenitiesResultSet.getString("a.amenity");
							System.out.println(amenityOption +". " + amenity);
						}
						while(amenitiesResultSet.next());
						
				}
					int amenitySelection=input.nextInt();
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"a.amenity, a.amenityID,c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID " + 
							"join group4.amenities a on a.amenityID=pa.amenityID   " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pu.numberOfBedrooms= '"+numberOfBedrooms+"' and pu.numberOfBathrooms= '"+numberOfBathrooms+"' " + 
							"and c.monthlyCost<='"+price+"' "+ 
							" and exists ( " + 
							"select * " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID  " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pa.amenityID='"+amenitySelection+"') ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultAmenity = searchResultSet.getString("a.amenity");
							int resultAmenityID= searchResultSet.getInt("a.amenityID");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");
							if(resultAmenityID==amenitySelection) {
								System.out.print("\n");
								System.out.println("Property Unit ID: " + resultPropertyUnitID);
								System.out.println("Property ID: " + resultPropertyID);
								System.out.println("Property Name: " + resultPropertyName);
								System.out.println("Address: " + resultAddress);
								System.out.println("City: " + resultCity);
								System.out.println("State: " + resultState);
								System.out.println("Zip: " + resultZip);
								System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
								System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
								System.out.println("Monthly Cost: " + resultPrice);
								System.out.println("Duration (in months): " + resultDuration);
								System.out.println("Amenity: " + resultAmenity);
								System.out.println("BT bus lines: " + resultBTBusLines);
								System.out.println("Crime rate: " + resultCrimeRate);
								System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
								System.out.print("\n");
								
							}
						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;
				}
				case 4: {
				
					System.out.println("Number of bedrooms:");
					numberOfBedrooms=input.nextInt();
					System.out.println("Number of bathrooms:");
					numberOfBathrooms=input.nextInt();
					
					
					input.nextLine();
					System.out.println("street address:");
					address = input.nextLine();
					System.out.println("city:");
					city = input.nextLine();
					System.out.println("state:");
					state = input.nextLine();

					System.out.println("zip:");
					zip = input.nextInt();

					
					System.out.println("Amenity:");
					System.out.println("As a reminder, these are the amenities you can search by. Please choose the number of the desired amenity:");
					Statement amenitiesStatement = null;
					ResultSet amenitiesResultSet = null;
					amenitiesStatement=conn.createStatement();
					String amenitiesSQL ="select * from group4.amenities a";
					amenitiesResultSet = amenitiesStatement.executeQuery(amenitiesSQL);
					if(amenitiesResultSet.next()) {
						do {
							int amenityOption = amenitiesResultSet.getInt("a.amenityID");
							System.out.print("\n");
							amenity=amenitiesResultSet.getString("a.amenity");
							System.out.println(amenityOption +". " + amenity);
						}
						while(amenitiesResultSet.next());
						
				}
					int amenitySelection=input.nextInt();
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"a.amenity, a.amenityID, c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID " + 
							"join group4.amenities a on a.amenityID=pa.amenityID   " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pu.numberOfBedrooms= '"+numberOfBedrooms+"' and pu.numberOfBathrooms= '"+numberOfBathrooms+"' " + 
							"and p.address='"+address+"' " + 
							"and p.city ='"+city+"' and p.state='"+state+"' and p.zip='"+zip+"' " + 
							"and exists ( " + 
							"select * " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID  " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pa.amenityID='"+amenitySelection+"') ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultAmenity = searchResultSet.getString("a.amenity");
							int resultAmenityID=searchResultSet.getInt("a.amenityID");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							if(resultAmenityID==amenitySelection) {
								System.out.print("\n");
								System.out.println("Property Unit ID: " + resultPropertyUnitID);
								System.out.println("Property ID: " + resultPropertyID);
								System.out.println("Property Name: " + resultPropertyName);
								System.out.println("Address: " + resultAddress);
								System.out.println("City: " + resultCity);
								System.out.println("State: " + resultState);
								System.out.println("Zip: " + resultZip);
								System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
								System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
								System.out.println("Monthly Cost: " + resultPrice);
								System.out.println("Duration (in months): " + resultDuration);
								System.out.println("Amenity: " + resultAmenity);
								System.out.println("BT bus lines: " + resultBTBusLines);
								System.out.println("Crime rate: " + resultCrimeRate);
								System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
								System.out.print("\n");
								
							}
						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				}
				case 5: {
				
					System.out.println("Number of bedrooms:");
					numberOfBedrooms=input.nextInt();
					System.out.println("Number of bathrooms:");
					numberOfBathrooms=input.nextInt();
					
					System.out.println("What is the highest you'd be willing to pay?");
					price=input.nextInt();
					
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pu.numberOfBedrooms= '"+numberOfBedrooms+"'" +
							"and pu.numberOfBedrooms= '"+numberOfBathrooms+"'" +
							"and c.monthlyCost= '"+price+"'";



					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							System.out.print("\n");
							System.out.println("Property Unit ID: " + resultPropertyUnitID);
							System.out.println("Property ID: " + resultPropertyID);
							System.out.println("Property Name: " + resultPropertyName);
							System.out.println("Address: " + resultAddress);
							System.out.println("City: " + resultCity);
							System.out.println("State: " + resultState);
							System.out.println("Zip: " + resultZip);
							System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
							System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
							System.out.println("Monthly Cost: " + resultPrice);
							System.out.println("Duration (in months): " + resultDuration);
							System.out.println("BT bus lines: " + resultBTBusLines);
							System.out.println("Crime rate: " + resultCrimeRate);
							System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
							System.out.print("\n");
							

						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				}
				case 6: {
				
					System.out.println("Number of bedrooms:");
					numberOfBedrooms=input.nextInt();
					System.out.println("Number of bathrooms:");
					numberOfBathrooms=input.nextInt();
					
					input.nextLine();
					System.out.println("street address:");
					address = input.nextLine();
					System.out.println("city:");
					city = input.nextLine();
					System.out.println("state:");
					state = input.nextLine();

					System.out.println("zip:");
					zip = input.nextInt();

					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pu.numberOfBedrooms= '"+numberOfBedrooms+"' and pu.numberOfBathrooms= '"+numberOfBathrooms+"' " + 
							"and p.address='"+address+"' " + 
							"and p.city ='"+city+"' and p.state='"+state+"' and p.zip='"+zip+"'";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							System.out.print("\n");
							System.out.println("Property Unit ID: " + resultPropertyUnitID);
							System.out.println("Property ID: " + resultPropertyID);
							System.out.println("Property Name: " + resultPropertyName);
							System.out.println("Address: " + resultAddress);
							System.out.println("City: " + resultCity);
							System.out.println("State: " + resultState);
							System.out.println("Zip: " + resultZip);
							System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
							System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
							System.out.println("Monthly Cost: " + resultPrice);
							System.out.println("Duration (in months): " + resultDuration);
							System.out.println("BT bus lines: " + resultBTBusLines);
							System.out.println("Crime rate: " + resultCrimeRate);
							System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
							System.out.print("\n");
						

						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				}
				case 7: {
				
					System.out.println("Number of bedrooms:");
					numberOfBedrooms=input.nextInt();
					System.out.println("Number of bathrooms:");
					numberOfBathrooms=input.nextInt();
					
					System.out.println("Amenity:");
					System.out.println("As a reminder, these are the amenities you can search by. Please choose the number of the desired amenity:");
					Statement amenitiesStatement = null;
					ResultSet amenitiesResultSet = null;
					amenitiesStatement=conn.createStatement();
					String amenitiesSQL ="select * from group4.amenities a";
					amenitiesResultSet = amenitiesStatement.executeQuery(amenitiesSQL);
					if(amenitiesResultSet.next()) {
						do {
							int amenityOption = amenitiesResultSet.getInt("a.amenityID");
							System.out.print("\n");
							amenity=amenitiesResultSet.getString("a.amenity");
							System.out.println(amenityOption +". " + amenity);
						}
						while(amenitiesResultSet.next());
						
				}
					int amenitySelection=input.nextInt();
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"a.amenity, a.amenityID, c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID " + 
							"join group4.amenities a on a.amenityID=pa.amenityID   " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pu.numberOfBedrooms= '"+numberOfBedrooms+"' and pu.numberOfBathrooms= '"+numberOfBathrooms+"' " +  
							"and exists ( " + 
							"select * " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID  " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pa.amenityID='"+amenitySelection+"') ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultAmenity = searchResultSet.getString("a.amenity");
							int resultAmenityID=searchResultSet.getInt("a.amenityID");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");
							if(resultAmenityID==amenitySelection) {
								System.out.print("\n");
								System.out.println("Property Unit ID: " + resultPropertyUnitID);
								System.out.println("Property ID: " + resultPropertyID);
								System.out.println("Property Name: " + resultPropertyName);
								System.out.println("Address: " + resultAddress);
								System.out.println("City: " + resultCity);
								System.out.println("State: " + resultState);
								System.out.println("Zip: " + resultZip);
								System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
								System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
								System.out.println("Monthly Cost: " + resultPrice);
								System.out.println("Duration (in months): " + resultDuration);
								System.out.println("Amenity: " + resultAmenity);
								System.out.println("BT bus lines: " + resultBTBusLines);
								System.out.println("Crime rate: " + resultCrimeRate);
								System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
								System.out.print("\n");
								
							}
						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				}
				case 8: {
					
					
					System.out.println("What is the highest you'd be willing to pay?");
					price=input.nextInt();
					input.nextLine();
					System.out.println("street address:");
					address = input.nextLine();
					System.out.println("city:");
					city = input.nextLine();
					System.out.println("state:");
					state = input.nextLine();

					System.out.println("zip:");
					zip = input.nextInt();

					
					System.out.println("Amenity:");
					System.out.println("As a reminder, these are the amenities you can search by. Please choose the number of the desired amenity:");
					Statement amenitiesStatement = null;
					ResultSet amenitiesResultSet = null;
					amenitiesStatement=conn.createStatement();
					String amenitiesSQL ="select * from group4.amenities a";
					amenitiesResultSet = amenitiesStatement.executeQuery(amenitiesSQL);
					if(amenitiesResultSet.next()) {
						do {
							int amenityOption = amenitiesResultSet.getInt("a.amenityID");
							System.out.print("\n");
							amenity=amenitiesResultSet.getString("a.amenity");
							System.out.println(amenityOption +". " + amenity);
						}
						while(amenitiesResultSet.next());
						
				}
					int amenitySelection=input.nextInt();
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"a.amenity, a.amenityID, c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID " + 
							"join group4.amenities a on a.amenityID=pa.amenityID   " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where c.monthlyCost<='"+price+"' and p.address='"+address+"' " + 
							"and p.city ='"+city+"' and p.state='"+state+"' and p.zip='"+zip+"' " + 
							"and exists ( " + 
							"select * " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID  " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pa.amenityID='"+amenitySelection+"') ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultAmenity = searchResultSet.getString("a.amenity");
							int resultAmenityID=searchResultSet.getInt("a.amenityID");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");
							
							if(resultAmenityID==amenitySelection) {
								System.out.print("\n");
								System.out.println("Property Unit ID: " + resultPropertyUnitID);
								System.out.println("Property ID: " + resultPropertyID);
								System.out.println("Property Name: " + resultPropertyName);
								System.out.println("Address: " + resultAddress);
								System.out.println("City: " + resultCity);
								System.out.println("State: " + resultState);
								System.out.println("Zip: " + resultZip);
								System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
								System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
								System.out.println("Monthly Cost: " + resultPrice);
								System.out.println("Duration (in months): " + resultDuration);
								System.out.println("Amenity: " + resultAmenity);
								System.out.println("BT bus lines: " + resultBTBusLines);
								System.out.println("Crime rate: " + resultCrimeRate);
								System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
								System.out.print("\n");
							
							}
						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				
				}
				case 9: {
					
					
					System.out.println("What is the highest you'd be willing to pay?");
					price=input.nextInt();
					input.nextLine();
					System.out.println("street address:");
					address = input.nextLine();
					System.out.println("city:");
					city = input.nextLine();
					System.out.println("state:");
					state = input.nextLine();

					System.out.println("zip:");
					zip = input.nextInt();

					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " +  
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							
							"where c.monthlyCost<='"+price+"' and p.address='"+address+"' " + 
							"and p.city ='"+city+"' and p.state='"+state+"' and p.zip='"+zip+"' ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							System.out.print("\n");
							System.out.println("Property Unit ID: " + resultPropertyUnitID);
							System.out.println("Property ID: " + resultPropertyID);
							System.out.println("Property Name: " + resultPropertyName);
							System.out.println("Address: " + resultAddress);
							System.out.println("City: " + resultCity);
							System.out.println("State: " + resultState);
							System.out.println("Zip: " + resultZip);
							System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
							System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
							System.out.println("Monthly Cost: " + resultPrice);
							System.out.println("Duration (in months): " + resultDuration);
							System.out.println("BT bus lines: " + resultBTBusLines);
							System.out.println("Crime rate: " + resultCrimeRate);
							System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
							System.out.print("\n");
							

						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				
				}
				case 10: {
					
					
					System.out.println("What is the highest you'd be willing to pay?");
					price=input.nextInt();
					
					System.out.println("Amenity:");
					System.out.println("As a reminder, these are the amenities you can search by. Please choose the number of the desired amenity:");
					Statement amenitiesStatement = null;
					ResultSet amenitiesResultSet = null;
					amenitiesStatement=conn.createStatement();
					String amenitiesSQL ="select * from group4.amenities a";
					amenitiesResultSet = amenitiesStatement.executeQuery(amenitiesSQL);
					if(amenitiesResultSet.next()) {
						do {
							int amenityOption = amenitiesResultSet.getInt("a.amenityID");
							System.out.print("\n");
							amenity=amenitiesResultSet.getString("a.amenity");
							System.out.println(amenityOption +". " + amenity);
						}
						while(amenitiesResultSet.next());
						
				}
					int amenitySelection=input.nextInt();
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"a.amenity, a.amenityID, c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID " + 
							"join group4.amenities a on a.amenityID=pa.amenityID   " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where c.monthlyCost<='"+price+"' "+
							"and exists ( " + 
							"select * " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID  " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pa.amenityID='"+amenitySelection+"') ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultAmenity = searchResultSet.getString("a.amenity");
							int resultAmenityID=searchResultSet.getInt("a.amenityID");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							if(resultAmenityID==amenitySelection) {
								System.out.print("\n");
								System.out.println("Property Unit ID: " + resultPropertyUnitID);
								System.out.println("Property ID: " + resultPropertyID);
								System.out.println("Property Name: " + resultPropertyName);
								System.out.println("Address: " + resultAddress);
								System.out.println("City: " + resultCity);
								System.out.println("State: " + resultState);
								System.out.println("Zip: " + resultZip);
								System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
								System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
								System.out.println("Monthly Cost: " + resultPrice);
								System.out.println("Duration (in months): " + resultDuration);
								System.out.println("Amenity: " + resultAmenity);
								System.out.println("BT bus lines: " + resultBTBusLines);
								System.out.println("Crime rate: " + resultCrimeRate);
								System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
								System.out.print("\n");

							}
						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				
				}
				case 11: {
					System.out.println("street address:");
					input.nextLine();

					address = input.nextLine();
					System.out.println("city:");
					city = input.nextLine();
					System.out.println("state:");

					state = input.nextLine();

					System.out.println("zip:");
					zip = input.nextInt();

					
					System.out.println("Amenity:");
					System.out.println("As a reminder, these are the amenities you can search by. Please choose the number of the desired amenity:");
					Statement amenitiesStatement = null;
					ResultSet amenitiesResultSet = null;
					amenitiesStatement=conn.createStatement();
					String amenitiesSQL ="select * from group4.amenities a";
					amenitiesResultSet = amenitiesStatement.executeQuery(amenitiesSQL);
					if(amenitiesResultSet.next()) {
						do {
							int amenityOption = amenitiesResultSet.getInt("a.amenityID");
							System.out.print("\n");
							amenity=amenitiesResultSet.getString("a.amenity");
							System.out.println(amenityOption +". " + amenity);
						}
						while(amenitiesResultSet.next());
						
				}
					int amenitySelection=input.nextInt();
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"a.amenity, a.amenityID, c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID " + 
							"join group4.amenities a on a.amenityID=pa.amenityID   " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where p.address='"+address+"' " + 
							"and p.city ='"+city+"' and p.state='"+state+"' and p.zip='"+zip+"' " + 
							"and exists ( " + 
							"select * " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID  " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pa.amenityID='"+amenitySelection+"') ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultAmenity = searchResultSet.getString("a.amenity");
							int resultAmenityID=searchResultSet.getInt("a.amenityID");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							if(resultAmenityID==amenitySelection) {
								System.out.print("\n");
								System.out.println("Property Unit ID: " + resultPropertyUnitID);
								System.out.println("Property ID: " + resultPropertyID);
								System.out.println("Property Name: " + resultPropertyName);
								System.out.println("Address: " + resultAddress);
								System.out.println("City: " + resultCity);
								System.out.println("State: " + resultState);
								System.out.println("Zip: " + resultZip);
								System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
								System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
								System.out.println("Monthly Cost: " + resultPrice);
								System.out.println("Duration (in months): " + resultDuration);
								System.out.println("Amenity: " + resultAmenity);
								System.out.println("BT bus lines: " + resultBTBusLines);
								System.out.println("Crime rate: " + resultCrimeRate);
								System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
								System.out.print("\n");

							}
						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				
				}
				case 12: {
					
					System.out.println("Number of bedrooms:");
					numberOfBedrooms=input.nextInt();
					System.out.println("Number of bathrooms:");
					numberOfBathrooms=input.nextInt();
					
					
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pu.numberOfBedrooms= '"+numberOfBedrooms+"' and pu.numberOfBathrooms= '"+numberOfBathrooms+"'";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							System.out.print("\n");
							System.out.println("Property Unit ID: " + resultPropertyUnitID);
							System.out.println("Property ID: " + resultPropertyID);
							System.out.println("Property Name: " + resultPropertyName);
							System.out.println("Address: " + resultAddress);
							System.out.println("City: " + resultCity);
							System.out.println("State: " + resultState);
							System.out.println("Zip: " + resultZip);
							System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
							System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
							System.out.println("Monthly Cost: " + resultPrice);
							System.out.println("Duration (in months): " + resultDuration);
							System.out.println("BT bus lines: " + resultBTBusLines);
							System.out.println("Crime rate: " + resultCrimeRate);
							System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
							System.out.print("\n");


						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				
				}
				case 13: {
					
					
					
					System.out.println("What is the highest you'd be willing to pay?");
					price=input.nextInt();
					
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " +  
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where c.monthlyCost<='"+price+"'";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							System.out.print("\n");
							System.out.println("Property Unit ID: " + resultPropertyUnitID);
							System.out.println("Property ID: " + resultPropertyID);
							System.out.println("Property Name: " + resultPropertyName);
							System.out.println("Address: " + resultAddress);
							System.out.println("City: " + resultCity);
							System.out.println("State: " + resultState);
							System.out.println("Zip: " + resultZip);
							System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
							System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
							System.out.println("Monthly Cost: " + resultPrice);
							System.out.println("Duration (in months): " + resultDuration);
							System.out.println("BT bus lines: " + resultBTBusLines);
							System.out.println("Crime rate: " + resultCrimeRate);
							System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
							System.out.print("\n");
						

						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				
				}
				case 14: {
				

					System.out.println("street address:");
					input.nextLine();

					address = input.nextLine();
					System.out.println("city:");
					city = input.nextLine();
					System.out.println("state:");

					state = input.nextLine();
					System.out.println("zip:");
					zip = input.nextInt();
					System.out.println(address+city+state+zip);
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where p.address='"+address+"' " + 
							"and p.city ='"+city+"' and p.state='"+state+"' and p.zip='"+zip+"' ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");

							System.out.print("\n");
							System.out.println("Property Unit ID: " + resultPropertyUnitID);
							System.out.println("Property ID: " + resultPropertyID);
							System.out.println("Property Name: " + resultPropertyName);
							System.out.println("Address: " + resultAddress);
							System.out.println("City: " + resultCity);
							System.out.println("State: " + resultState);
							System.out.println("Zip: " + resultZip);
							System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
							System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
							System.out.println("Monthly Cost: " + resultPrice);
							System.out.println("Duration (in months): " + resultDuration);
							System.out.println("BT bus lines: " + resultBTBusLines);
							System.out.println("Crime rate: " + resultCrimeRate);
							System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
							System.out.print("\n");


						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				}
				case 15: {
					
					
					System.out.println("Amenity:");
					System.out.println("As a reminder, these are the amenities you can search by. Please choose the number of the desired amenity:");
					Statement amenitiesStatement = null;
					ResultSet amenitiesResultSet = null;
					amenitiesStatement=conn.createStatement();
					String amenitiesSQL ="select * from group4.amenities a";
					amenitiesResultSet = amenitiesStatement.executeQuery(amenitiesSQL);
					if(amenitiesResultSet.next()) {
						do {
							int amenityOption = amenitiesResultSet.getInt("a.amenityID");
							System.out.print("\n");
							amenity=amenitiesResultSet.getString("a.amenity");
							System.out.println(amenityOption +". " + amenity);
						}
						while(amenitiesResultSet.next());
						
				}
					int amenitySelection=input.nextInt();
					
					Statement searchStatement = null;
					ResultSet searchResultSet = null;
					searchStatement=conn.createStatement();
					String query ="select DISTINCT pu.propertyunitID,  pu.isAvailable,p.propertyID," + 
							"pu.numberOfBedrooms, pu.numberOfBathrooms,  " + 
							"p.address, p.propertyName, p.city, p.STATE, p.ZIP, p.btBusLines, p.crimeRates, p.distanceFromUNF, " + 
							"a.amenity, a.amenityID, c.duration, c.monthlyCost " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID " + 
							"join group4.amenities a on a.amenityID=pa.amenityID   " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where exists ( " + 
							"select * " + 
							"from group4.property_units pu  " + 
							"join group4.properties p on p.propertyID=pu.propertyID " + 
							"join group4.property_amenities pa on pa.propertyID=p.propertyID  " + 
							"join group4.leases l on l.propertyUnitID=pu.propertyUnitID  " + 
							"join group4.contracts c on c.leaseID=l.leaseID  " + 
							"where pa.amenityID='"+amenitySelection+"') ";


					searchResultSet = searchStatement.executeQuery(query);
					if(searchResultSet.next()) {
						do {
							int resultPropertyUnitID =searchResultSet.getInt("pu.propertyUnitID");
							int resultPropertyID =searchResultSet.getInt("p.propertyID");
							int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
							int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
							int resultDuration = searchResultSet.getInt("c.duration");
							int resultPrice = searchResultSet.getInt("c.monthlyCost");
							String resultAmenity = searchResultSet.getString("a.amenity");
							int resultAmenityID=searchResultSet.getInt("a.amenityID");
							String resultPropertyName = searchResultSet.getString("p.propertyName");
							String resultAddress = searchResultSet.getString("p.address");
							String resultCity = searchResultSet.getString("p.city");
							String resultState = searchResultSet.getString("p.state");
							int resultZip = searchResultSet.getInt("p.zip");
							String resultBTBusLines = searchResultSet.getString("p.btBusLines");
							int resultCrimeRate = searchResultSet.getInt("p.crimeRates");
							int resultDistanceFromUNF = searchResultSet.getInt("p.distanceFromUNF");
							
							if(resultAmenityID==amenitySelection) {
								System.out.print("\n");
								System.out.println("Property Unit ID: " + resultPropertyUnitID);
								System.out.println("Property ID: " + resultPropertyID);
								System.out.println("Property Name: " + resultPropertyName);
								System.out.println("Address: " + resultAddress);
								System.out.println("City: " + resultCity);
								System.out.println("State: " + resultState);
								System.out.println("Zip: " + resultZip);
								System.out.println("Number of bedrooms: " + resultNumberOfBedrooms);
								System.out.println("Number of bathrooms: " + resultNumberOfBathrooms);
								System.out.println("Monthly Cost: " + resultPrice);
								System.out.println("Duration (in months): " + resultDuration);
								System.out.println("Amenity: " + resultAmenity);
								System.out.println("BT bus lines: " + resultBTBusLines);
								System.out.println("Crime rate: " + resultCrimeRate);
								System.out.println("Distance from UNF (In Miles): " + resultDistanceFromUNF);
								System.out.print("\n");

							}
						}
						while(searchResultSet.next());
					}
						else
							System.out.println("Sorry, there are no results under the specified criteria.");
						
					
					break;

				
				}
				default: {
					System.out.println("Sorry, that wasn't a valid input. Please try again.");
					userSearchOptions(conn,userID);
				}
			}
			}
			catch(Exception firstIfErr) {
				System.out.println("Sorry, that wasn't a valid input. Please try again. " + firstIfErr.getMessage());
				user(conn,userID);
			}
		}
		
		if(searchChoice==2) {
			try {
				System.out.println("Executed userPropertyRecommendations");
				ArrayList<Integer> numBedList = new ArrayList<Integer>();
				ArrayList<Integer> numBathList = new ArrayList<Integer>();
				ArrayList<Integer> priceList = new ArrayList<Integer>();
				ArrayList<Integer> zipList = new ArrayList<Integer>();
				ArrayList<Integer> amenityIDList = new ArrayList<Integer>();
				ArrayList<Integer> propertyUnitsList = new ArrayList<Integer>();


				Statement searchStatement = null;
				ResultSet searchResultSet = null;
				searchStatement=conn.createStatement();
				String searchQuery ="select * from group4.property_units pu join group4.properties p on p.propertyID=pu.propertyID "
						+ "join group4.property_amenities pa on pa.propertyID=p.propertyID "
						+ "join group4.leases l on l.propertyUnitID=pu.propertyUnitID "
						+ "join group4.lease_history lh on lh.leaseID=l.leaseID "
						+ "join group4.contracts c on c.leaseID=l.leaseID "
						+ "where lh.userID = '"+userID+"'";

				searchResultSet = searchStatement.executeQuery(searchQuery);
				if(searchResultSet.next()) {
					do {
						int resultNumberOfBedrooms = searchResultSet.getInt("pu.numberOfBedrooms");
						numBedList.add(resultNumberOfBedrooms);
						int resultNumberOfBathrooms = searchResultSet.getInt("pu.numberOfBathrooms");
						numBathList.add(resultNumberOfBathrooms);
						int resultPrice = searchResultSet.getInt("c.monthlyCost");
						priceList.add(resultPrice);

						int resultAmenity = searchResultSet.getInt("pa.amenityID");
						amenityIDList.add(resultAmenity);

						int resultZip = searchResultSet.getInt("p.zip");
						zipList.add(resultZip);


					}
					while(searchResultSet.next());
				}
					Collections.sort(numBedList);
					int minNumBed = numBedList.get(0);
					int maxNumBed = numBedList.get(numBedList.size()-1);
					Collections.sort(numBathList);
					int minNumBath = numBathList.get(0);
					int maxNumBath = numBathList.get(numBathList.size()-1);
					Collections.sort(priceList);
					int minPrice = priceList.get(0);
					int maxPrice = priceList.get(priceList.size()-1);
					Collections.sort(zipList);
					Collections.sort(amenityIDList);
					
					Statement recStatement = null;
					ResultSet recResultSet = null;
					recStatement=conn.createStatement();
					String recQuery ="select  distinct pu.propertyUnitID, pu.propertyID, pu.isAvailable,"
							+ "pu.numberOfBedrooms, pu.numberOfBathrooms, p.propertyName, "
							+ "p.address, p.city, p.state, p.zip, pa.amenityID, c.monthlyCost, c.duration "
							+ "from group4.property_units pu "
							+ "join group4.properties p on p.propertyID=pu.propertyID "
							+ "join group4.property_amenities pa on pa.propertyID=p.propertyID "
							+ "join group4.leases l on l.propertyUnitID=pu.propertyUnitID "
							+ "join group4.contracts c on c.leaseID=l.leaseID "
							+ "where pu.numberOfBedrooms between '"+minNumBed+"' and '"+maxNumBed+"' "
							+ "and pu.numberOfBathrooms between '"+minNumBath+"' and '"+maxNumBath+"' "
							+ "and c.monthlyCost between'"+minPrice+"' and '"+maxPrice+"' ";

					recResultSet = recStatement.executeQuery(recQuery);
					int counter=0;
					if(recResultSet.next()) {
					do {
						int recPropertyUnitID =recResultSet.getInt("pu.propertyUnitID");
						int recPropertyID =recResultSet.getInt("pu.propertyID");
						int recNumberOfBedrooms = recResultSet.getInt("pu.numberOfBedrooms");
						int recNumberOfBathrooms = recResultSet.getInt("pu.numberOfBathrooms");
						int recIsAvailable = recResultSet.getInt("pu.isAvailable");
						int recPrice = recResultSet.getInt("c.monthlyCost");
						int recDuration = recResultSet.getInt("c.duration");
						int recAmenityID=recResultSet.getInt("pa.amenityID");
						String recPropertyName = recResultSet.getString("p.propertyName");
						String recAddress = recResultSet.getString("p.address");
						String recCity = recResultSet.getString("p.city");
						String recState = recResultSet.getString("p.state");
						int recZip = recResultSet.getInt("p.zip");

						if(zipList.contains(recZip) && amenityIDList.contains(recAmenityID) && 
							!propertyUnitsList.contains(recPropertyUnitID) && recIsAvailable==1) {
							System.out.print("\n");
							System.out.println("Property Unit ID: " + recPropertyUnitID);
							System.out.println("Property ID: " + recPropertyID);
							System.out.println("Property Name: " + recPropertyName);
							System.out.println("Address: " + recAddress);
							System.out.println("City: " + recCity);
							System.out.println("State: " + recState);
							System.out.println("Zip: " + recZip);
							System.out.println("Number of bedrooms: " + recNumberOfBedrooms);
							System.out.println("Number of bathrooms: " + recNumberOfBathrooms);
							System.out.println("Monthly Cost: " + recPrice);
							System.out.println("Duration (in months): " + recDuration);
							System.out.print("\n");
							propertyUnitsList.add(recPropertyUnitID);
							counter++;
						}
					}
					while(recResultSet.next());
					if(counter==0)
						System.out.println("Sorry, there are no results based on your history. We recommend using a manual search instead.");

				}
					else
						System.out.println("Sorry, there are no results based on your history.");
				}
				catch (Exception err) {
					System.out.println("Sorry, that wasn't a valid input." + " " +err.getMessage());
					user(conn, userID);
				}
			
		}
		if(searchChoice!=1 && searchChoice!=2) {
			System.out.println("Sorry, that wasn't a valid input. Please try again.");
			userSearchOptions(conn,userID);
			}
		
		}
		catch(Exception finalException) {
			System.out.println("Sorry, that wasn't a valid input. Please try again. " + finalException.getMessage());
			user(conn,userID);
		}
		
	}

	public static void userPropertyRecommendations(Connection conn, int userID) throws SQLException {
		try {
		System.out.println("Here are our properties and their average review score.");
		
		
		Statement propertyStatement = null;
		ResultSet propertyResultSet = null;
		propertyStatement=conn.createStatement();
		String propertyQuery ="SELECT p.propertyName,r.propertyID, r.score, " + 
				"CAST(ROUND(AVG(score),2) AS DEC(10,2)) avg_score " + 
				"FROM properties p " + 
				"JOIN reviews r ON r.propertyid = p.propertyid " + 
				"GROUP BY p.propertyname " + 
				"ORDER BY r.propertyid;";

		propertyResultSet = propertyStatement.executeQuery(propertyQuery);
		if(propertyResultSet.next()) {
			System.out.printf("%-15s%-40s%-19s\n","Property ID", "Property Name", "Average Score");
			do {
				int resultPropertyID = propertyResultSet.getInt("r.propertyID");
				String resultPropertyName = propertyResultSet.getString("p.propertyName");
				double averageScore = propertyResultSet.getDouble("avg_score");

				System.out.print("\n");
				System.out.printf("%-15s%-40s%-19s\n",resultPropertyID, resultPropertyName, averageScore);

			}
			while(propertyResultSet.next());
		}
		
		}
		catch (Exception err) {
			System.out.println("Sorry, that wasn't a valid input." + " " +err.getMessage());
			user(conn, userID);
		}

	}

	public static void userRequestLease(Connection conn, int userID) throws SQLException {
		try {
			
			Scanner input = new Scanner(System.in);
			System.out.println("Executed userRequestLease");
			System.out.println("What kind of lease would you like to have?");
			System.out.println("********************************************");
			System.out.println("Please choose one of the following options:");
			System.out.println("1. \tPersonal");
			System.out.println("2. \tCosigner");
			System.out.println("3. \tRoomates");
			System.out.println("********************************************");
			int leaseChoice = input.nextInt();
			String leaseType=null;
			if(leaseChoice==1) {
				leaseType="Personal";
			}
			if(leaseChoice==2) {
				leaseType="Cosigner";
			}
			if(leaseChoice==3) {
				leaseType="Roommates";
			}
			
			System.out.println("How many bedrooms would you like (1-3)?");
			int numBedrooms = input.nextInt();
			System.out.println("How many bathrooms would you like (1-3)?");
			int numBathrooms = input.nextInt();

				userPropertyRecommendations(conn,userID);
				System.out.println("Which property would you like to call your new home? (enter the number associated to the desired option.)");
				int propertyChoice = input.nextInt();
				
				
				Statement propertyUnitStatement = null;
				ResultSet propertyUnitResultSet = null;
				propertyUnitStatement=conn.createStatement();
				String propertyUnitQuery ="SELECT * from property_units pu "
						+ "join properties p on p.propertyID=pu.propertyID " + 
						"where pu.propertyID='"+propertyChoice+"'";

				propertyUnitResultSet = propertyUnitStatement.executeQuery(propertyUnitQuery);
				if(propertyUnitResultSet.next()) {
					int counter = 0;
					do {
						int propertyUnitID = propertyUnitResultSet.getInt("pu.propertyUnitID");
						int resultNumberOfBedrooms = propertyUnitResultSet.getInt("pu.numberOfBedrooms");
						int resultNumberOfBathrooms = propertyUnitResultSet.getInt("pu.numberOfBathrooms");
						int resultUnitNumber = propertyUnitResultSet.getInt("pu.unitNumber");


						System.out.print("\n");
						if(numBedrooms==resultNumberOfBedrooms && numBathrooms==resultNumberOfBathrooms) {
							System.out.println("**************************************************************************************************");
							System.out.println("\tProperty Unit\tNumber of Bedrooms\tNumber of Bathrooms\tUnit Number");
							System.out.println("\t" + propertyUnitID + "\t\t" + resultNumberOfBedrooms + "\t\t\t" + resultNumberOfBathrooms + "\t\t\t" + resultUnitNumber);
							System.out.println("**************************************************************************************************");
						counter++;
						}
						
					}
					while(propertyUnitResultSet.next());
					if (counter == 0) {
						System.out.println("Sorry, there are no units available at this property with your criteria.\nPlease try requesting a lease again with different criteria.");
						user(conn,userID);
					}else {
						System.out.println("Please choose the property unit that interests you most.");
					}
				}
				else {
					System.out.println("Sorry, no available units were found under that criteria.");
					user(conn,userID);
				}
				int unitSelection = input.nextInt();
				Statement leaseCountStatement = null;
				ResultSet leaseCountResultSet = null;
				leaseCountStatement=conn.createStatement();
				String leaseCountQuery ="select * from leases; ";
				leaseCountResultSet = leaseCountStatement.executeQuery(leaseCountQuery);
				Statement leaseCreateStatement = null;
				ResultSet leaseCreateResultSet = null;
				leaseCreateStatement=conn.createStatement();
				String leaseCreateQuery ="insert into leases (propertyUnitID, leaseType) values('"+unitSelection+"','"+leaseType+"'); ";
				leaseCreateStatement.executeUpdate(leaseCreateQuery);
				
			
			System.out.println("Congratulations! A lease has been generated. One of our agents will reach out to you once the lease has been reviewed.");
			}
			catch (Exception err) {
				System.out.println("Sorry, that wasn't a valid input."+ " " +err.getMessage());
				user(conn, userID);
			}
}	
	
	public static void userDues(Connection conn, int userID) throws SQLException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Executed userDues");
			
			Statement billStatement = null;
			ResultSet billResultSet = null;
			billStatement=conn.createStatement();
			String billQuery ="SELECT * from bills b "
					+ "join contracts c on c.contractID=b.contractID "  
					+ "join leases l on l.leaseID=c.leaseID "
					+ "join lease_history lh on lh.leaseID=l.leaseID "

					+ "where lh.userID='"+userID+"' and b.paymentDate IS NULL";
					

			billResultSet = billStatement.executeQuery(billQuery);
			if(billResultSet.next()) {
				System.out.println("billID    cost    bill date    late rent penalty    early termination fee");
				System.out.print("\n");

				do {
					int resultBillID = billResultSet.getInt("b.billID");
					int resultCost = billResultSet.getInt("b.cost");
					Date resultBillDate = billResultSet.getDate("b.billDate");
					int resultLateRentPenalty = billResultSet.getInt("c.lateRentPenalty");
					int resultEarlyTerminationFee = billResultSet.getInt("c.earlyTerminationFee");
					System.out.println(resultBillID + "        " + resultCost + "    " + resultBillDate + "      " + resultLateRentPenalty + "                 " + resultEarlyTerminationFee);	
				}
				while(billResultSet.next());
			
			}
			System.out.println("Choose the billID you wish to pay.");
			int billPay = input.nextInt();
			
			Statement billPayStatement = null;
			ResultSet billPayResultSet = null;
			billPayStatement=conn.createStatement();
			String billPayQuery ="select * from bills bb where billID ='"+billPay+"'";
			
			billPayResultSet = billPayStatement.executeQuery(billPayQuery);
			
			while(billPayResultSet.next()) {	
			
		
		Date today = new Date();
		Date billDueDate= billPayResultSet.getDate("bb.billDate");
		String datePattern="yyyy-MM-dd";
		SimpleDateFormat sfd = new SimpleDateFormat(datePattern);
		String mySQLToday = sfd.format(today);
		String mySQLBillDueDate = sfd.format(billDueDate);
		LocalDate end = LocalDate.parse(mySQLToday);
		LocalDate start = LocalDate.parse(mySQLBillDueDate);
			
		
				
				if(ChronoUnit.DAYS.between(start,end)>5) {
					System.out.println("Your payment is more than five days late.");
					Statement billPayLateStatement = null;
					int billPayLateResultSet;
					billPayLateStatement=conn.createStatement();
					String billPayLateQuery ="update bills set paymentDate='"+today+"'"
					+ " join contracts c on c.contractID=bills.contractID"
							+ " where bills.billID='"+billPay+"';";
					
					billPayLateResultSet = billPayLateStatement.executeUpdate(billPayLateQuery);
					
					Statement billPayLateStatement2 = null;
					int billPayLateResultSet2;
					billPayLateStatement2=conn.createStatement();
					String billPayLateQuery2 ="update bills set isPaidOnTime=0 "
					+ " join contracts c on c.contractID=bills.contractID"
							+ " where bills.billID='"+billPay+"';";
					
					billPayLateResultSet2 = billPayLateStatement2.executeUpdate(billPayLateQuery2);
				}
				else {
					System.out.println("Thank you for paying your bill on time!");
					Statement billPayOnTimeStatement = null;
					int billPayOnTimeResultSet;
					billPayOnTimeStatement=conn.createStatement();
					String billPayOnTimeQuery ="update bills "  
							+ "join contracts c on c.contractID=bills.contractID "
							+ "set paymentDate='"+mySQLToday+"'" //and paidOnTime=1 
							+ "where bills.billID='"+billPay+"'";
					
					billPayOnTimeResultSet = billPayOnTimeStatement.executeUpdate(billPayOnTimeQuery);
					
					Statement billPayOnTimeStatement2 = null;
					int billPayOnTimeResultSet2;
					billPayOnTimeStatement2=conn.createStatement();
					String billPayOnTimeQuery2 ="update bills "  
							+ "join contracts c on c.contractID=bills.contractID "
							+ "set paidOnTime=1 " 
							+ "where bills.billID='"+billPay+"'";
					
					billPayOnTimeResultSet2 = billPayOnTimeStatement2.executeUpdate(billPayOnTimeQuery2);
				}
					
		
			}
			
				
		}
			catch (Exception err) {
				System.out.println("Sorry, that wasn't a valid input."+ " " +err.getMessage());
				user(conn, userID);
			}

	}
	public static void userRecommendations(Connection conn, int userID) throws SQLException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Executed userRecommendations");
			Statement propertyStatement = null;
			ResultSet propertyResultSet = null;
			propertyStatement=conn.createStatement();
			String propertyQuery ="SELECT p.propertyName,r.propertyID, r.score, " + 
					"CAST(ROUND(AVG(score),2) AS DEC(10,2)) avg_score " + 
					"FROM reviews r " + 
					"INNER JOIN properties p ON r.propertyid = p.propertyid " + 
					"GROUP BY p.propertyname " + 
					"ORDER BY r.propertyid;";

			propertyResultSet = propertyStatement.executeQuery(propertyQuery);
			while(propertyResultSet.next()) {
				
					int resultPropertyID = propertyResultSet.getInt("r.propertyID");
					String resultPropertyName = propertyResultSet.getString("p.propertyName");
					double averageScore = propertyResultSet.getDouble("avg_score");

					System.out.print("\n");
					System.out.println(resultPropertyID + ". " + resultPropertyName );
			}
			System.out.println("Please enter the propertyID you want to leave a review for");
			int propertyID = input.nextInt();
			System.out.println("Please place a numerical score (from 0-5, as an integer) for this community");
			int score = input.nextInt();
				
			Statement reviewStatement = null;
			int reviewResultSet;
			reviewStatement=conn.createStatement();
			String reviewQuery ="insert into reviews values('"+userID+"','"+propertyID+"','"+score+"');";
			
			reviewResultSet = reviewStatement.executeUpdate(reviewQuery);
				
		}
	
			catch (Exception err) {
				System.out.println("Sorry, that wasn't a valid input."+ " " +err.getMessage());
				user(conn, userID);
			}

	}
	public static void userRequestTerminations(Connection conn, int userID) throws SQLException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Executed userRequestTerminations");
			
			Statement contractsStatement = null;
			ResultSet contractsResultSet = null;
			contractsStatement=conn.createStatement();
			String contractsQuery ="SELECT * from contracts c "
					+ "join leases l on l.leaseID=c.leaseID " 
					+ "join lease_history lh on lh.leaseID=l.leaseID " + 

					"where lh.userID='"+userID+"'";
			int counter=0;
			contractsResultSet = contractsStatement.executeQuery(contractsQuery);
			if(contractsResultSet.next()) {
				do {
					
					Date today = new Date();
					System.out.print(today);
					int contractID = contractsResultSet.getInt("c.contractID");
					Date contractStartDate = contractsResultSet.getDate("c.startDate");
					Date contractEndDate = contractsResultSet.getDate("c.endDate");
					int contractMonthlyCost = contractsResultSet.getInt("c.monthlyCost");
					int contractEarlyTerminationFee = contractsResultSet.getInt("c.earlyTerminationFee");

					//Issue with date format
					String datePattern="yyyy-MM-dd";
					SimpleDateFormat sfd = new SimpleDateFormat(datePattern);
					String mySQLToday = sfd.format(new Date());
					System.out.print(mySQLToday);
					String mySQLStartDate = sfd.format(contractStartDate);
					String mySQLEndDate = sfd.format(contractEndDate);
						
					LocalDate todayLocal = LocalDate.parse(mySQLToday);
					LocalDate contractStartDateLocal = LocalDate.parse(mySQLStartDate);
					LocalDate contractEndDateLocal = LocalDate.parse(mySQLEndDate);

					
					if(todayLocal.isAfter(contractStartDateLocal) && todayLocal.isBefore(contractEndDateLocal)) {
						System.out.println("Here is your outstanding contract: ");
						System.out.println(contractID + " " + contractStartDate + " " + contractEndDate + " "
								+ contractMonthlyCost + " " + contractEarlyTerminationFee);
						counter++;
						}
				}
					while(contractsResultSet.next());
				
					if(counter!=0) {
						System.out.println("Please enter the contractID you'd like to terminate.");
						int termContract = input.nextInt();
						Statement terminateStatement = null;
						int terminateResultSet;
						terminateStatement=conn.createStatement();
						String terminateQuery ="update contracts set terminationRequested=1 "
								+ "where contracts.contractID='"+termContract+"'";
						
						terminateResultSet = terminateStatement.executeUpdate(terminateQuery);
						System.out.println("");
						System.out.println("Termination has been requested.");
					}
						
					
					if (counter==0) {
						System.out.println("It appears you don't have any contracts that can be terminated.");
						}
			}
		}	
			
		
			catch (Exception err) {
				System.out.println("Sorry, that wasn't a valid input."+ " " +err.getMessage());
				user(conn, userID);
			}
		user(conn,userID);

	}
	
	public static void adminCreateLeaseContract(Connection conn, int userID) throws SQLException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Executed adminCreateLease");
			System.out.println("Here are the leases with no corresponding contracts:");
			
			Statement outStandingLeasesStatement = null;
			ResultSet outStandingLeasesResultSet = null;
			outStandingLeasesStatement=conn.createStatement();
			String outStandingLeasesQuery ="SELECT " + 
					"l.leaseID, l.propertyUnitID, l.leaseType, lh.userID " + 
					"from leases l " + 
					"left outer join lease_history lh on lh.leaseID=l.leaseID " + 
					"where not exists ( " + 
					"	select *" + 
					"    from contracts c " + 
					"    where c.leaseID=l.leaseID )";

			outStandingLeasesResultSet = outStandingLeasesStatement.executeQuery(outStandingLeasesQuery);
			if(outStandingLeasesResultSet.next()) {
				System.out.println("leaseID   propertyUnitID   leaseType");
				System.out.print("\n");
				System.out.println("********************************************");
				do {
					int unusedLeaseID = outStandingLeasesResultSet.getInt("l.leaseID");
					int unusedLeasePropertyUnitID = outStandingLeasesResultSet.getInt("l.propertyUnitID");
					int unusedLeaseUserID = outStandingLeasesResultSet.getInt("lh.userID");
					String unusedLeaseLeaseType = outStandingLeasesResultSet.getString("l.leaseType");
					System.out.println(unusedLeaseID + ".         " + unusedLeasePropertyUnitID + "               " + unusedLeaseLeaseType);
					
				}
				while(outStandingLeasesResultSet.next());
				
				 
				System.out.println("Please select the lease ID you wish to create a contract for. Otherwise, press 0 to exit.");
				int userChoice = input.nextInt();
				System.out.println("Please select the duration this contract should have.");
				int duration = input.nextInt();
				System.out.println("Please enter the monthly cost associated this contract.");
				int monthlyCost = input.nextInt();
				System.out.println("Please enter the late rent penalty associated to this contract.");
				int lateRentPenalty = input.nextInt();
				System.out.println("Please enter the early termination associated to this contract");
				int earlyTerminationFee = input.nextInt();
				input.nextLine();
				System.out.println("Please enter the start date associated to this contract (enter in YYYY-MM-DD format)");
				
				String startDate = input.nextLine();
				
				 
				
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date myDate = formatter.parse(startDate);
				java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
				
				
				System.out.println("Please enter the end date associated to this contract (enter in YYYY-MM-DD format)");
				String endDate = input.nextLine();
				
				
				Date myDate1 = formatter.parse(endDate);
				java.sql.Date sqlDate1 = new java.sql.Date(myDate1.getTime());
				
				
				ArrayList<Integer> numberOfTenantsPerLease = new ArrayList<Integer>();
				boolean keepGoing=true;
				int counter2=0;
				
				
				while(keepGoing==true){
					System.out.println("Please enter a userID associated to this contract, otherwise press 0 to exit.");
					int userChoice2=input.nextInt();
					
					if(userChoice2!=0) {
					numberOfTenantsPerLease.add(userChoice2);
					counter2++;
					}
					if(userChoice2 == 0) {
						keepGoing=false;
					}
					
				}
				
				Statement contractCountStatement = null;
				ResultSet contractCountResultSet = null;
				contractCountStatement=conn.createStatement();
				String contractCountQuery ="select * from contracts; ";
				contractCountResultSet = contractCountStatement.executeQuery(contractCountQuery);

				int counter=1;
				if(contractCountResultSet.next()) {
					do {
						counter++;
					}
					while(contractCountResultSet.next());
				}
				
				

				if(userChoice!=0) {
					Statement createContractStatement = null;
					int createContractResultSet;
					int termRequested = 0;
					int isTerminated = 0;
					createContractStatement=conn.createStatement();
					String createContractQuery ="insert into contracts values"
							+ "('"+counter+"','"+userChoice+"','"+duration+"','"+monthlyCost+"','"+lateRentPenalty+"',"
							+ "'"+earlyTerminationFee+"','"+sqlDate+"', '"+sqlDate1+"', '"+termRequested+"', '"+isTerminated+"');";

					createContractResultSet = createContractStatement.executeUpdate(createContractQuery);
					
					for (int i=0;!numberOfTenantsPerLease.isEmpty();i++) {
						Statement updateLeaseHistoryStatement = null;
						updateLeaseHistoryStatement=conn.createStatement();
						String updateLeaseHistoryQuery ="insert into lease_history values "
								+ "('"+numberOfTenantsPerLease.get(i)+"','"+counter+"'";

						updateLeaseHistoryStatement.executeUpdate(updateLeaseHistoryQuery);
						i++;
					}
				}
				else
					System.out.println("Okay, we will direct you back to the main page.");
			
			}
			else
				System.out.println("There are no outstanding leases.");
			
			}
			catch (Exception err) {
				System.out.println("Sorry, that wasn't a valid input."+ " " +err.getMessage());
				admin(conn, userID);
			}
	}
		
	public static void adminAddLateFeesAndTerminations(Connection conn, int userID) throws SQLException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Executed adminFeesAndTerminations");
			System.out.println("Would you like to assess late fees or terminations?");
			System.out.print("\n");
			System.out.println("********************************************");
			System.out.println("\tPress 1 for late fees.");
			System.out.println("\tPress 2 for terminations.");
			System.out.println("\tPress 3 to go back.");
			System.out.println("********************************************");
			int userChoice=input.nextInt();
			if (userChoice==1) {
					
					ArrayList<Integer> billIDCheckList = new ArrayList<Integer>();
					int counter1 = 0;
					
					String safetyrelease = "SET SQL_SAFE_UPDATES = 0;";
					PreparedStatement prepare = conn.prepareStatement(safetyrelease);
					prepare.execute();
					
					
					
					String lateBillUpdate = "UPDATE bills b " + 
							"SET paidOnTime = 0 " + 
							"WHERE b.paymentDate IS NULL " + 
							"AND curdate() > date_add(b.billDate, INTERVAL 6 DAY);";
					prepare = conn.prepareStatement(lateBillUpdate);
					prepare.execute();
					
					
					Statement lateFeesStatement = null;
					ResultSet lateFeesResultSet = null;
					lateFeesStatement=conn.createStatement();
					String lateFeesQuery ="SELECT * " + 
							"from bills b " + 
							"join contracts c on c.contractID=b.contractID " + 
							"join leases l on l.leaseID=c.leaseID " + 
							"join lease_history lh on lh.leaseID=l.leaseID " + 

							"where b.paidOnTime=0";

					lateFeesResultSet = lateFeesStatement.executeQuery(lateFeesQuery);
					
					
					if(lateFeesResultSet.next()) {
						do {
							int lateBillID = lateFeesResultSet.getInt("b.billID");
							billIDCheckList.add(lateBillID);
							
							if(counter1 == 0) {
								
								int correspondingContractID = lateFeesResultSet.getInt("b.contractID");
								int billCost = lateFeesResultSet.getInt("b.cost");
								int lateUserID = lateFeesResultSet.getInt("lh.userID");
								int originalBill = lateFeesResultSet.getInt("c.monthlyCost");
								Date startDate = lateFeesResultSet.getDate("c.startDate");
								Date endDate = lateFeesResultSet.getDate("c.endDate");
								int lateRentPenalty = lateFeesResultSet.getInt("c.lateRentPenalty");
								System.out.println("*********************************************************************************************************************************************************************************************************");
								System.out.println("\tBill ID\t\tContract ID\t\tBill Due\t\tOriginal Bill Amount\t\tUser ID\t\tContract Start Date\t\tContract End Date\t\tLate Fee");
								System.out.println("\t" + lateBillID + "\t\t" + correspondingContractID + "\t\t\t$" 
								+ billCost + "\t\t\t$" + originalBill + "\t\t\t\t" + lateUserID + "\t\t" + startDate + "\t\t\t" + endDate + "\t\t\t$" + lateRentPenalty);
								System.out.println("*********************************************************************************************************************************************************************************************************");
								
							}else if (counter1 > 0) {
								if(billIDCheckList.get(counter1) != billIDCheckList.get(counter1 - 1)) {
									int correspondingContractID = lateFeesResultSet.getInt("b.contractID");
									int billCost = lateFeesResultSet.getInt("b.cost");
									int lateUserID = lateFeesResultSet.getInt("lh.userID");
									int originalBill = lateFeesResultSet.getInt("c.monthlyCost");
									Date startDate = lateFeesResultSet.getDate("c.startDate");
									Date endDate = lateFeesResultSet.getDate("c.endDate");
									int lateRentPenalty = lateFeesResultSet.getInt("c.lateRentPenalty");
									System.out.println("*********************************************************************************************************************************************************************************************************");
									System.out.println("\tBill ID\t\tContract ID\t\tBill Due\t\tOriginal Bill Amount\t\tUser ID\t\tContract Start Date\t\tContract End Date\t\tLate Fee");
									System.out.println("\t" + lateBillID + "\t\t" + correspondingContractID + "\t\t\t$" 
									+ billCost + "\t\t\t$" + originalBill + "\t\t\t\t" + lateUserID + "\t\t" + startDate + "\t\t\t" + endDate + "\t\t\t$" + lateRentPenalty);
									System.out.println("*********************************************************************************************************************************************************************************************************");
								}
							}
							counter1++;
						}
						while(lateFeesResultSet.next());
						
						
					System.out.println("Please enter the billID you'd like to add a late fee for, or press 0 to go back.");
					int userChoice2 = input.nextInt();
					if(userChoice2!=0) {
						int counter=0;
						Statement lateFeesCommitStatement = null;
						ResultSet lateFeesCommitResultSet = null;
						lateFeesCommitStatement=conn.createStatement();
						String lateFeesCommitQuery ="update bills b "
								+ "join contracts c on c.contractID =b.contractID "
								+ "set b.cost=b.cost+c.lateRentPenalty "
								+ "where b.billID=" + userChoice2;

						lateFeesCommitStatement.executeUpdate(lateFeesCommitQuery);
						
					}
					else {
						System.out.println("Okay, we'll go back.");
						admin(conn,userID);
					}
				}
				}
					
			if (userChoice==2) {
				
				ArrayList<Integer> contractIDCheck = new ArrayList<Integer>();
				int counter1 = 0;
				
				Statement terminateContractStatement = null;
				ResultSet terminateContractResultSet = null;
				terminateContractStatement=conn.createStatement();
				String terminateContractQuery ="SELECT *" + 
						"from contracts c " + 
						"join bills b on b.contractID=c.ContractID " + 
						"join leases l on l.leaseID=c.leaseID " + 
						"join lease_history lh on lh.leaseID=l.leaseID " + 
						"where c.terminationRequested=1 and c.isTerminated IS NULL";

				terminateContractResultSet = terminateContractStatement.executeQuery(terminateContractQuery);
				if(terminateContractResultSet.next()) {
					do {
						int terminateContractID = terminateContractResultSet.getInt("c.contractID");
						contractIDCheck.add(terminateContractID);
						
						if(counter1 == 0) {
						int terminateContractUserID = terminateContractResultSet.getInt("lh.userID");
						int terminateContractEarlyTerminationFee = terminateContractResultSet.getInt("c.earlyTerminationFee");
						int billID = terminateContractResultSet.getInt("b.billID");
						Date startDate = terminateContractResultSet.getDate("c.startDate");
						Date endDate = terminateContractResultSet.getDate("c.endDate");

						System.out.print("\n");
						System.out.println("******************************************************************************************************************************************");
						System.out.println("\tContract ID\tUser ID\t\tBill ID\t\tTermination Fee\t\tContract Start Date\t\tContract End Date");
						System.out.println("\t" + terminateContractID + "\t\t" + terminateContractUserID + "\t\t" + billID + "\t\t$" 
						+ terminateContractEarlyTerminationFee + "\t\t\t" + startDate + "\t\t\t" + endDate);
						System.out.println("******************************************************************************************************************************************");
						} else if(counter1 > 0) {
							if(terminateContractID != contractIDCheck.get(counter1 - 1)) {
								int terminateContractUserID = terminateContractResultSet.getInt("lh.userID");
								int terminateContractEarlyTerminationFee = terminateContractResultSet.getInt("c.earlyTerminationFee");
								int billID = terminateContractResultSet.getInt("b.billID");
								Date startDate = terminateContractResultSet.getDate("c.startDate");
								Date endDate = terminateContractResultSet.getDate("c.endDate");

								System.out.print("\n");
								System.out.println("******************************************************************************************************************************************");
								System.out.println("\tContract ID\tUser ID\t\tBill ID\t\tTermination Fee\t\tContract Start Date\t\tContract End Date");
								System.out.println("\t" + terminateContractID + "\t\t" + terminateContractUserID + "\t\t" + billID + "\t\t$" 
								+ terminateContractEarlyTerminationFee + "\t\t\t" + startDate + "\t\t\t" + endDate);
								System.out.println("******************************************************************************************************************************************");
							}
						}
						counter1++;
					}
					while(terminateContractResultSet.next());
					System.out.println("Please enter the contractID you'd like to terminate, or press 0 to go back.");
					int userChoice2 = input.nextInt();
					System.out.println("Please enter the billID for which the termination fee should be applied, or press 0 to go back.");
					int userChoice3 = input.nextInt();
					if(userChoice2!=0) {
						int counter=0;
						Statement terminateContractCommitStatement = null;
						ResultSet terminateContractCommitResultSet = null;
						terminateContractCommitStatement=conn.createStatement();
						String terminateContractCommitQuery ="UPDATE bills b "
								+ "join contracts c on c.contractID = b.contractID SET b.cost =c.earlyTerminationFee WHERE b.billID =" + userChoice3;
								
								
						terminateContractCommitStatement.executeUpdate(terminateContractCommitQuery);
						int num = 1;
						String terminateContractCommitQuery2 ="UPDATE contracts c SET c.isTerminated =" + num + " WHERE contractID =" + userChoice2;
								
						terminateContractCommitStatement.executeUpdate(terminateContractCommitQuery2);
						
					}
					else {
						System.out.println("Okay, we'll go back.");
						admin(conn,userID);
					}
				}
				}
			
			if (userChoice==3) {
				admin(conn,userID);
			
			}
			else {
				//System.out.println("Sorry, that selection didn't correspond to any available option. Please try again.");
				adminAddLateFeesAndTerminations(conn,userID);
			}
			}
			catch (Exception err) {
				System.out.println("Sorry, that wasn't a valid input."+ " " +err.getMessage());
				admin(conn, userID);
			}

	}

	

	public static void adminUpdateInventory(Connection conn, int userID) throws SQLException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Executed adminUpdateInventory");
			System.out.println("Would you like to add, update, or delete inventory?");
			System.out.print("\n");
			System.out.println("********************************************");
			System.out.println("\tPress 1 to add inventory.");
			System.out.println("\tPress 2 to update inventory.");
			System.out.println("\tPress 3 to delete inventory.");
			System.out.println("********************************************");
			int userChoice=input.nextInt();
			if (userChoice==1) {
				Statement getPropertyStatement = null;
				ResultSet getPropertyResultSet = null;
				getPropertyStatement=conn.createStatement();
				String getPropertyQuery ="SELECT * from properties p";

				getPropertyResultSet = getPropertyStatement.executeQuery(getPropertyQuery);
				if(getPropertyResultSet.next()) {
					do {
						int propertyID = getPropertyResultSet.getInt("p.propertyID");
						String propertyName = getPropertyResultSet.getString("p.propertyName");
						String address = getPropertyResultSet.getString("p.address");
						String city = getPropertyResultSet.getString("p.city");
						String state = getPropertyResultSet.getString("p.state");
						int zip = getPropertyResultSet.getInt("p.zip");
						


						System.out.print("\n");
						System.out.println(propertyID + ". " + propertyName + " " 
						+ address + " " + city + " " + state + " " + zip);
						
					}
					while(getPropertyResultSet.next());
					System.out.println("Please enter the propertyID for which you'd like to add a unit, or press 0 to go back.");
					int userChoice2 = input.nextInt();
					if(userChoice2!=0) {
						int counter=2;         //This is very important!!!! Since primary keys cannot be repeated, even after deletion, we have to account for deleted PK's. Right now
						//We can start with 13 and that is why the counter is set at 2. It will count all the 11 rows already made and add the 2 to make the new PK = 13.
						Statement propertyUnitCountStatement = null;
						ResultSet propertyUnitCountResultSet = null;
						propertyUnitCountStatement=conn.createStatement();
						String propertyUnitCountQuery ="select * "
								+ "from property_units pu ";

						propertyUnitCountResultSet = propertyUnitCountStatement.executeQuery(propertyUnitCountQuery);
						if(propertyUnitCountResultSet.next()) {
							do {
								counter++;
							}
							
							while(propertyUnitCountResultSet.next());
							}
						
						else {
							System.out.println("Sorry, that didn't match a propertyID");
							admin(conn,userID);
						}
						
						System.out.println("What is the unit number?");
						int unitNumberNew=input.nextInt();
						System.out.println("How many bedrooms?");
						int numberOfBedroomsNew=input.nextInt();
						System.out.println("How many bathrooms?");
						int numberOfBathroomsNew=input.nextInt();
						
						Statement propertyUnitAddStatement = null;
						ResultSet propertyUnitAddResultSet = null;
						propertyUnitAddStatement=conn.createStatement();
						String propertyUnitAddQuery ="insert into property_units (propertyID, numberOfBedrooms, numberOfBathrooms, unitNumber, isAvailable) "
								+ "values ('"+userChoice2+"','"+numberOfBedroomsNew+"','"+numberOfBathroomsNew+"','"+unitNumberNew+"',1);";

						propertyUnitAddStatement.executeUpdate(propertyUnitAddQuery);
					}
						
					else {
						System.out.println("Okay, we'll go back.");
						admin(conn,userID);
					}
				}
				else {
					System.out.println("Sorry, there are currently no properties.");
				}
				
			}
			
			if (userChoice==2) {
				Statement getPropertyStatement = null;
				ResultSet getPropertyResultSet = null;
				getPropertyStatement=conn.createStatement();
				String getPropertyQuery ="SELECT * from properties p";

				getPropertyResultSet = getPropertyStatement.executeQuery(getPropertyQuery);
				if(getPropertyResultSet.next()) {
					do {
						int propertyID = getPropertyResultSet.getInt("p.propertyID");
						String propertyName = getPropertyResultSet.getString("p.propertyName");
						String address = getPropertyResultSet.getString("p.address");
						String city = getPropertyResultSet.getString("p.city");
						String state = getPropertyResultSet.getString("p.state");
						int zip = getPropertyResultSet.getInt("p.zip");
						


						System.out.print("\n");
						System.out.println(propertyID + ". " + propertyName + " " 
						+ address + " " + city + " " + state + " " + zip);
						
					}
					while(getPropertyResultSet.next());
					System.out.println("Please enter the propertyID that contains the unit you'd like to update, or press 0 to go back.");
					int userChoice2 = input.nextInt();
					if(userChoice2!=0) {
						System.out.println("Please choose the propertyUnitID for the unit you'd like to edit.");
						Statement propertyUnitViewStatement = null;
						ResultSet  propertyUnitViewResultSet = null;
						 propertyUnitViewStatement=conn.createStatement();
						String propertyUnitViewQuery ="select * "
								+ "from property_units pu "
								+ "where pu.propertyID='"+userChoice2+"'";

						propertyUnitViewResultSet = propertyUnitViewStatement.executeQuery(propertyUnitViewQuery);
						if(propertyUnitViewResultSet.next()) {
							do {
								int propertyUnitID = propertyUnitViewResultSet.getInt("pu.propertyUnitID");
								int numberOfBedrooms = propertyUnitViewResultSet.getInt("pu.numberOfBedrooms");
								int numberOfBathrooms = propertyUnitViewResultSet.getInt("pu.numberOfBathrooms");
								int unitNumber = propertyUnitViewResultSet.getInt("pu.unitNumber");
								int isAvailable = propertyUnitViewResultSet.getInt("pu.isAvailable");
								System.out.print("\n");
								System.out.println("*****************************************************************************************************************************");
								System.out.println("\tUnit ID\t\tNumber of Bedrooms\tNumber of Bathrooms\tUnit #\t\tAvailability(0 - No, 1 - Yes)");
								System.out.println("\t" + propertyUnitID + "\t\t" + numberOfBedrooms + "\t\t\t" 
								+ numberOfBathrooms + "\t\t\t" + unitNumber + "\t\t" + isAvailable );
								System.out.println("*****************************************************************************************************************************");
							}
							while(propertyUnitViewResultSet.next());
							
							}
						
						else {
							System.out.println("Sorry, that didn't match a propertyID");
							admin(conn,userID);
						}
						int unitSelection = input.nextInt();
						System.out.println("How many bedrooms are there?");
						int numBedrooms=input.nextInt();
						System.out.println("How many bathrooms are there?");
						int numBathrooms=input.nextInt();
						System.out.println("What is the unit number?");
						int unitNumber=input.nextInt();
						System.out.println("Enter 0 if this unit is no longer available, 1 if it is now available.");
						int isAvailable=input.nextInt();
						
						Statement propertyUnitEditStatement = null;
						ResultSet propertyUnitEditResultSet = null;
						propertyUnitEditStatement=conn.createStatement();
						String propertyUnitEditQuery ="update property_units "
								+ "set propertyUnitID = '"+unitSelection+"',propertyID='"+userChoice2+"',"
								+ "numberOfBedrooms='"+numBedrooms+"',numberOfBathrooms='"+numBathrooms+"',"
								+ "unitNumber='"+unitNumber+"',isAvailable='"+isAvailable+"'"
								+ "where propertyUnitID='"+unitSelection+"'";

						propertyUnitEditStatement.executeUpdate(propertyUnitEditQuery);
						if(!propertyUnitEditResultSet.next()) {
							System.out.println("Sorry, there was an error when entering in the data. Please try again.");
						}
					}
						
					else {
						System.out.println("Okay, we'll go back.");
						admin(conn,userID);
					}
				}
				else {
					System.out.println("Sorry, there are currently no properties.");
				}
				
			}
			if (userChoice==3) {
				Statement getPropertyStatement = null;
				ResultSet getPropertyResultSet = null;
				getPropertyStatement=conn.createStatement();
				String getPropertyQuery ="SELECT * from properties p";

				getPropertyResultSet = getPropertyStatement.executeQuery(getPropertyQuery);
				if(getPropertyResultSet.next()) {
					do {
						int propertyID = getPropertyResultSet.getInt("p.propertyID");
						String propertyName = getPropertyResultSet.getString("p.propertyName");
						String address = getPropertyResultSet.getString("p.address");
						String city = getPropertyResultSet.getString("p.city");
						String state = getPropertyResultSet.getString("p.state");
						int zip = getPropertyResultSet.getInt("p.zip");
						


						System.out.print("\n");
						System.out.println(propertyID + ". " + propertyName + " " 
						+ address + " " + city + " " + state + " " + zip);
						
					}
					while(getPropertyResultSet.next());
					System.out.println("\nPlease enter the propertyID that contains the unit you'd like to delete, or press 0 to go back.");
					int userChoice2 = input.nextInt();
					if(userChoice2!=0) {
						
						Statement propertyUnitViewStatement = null;
						ResultSet  propertyUnitViewResultSet = null;
						 propertyUnitViewStatement=conn.createStatement();
						String propertyUnitViewQuery ="select * "
								+ "from property_units pu "
								+ "where pu.propertyID=" + userChoice2;

						propertyUnitViewResultSet = propertyUnitViewStatement.executeQuery(propertyUnitViewQuery);
						if(propertyUnitViewResultSet.next()) {
							do {
								int propertyUnitID = propertyUnitViewResultSet.getInt("pu.propertyUnitID");
								int numberOfBedrooms = propertyUnitViewResultSet.getInt("pu.numberOfBedrooms");
								int numberOfBathrooms = propertyUnitViewResultSet.getInt("pu.numberOfBathrooms");
								int unitNumber = propertyUnitViewResultSet.getInt("pu.unitNumber");
								int isAvailable = propertyUnitViewResultSet.getInt("pu.isAvailable");
								System.out.print("\n");
								System.out.println("*****************************************************************************************************************************");
								System.out.println("\tUnit ID\t\tNumber of Bedrooms\tNumber of Bathrooms\tUnit #\t\tAvailability(0 - No, 1 - Yes)");
								System.out.println("\t" + propertyUnitID + "\t\t" + numberOfBedrooms + "\t\t\t" 
								+ numberOfBathrooms + "\t\t\t" + unitNumber + "\t\t" + isAvailable );
								System.out.println("*****************************************************************************************************************************");
							}
							while(propertyUnitViewResultSet.next());
							
							}
						
						else {
							System.out.println("Sorry, that didn't match a propertyID");
							admin(conn,userID);
						}
						System.out.println("Please choose the propertyUnitID for the unit you'd like to delete.");
						int unitSelection = input.nextInt();	
						Statement propertyUnitDeleteStatement = null;
						ResultSet propertyUnitDeleteResultSet = null;
						propertyUnitDeleteStatement=conn.createStatement();
						String propertyUnitDeleteQuery ="delete from property_units "
								+ "where propertyUnitID=" + unitSelection;

						propertyUnitDeleteStatement.executeUpdate(propertyUnitDeleteQuery);
						if(!propertyUnitDeleteResultSet.next()) {
							System.out.println("Sorry, that doesn't match a search result.");
						}
					}
						
					else {
						System.out.println("Okay, we'll go back.");
						admin(conn,userID);
					}
				}
				else {
					System.out.println("Sorry, there are currently no properties.");
				}
			
			}
			if (userChoice==4) {
				admin(conn,userID);

			}
			else {
				
				adminUpdateInventory(conn,userID);
			}
			}
			catch (Exception err) {
				admin(conn, userID);
			}
	}	
	
	public static void adminCheckHistoryAndBalances(Connection conn, int userID) throws SQLException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Executed adminCheckHistoryAndBalances");
			

			Statement getUsersStatement = null;
			ResultSet getUsersResultSet = null;
			getUsersStatement=conn.createStatement();
			//this query searches up users that are still in contract
			String getUsersQuery ="SELECT u.userid, u.username, l.leasetype " + 
					"from users u " + 
					"join lease_history lh on lh.userID = u.userID " + 
					"join leases l on l.leaseID = lh.leaseID " + 
					"join contracts c on c.leaseID = l.leaseID " + 
					"where userTypeID=2 " + 
					"and (curdate() < c.endDate and c.isTerminated is null) order by userid";

			getUsersResultSet = getUsersStatement.executeQuery(getUsersQuery);
			if(getUsersResultSet.next()) {
				do {
					int userResultID = getUsersResultSet.getInt("u.userID");
					String userName = getUsersResultSet.getString("u.userName");
					String leaseType = getUsersResultSet.getString("l.leasetype");

					System.out.print("\n");
					System.out.println("*************************************************");
					System.out.println("\tuserID\tuserName\tleaseType");

					System.out.println("*************************************************");
					System.out.println("\t" + userResultID + "\t" + userName + "\t" + leaseType);
					
				}
				while(getUsersResultSet.next());
			}
			System.out.println("\nPlease enter the userID for the user you would like to analyze.");
			int selectedUser=input.nextInt();
			System.out.println("The user's remaining balance is: ");
			Statement remainingBalanceStatement = null;
			ResultSet remainingBalanceResultSet = null;
			remainingBalanceStatement=conn.createStatement();
			int totalCost=0;
			
			//not exactly sure what this query does but use the query you made to look up remaining bills and use it to complete the method
			String remainingBalanceQuery = "SELECT b.cost "
					+ "from bills b "
					+ "join contracts c on c.contractID = b.contractID "
					+ "join leases l on l.leaseID = c.contractID "
					+ "join lease_history lh on lh.leaseID = l.leaseID "
					+ "where lh.userID=" + selectedUser + " and b.paymentDate is null and (curdate() < c.endDate and c.isTerminated is null);";
			remainingBalanceResultSet = remainingBalanceStatement.executeQuery(remainingBalanceQuery);
			
			int number1 = 0;
			if(remainingBalanceResultSet.next()) {
				
				do {
					
					int number = remainingBalanceResultSet.getInt("cost");
					number1 = number1 + number;
				
				
				}while(remainingBalanceResultSet.next());
			}
			System.out.println("$" + number1);
			
			
			System.out.println("\nPlease choose the desired duration: ");
			System.out.println("1. Over 1 month ");
			System.out.println("2. Over 3 months ");
			System.out.println("3. Over 6 months ");
			System.out.println("4. Over a year  ");
			int userDuration = input.nextInt();

			
			if(userDuration == 1) {
				Statement bills1MonthStatement = null;
				ResultSet bills1MonthResultSet = null;
				bills1MonthStatement=conn.createStatement();
				String bills1MonthQuery ="SELECT b.billID, b.cost, b.billDate, b.paymentDate "
						+ "from bills b "
						+ "join contracts c on c.contractID = b.contractID "
						+ "join leases l on l.leaseID = c.contractID "
						+ "join lease_history lh on lh.leaseID = l.leaseID "
						+ "where lh.userID=" + selectedUser + " and b.paymentDate is null and (curdate() < c.endDate and c.isTerminated is null) "
						+ "and b.billDate < date_add(curdate(), INTERVAL 1 month);";

				bills1MonthResultSet = bills1MonthStatement.executeQuery(bills1MonthQuery);
				if(bills1MonthResultSet.next()) {
					do {
						int billID = bills1MonthResultSet.getInt("b.billID");
						int cost = bills1MonthResultSet.getInt("b.cost");
						Date billDate = bills1MonthResultSet.getDate("b.billDate");
						Date paymentDate = bills1MonthResultSet.getDate("b.paymentDate");


						System.out.print("\n");
						System.out.println("**********************************************************************************************************");
						System.out.println("\tBill ID\t\tCost\t\tDue Date\tDate Paid");
						System.out.println("\t" + billID + "\t\t$" + cost + "\t\t" + billDate + "\t" +paymentDate);
						System.out.println("**********************************************************************************************************");

					}
					while(bills1MonthResultSet.next());
				}
				
			}
			if(userDuration == 2) {
				Statement bills3MonthsStatement = null;
				ResultSet bills3MonthsResultSet = null;
				bills3MonthsStatement=conn.createStatement();
				String bills3MonthsQuery ="SELECT b.billID, b.cost, b.billDate, b.paymentDate "
						+ "from bills b "
						+ "join contracts c on c.contractID = b.contractID "
						+ "join leases l on l.leaseID = c.contractID "
						+ "join lease_history lh on lh.leaseID = l.leaseID "
						+ "where lh.userID=" + selectedUser + " and b.paymentDate is null and (curdate() < c.endDate and c.isTerminated is null) "
						+ "and b.billDate < date_add(curdate(), INTERVAL 3 month);";

				bills3MonthsResultSet = bills3MonthsStatement.executeQuery(bills3MonthsQuery);
				if(bills3MonthsResultSet.next()) {
					do {
						int billID = bills3MonthsResultSet.getInt("b.billID");
						int cost = bills3MonthsResultSet.getInt("b.cost");
						Date billDate = bills3MonthsResultSet.getDate("b.billDate");
						Date paymentDate = bills3MonthsResultSet.getDate("b.paymentDate");


						System.out.print("\n");
						System.out.println("**********************************************************************************************************");
						System.out.println("\tBill ID\t\tCost\t\tDue Date\tDate Paid");
						System.out.println("\t" + billID + "\t\t$" + cost + "\t\t" + billDate + "\t" +paymentDate);
						System.out.println("**********************************************************************************************************");

					}
					while(bills3MonthsResultSet.next());
				}
				
			}
			if(userDuration == 3) {
				Statement bills6MonthsStatement = null;
				ResultSet bills6MonthsResultSet = null;
				bills6MonthsStatement=conn.createStatement();
				String bills6MonthsQuery ="SELECT b.billID, b.cost, b.billDate, b.paymentDate "
						+ "from bills b "
						+ "join contracts c on c.contractID = b.contractID "
						+ "join leases l on l.leaseID = c.contractID "
						+ "join lease_history lh on lh.leaseID = l.leaseID "
						+ "where lh.userID=" + selectedUser + " and b.paymentDate is null and (curdate() < c.endDate and c.isTerminated is null) "
						+ "and b.billDate < date_add(curdate(), INTERVAL 6 month);";

				bills6MonthsResultSet = bills6MonthsStatement.executeQuery(bills6MonthsQuery);
				if(bills6MonthsResultSet.next()) {
					do {
						int billID = bills6MonthsResultSet.getInt("b.billID");
						int cost = bills6MonthsResultSet.getInt("b.cost");
						Date billDate = bills6MonthsResultSet.getDate("b.billDate");
						Date paymentDate = bills6MonthsResultSet.getDate("b.paymentDate");


						System.out.print("\n");
						System.out.println("**********************************************************************************************************");
						System.out.println("\tBill ID\t\tCost\t\tDue Date\tDate Paid");
						System.out.println("\t" + billID + "\t\t$" + cost + "\t\t" + billDate + "\t" +paymentDate);
						System.out.println("**********************************************************************************************************");

					}
					while(bills6MonthsResultSet.next());
				}
			}
			if(userDuration == 4) {
				Statement bills12MonthsStatement = null;
				ResultSet bills12MonthsResultSet = null;
				bills12MonthsStatement=conn.createStatement();
				String bills12MonthsQuery ="SELECT b.billID, b.cost, b.billDate, b.paymentDate "
						+ "from bills b "
						+ "join contracts c on c.contractID = b.contractID "
						+ "join leases l on l.leaseID = c.contractID "
						+ "join lease_history lh on lh.leaseID = l.leaseID "
						+ "where lh.userID=" + selectedUser + " and b.paymentDate is null and (curdate() < c.endDate and c.isTerminated is null) "
						+ "and b.billDate < date_add(curdate(), INTERVAL 12 month);";

				bills12MonthsResultSet = bills12MonthsStatement.executeQuery(bills12MonthsQuery);
				if(bills12MonthsResultSet.next()) {
					do {
						int billID = bills12MonthsResultSet.getInt("b.billID");
						int cost = bills12MonthsResultSet.getInt("b.cost");
						Date billDate = bills12MonthsResultSet.getDate("b.billDate");
						Date paymentDate = bills12MonthsResultSet.getDate("b.paymentDate");


						System.out.print("\n");
						System.out.println("**********************************************************************************************************");
						System.out.println("\tBill ID\t\tCost\t\tDue Date\tDate Paid");
						System.out.println("\t" + billID + "\t\t$" + cost + "\t\t" + billDate + "\t" +paymentDate);
						System.out.println("**********************************************************************************************************");

					}
					while(bills12MonthsResultSet.next());
				}
			}
			
			
			
			
			}
			catch (Exception err) {
				System.out.println("Sorry, that wasn't a valid input."+ " " +err.getMessage());
				admin(conn, userID);
			}

	}
	
	public static void adminUpdateReport(Connection conn, int userID) throws SQLException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Executed adminUpdateReport");
			System.out.println("Please choose the level in which the report should be generated, along with the respective duration:");
			System.out.println("Enter 1 for property/complex for within 1 month.");
			System.out.println("Enter 2 for property/complex for within 3 months.");
			System.out.println("Enter 3 for property/complex for within 6 months.");
			System.out.println("Enter 4 for property/complex for within 12 months.");
			System.out.println("Enter 5 for user payments for within 1 month.");
			System.out.println("Enter 6 for user payments for within 3 months.");
			System.out.println("Enter 7 for user payments for within 6 months.");
			System.out.println("Enter 8 for user payments for within 12 months.");

			int userChoice=input.nextInt();
			switch(userChoice) {
			case 1: {
				
				Statement propertyStatement = null;
				ResultSet propertyResultSet = null;
				propertyStatement=conn.createStatement();
				String propertyQuery = "SELECT SUM(b.cost) AS revenue, pu.propertyID, p.propertyName " + 
						"FROM bills b " + 
						"JOIN contracts c ON b.contractID = c.contractID " + 
						"JOIN leases l ON c.leaseID = l.leaseID " + 
						"JOIN property_units pu ON l.propertyUnitID = pu.propertyUnitID " + 
						"JOIN properties p ON pu.propertyID = p.propertyID " + 
						"WHERE b.paymentDate IS NOT NULL " + 
						"AND (b.paymentDate BETWEEN date_add(CURDATE(), INTERVAL -1 month) AND CURDATE()) " + 
						"GROUP BY p.propertyID " + 
						"ORDER BY propertyID";
						

				propertyResultSet = propertyStatement.executeQuery(propertyQuery);
				if(propertyResultSet.next()) {
					do {
						int resultPropertyUnitID = propertyResultSet.getInt("pu.propertyID");
						String resultPropertyName = propertyResultSet.getString("p.propertyName");
						int averageScore = propertyResultSet.getInt("revenue");

						System.out.print("\n");
						System.out.println("Property ID: " + resultPropertyUnitID + "\nProperty Name: " + resultPropertyName + "\nRevenue: $" + averageScore);
						

					}
					while(propertyResultSet.next());
				}
			
				break;
			}
			case 2: {
				Statement propertyStatement = null;
				ResultSet propertyResultSet = null;
				propertyStatement=conn.createStatement();
				String propertyQuery ="SELECT SUM(b.cost) AS revenue, pu.propertyID, p.propertyName " + 
						"FROM bills b " + 
						"JOIN contracts c ON b.contractID = c.contractID " + 
						"JOIN leases l ON c.leaseID = l.leaseID " + 
						"JOIN property_units pu ON l.propertyUnitID = pu.propertyUnitID " + 
						"JOIN properties p ON pu.propertyID = p.propertyID " + 
						"WHERE b.paymentDate IS NOT NULL " + 
						"AND (b.paymentDate BETWEEN date_add(CURDATE(), INTERVAL -3 month) AND CURDATE()) " + 
						"GROUP BY p.propertyID " + 
						"ORDER BY propertyID";

				propertyResultSet = propertyStatement.executeQuery(propertyQuery);
				if(propertyResultSet.next()) {
					do {
						int resultPropertyUnitID = propertyResultSet.getInt("pu.propertyID");
						String resultPropertyName = propertyResultSet.getString("p.propertyName");
						int averageScore = propertyResultSet.getInt("revenue");

						System.out.print("\n");
						System.out.println("Property ID: " + resultPropertyUnitID + "\nProperty Name: " + resultPropertyName + "\nRevenue: $" + averageScore);

					}
					while(propertyResultSet.next());
				}
				
				break;
			}
			case 3: {
				Statement propertyStatement = null;
				ResultSet propertyResultSet = null;
				propertyStatement=conn.createStatement();
				String propertyQuery ="SELECT SUM(b.cost) AS revenue, pu.propertyID, p.propertyName " + 
						"FROM bills b " + 
						"JOIN contracts c ON b.contractID = c.contractID " + 
						"JOIN leases l ON c.leaseID = l.leaseID " + 
						"JOIN property_units pu ON l.propertyUnitID = pu.propertyUnitID " + 
						"JOIN properties p ON pu.propertyID = p.propertyID " + 
						"WHERE b.paymentDate IS NOT NULL " + 
						"AND (b.paymentDate BETWEEN date_add(CURDATE(), INTERVAL -6 month) AND CURDATE()) " + 
						"GROUP BY p.propertyID " + 
						"ORDER BY propertyID";

				propertyResultSet = propertyStatement.executeQuery(propertyQuery);
				if(propertyResultSet.next()) {
					do {
						int resultPropertyUnitID = propertyResultSet.getInt("pu.propertyID");
						String resultPropertyName = propertyResultSet.getString("p.propertyName");
						int averageScore = propertyResultSet.getInt("revenue");

						System.out.print("\n");
						System.out.println("Property ID: " + resultPropertyUnitID + "\nProperty Name: " + resultPropertyName + "\nRevenue: $" + averageScore);

					}
					while(propertyResultSet.next());
				}
				
				break;
			}
			case 4: {
				Statement propertyStatement = null;
				ResultSet propertyResultSet = null;
				propertyStatement=conn.createStatement();
				String propertyQuery ="SELECT SUM(b.cost) AS revenue, pu.propertyID, p.propertyName " + 
						"FROM bills b " + 
						"JOIN contracts c ON b.contractID = c.contractID " + 
						"JOIN leases l ON c.leaseID = l.leaseID " + 
						"JOIN property_units pu ON l.propertyUnitID = pu.propertyUnitID " + 
						"JOIN properties p ON pu.propertyID = p.propertyID " + 
						"WHERE b.paymentDate IS NOT NULL " + 
						"AND (b.paymentDate BETWEEN date_add(CURDATE(), INTERVAL -12 month) AND CURDATE()) " + 
						"GROUP BY p.propertyID " + 
						"ORDER BY propertyID";

				propertyResultSet = propertyStatement.executeQuery(propertyQuery);
				if(propertyResultSet.next()) {
					do {
						int resultPropertyUnitID = propertyResultSet.getInt("pu.propertyID");
						String resultPropertyName = propertyResultSet.getString("p.propertyName");
						int averageScore = propertyResultSet.getInt("revenue");

						System.out.print("\n");
						System.out.println("Property ID: " + resultPropertyUnitID + "\nProperty Name: " + resultPropertyName + "\nRevenue: $" + averageScore);

					}
					while(propertyResultSet.next());
				}
				
				break;
			}
			case 5: {
				Statement propertyStatement = null;
				ResultSet propertyResultSet = null;
				propertyStatement=conn.createStatement();
				String propertyQuery ="SELECT lh.userID, SUM(b.cost) AS Paid, u.username " + 
						"FROM bills b " + 
						"JOIN contracts c ON b.contractID = c.contractID " + 
						"JOIN leases l ON c.leaseID = l.leaseID " + 
						"JOIN lease_history lh ON l.leaseID = lh.leaseID " + 
						"JOIN users u ON lh.userID = u.userID " + 
						"WHERE b.paymentDate IS NOT NULL " + 
						"AND (b.paymentDate BETWEEN date_add(CURDATE(), INTERVAL -1 month) AND CURDATE()) " + 
						"GROUP BY lh.userID " + 
						"ORDER BY userID";
						

				propertyResultSet = propertyStatement.executeQuery(propertyQuery);
				if(propertyResultSet.next()) {
					do {
						String resultUserName = propertyResultSet.getString("u.username");
						int averageScore = propertyResultSet.getInt("Paid");
						int userID1 = propertyResultSet.getInt("lh.userID");

						System.out.print("\n");
						System.out.println("User ID: " + userID1 + "\nName: " + resultUserName + "\n Total Paid: $" + averageScore);
						

					}
					while(propertyResultSet.next());
				}
				
				break;
			}
			case 6: {
				Statement propertyStatement = null;
				ResultSet propertyResultSet = null;
				propertyStatement=conn.createStatement();
				String propertyQuery ="SELECT lh.userID, SUM(b.cost) AS Paid, u.username " + 
						"FROM bills b " + 
						"JOIN contracts c ON b.contractID = c.contractID " + 
						"JOIN leases l ON c.leaseID = l.leaseID " + 
						"JOIN lease_history lh ON l.leaseID = lh.leaseID " + 
						"JOIN users u ON lh.userID = u.userID " + 
						"WHERE b.paymentDate IS NOT NULL " + 
						"AND (b.paymentDate BETWEEN date_add(CURDATE(), INTERVAL -3 month) AND CURDATE()) " + 
						"GROUP BY lh.userID " + 
						"ORDER BY userID";

				propertyResultSet = propertyStatement.executeQuery(propertyQuery);
				if(propertyResultSet.next()) {
					do {
						String resultUserName = propertyResultSet.getString("u.username");
						int averageScore = propertyResultSet.getInt("Paid");
						int userID1 = propertyResultSet.getInt("lh.userID");

						System.out.print("\n");
						System.out.println("User ID: " + userID1 + "\nName: " + resultUserName + "\n Total Paid: $" + averageScore);

					}
					while(propertyResultSet.next());
				}
				
				break;
			}
			case 7: {
				Statement propertyStatement = null;
				ResultSet propertyResultSet = null;
				propertyStatement=conn.createStatement();
				String propertyQuery ="SELECT lh.userID, SUM(b.cost) AS Paid, u.username " + 
						"FROM bills b " + 
						"JOIN contracts c ON b.contractID = c.contractID " + 
						"JOIN leases l ON c.leaseID = l.leaseID " + 
						"JOIN lease_history lh ON l.leaseID = lh.leaseID " + 
						"JOIN users u ON lh.userID = u.userID " + 
						"WHERE b.paymentDate IS NOT NULL " + 
						"AND (b.paymentDate BETWEEN date_add(CURDATE(), INTERVAL -6 month) AND CURDATE()) " + 
						"GROUP BY lh.userID " + 
						"ORDER BY userID";

				propertyResultSet = propertyStatement.executeQuery(propertyQuery);
				if(propertyResultSet.next()) {
					do {
						String resultUserName = propertyResultSet.getString("u.username");
						int averageScore = propertyResultSet.getInt("Paid");
						int userID1 = propertyResultSet.getInt("lh.userID");

						System.out.print("\n");
						System.out.println("User ID: " + userID1 + "\nName: " + resultUserName + "\n Total Paid: $" + averageScore);

					}
					while(propertyResultSet.next());
				}
				
				break;
			}
			case 8: {
				Statement propertyStatement = null;
				ResultSet propertyResultSet = null;
				propertyStatement=conn.createStatement();
				String propertyQuery ="SELECT lh.userID, SUM(b.cost) AS Paid, u.username " + 
						"FROM bills b " + 
						"JOIN contracts c ON b.contractID = c.contractID " + 
						"JOIN leases l ON c.leaseID = l.leaseID " + 
						"JOIN lease_history lh ON l.leaseID = lh.leaseID " + 
						"JOIN users u ON lh.userID = u.userID " + 
						"WHERE b.paymentDate IS NOT NULL " + 
						"AND (b.paymentDate BETWEEN date_add(CURDATE(), INTERVAL -12 month) AND CURDATE()) " + 
						"GROUP BY lh.userID " + 
						"ORDER BY userID";

				propertyResultSet = propertyStatement.executeQuery(propertyQuery);
				if(propertyResultSet.next()) {
					do {
						String resultUserName = propertyResultSet.getString("u.username");
						int averageScore = propertyResultSet.getInt("Paid");
						int userID1 = propertyResultSet.getInt("lh.userID");

						System.out.print("\n");
						System.out.println("User ID: " + userID1 + "\nName: " + resultUserName + "\nTotal Paid: $" + averageScore);
					}
					while(propertyResultSet.next());
				}
				
				break;
			}
			
			default: {
				System.out.println("Sorry, that doesn't match one of the available options.");
				adminUpdateReport(conn,userID);
			}
			
			}
			
			
			}
			catch (Exception err) {
				System.out.println("Sorry, that wasn't a valid input."+ " " +err.getMessage());
				admin(conn, userID);
			}

	}
		
	
}

