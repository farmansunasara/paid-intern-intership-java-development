package com.project;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class TrainAllocationSystem {
	private static Map<Integer, UserProfile> userProfiles = new HashMap<>();
	static String[] trainnames = {"Train 1","Train 2","Train 3"};
	static int booggie=3;
	static int seat=3;

	static boolean[][][] seats = new boolean[trainnames.length][booggie][seat];
	public static void main(String[] args) {
		
		TrainAllocationSystem tra= new TrainAllocationSystem();
		Scanner sc=new Scanner(System.in);
		userProfiles =  new HashMap<>();
		
		do {
			tra.displayMenu();
			System.out.println("Enter a your choice");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: 
				tra.createUserProfile(sc);
				break;
			case 2: 
				tra.chooseTrain(sc);
				break;
			case 3: 
				tra.displayProfile(sc);
				break;
			case 4: 
				tra.displayAllProfile(sc);
				break;
			case 5: 
				tra.displayTrainStatus();
				break;
			case 6: 
				System.exit(0);
			default:
				System.out.println("make sure your choice number should be between 1 to 6");
				break;
			}
		}while(true);
		
	
		

	}
	
	private void displayMenu() {
		System.out.println("===== Main Menu =====");

		System.out.println("1. Enter Profile");
		System.out.println("2. Enter Train Choice");
		System.out.println("3. Display Person Information");
		System.out.println("4. Display Complete Information ");
		System.out.println("5. Display Train Status");
		System.out.println("6. Exit");
		
	}
	
	private void createUserProfile(Scanner sc) {
		System.out.println("Enter a your name");
		String name = sc.next();
		System.out.println("Enter a your age");
		int age = sc.nextInt();
		int loginId = generateLoginId();
		
		
		 UserProfile userProfile = new UserProfile(loginId, name, age);
	        userProfiles.put(loginId, userProfile);
		
		System.out.println("Profile register successfully");
		
		System.out.println("your login Id : "+ loginId);

	}
	
	private void chooseTrain(Scanner sc) {

		System.out.println("Enter a your login Id");
		int loginId=sc.nextInt();
        UserProfile userProfile = findUserByLoginId(loginId);
	        if (userProfile == null) {
	            System.out.println("Invalid login ID.");
	            return;
	        }
	        System.out.println("Available trains");
		for(int i=0;i<trainnames.length;i++) {
			System.out.println(i+1+"."+trainnames[i]);
			
		}
		
		System.out.println("Enter a your train");
		int trainChoice=sc.nextInt();
		if(trainChoice<1 || trainChoice>3) {
			
			System.out.println("Train not available");
			return;
		}
		
		 
        if(userProfile.trainName != null) {
        	System.out.println("Already your seat is booked..!");
        	return;
        }
        
		allocatSeat(userProfile,trainChoice-1);
		


	}
	
	
	
	private void allocatSeat(UserProfile userProfile,int trainIndex) {
		// TODO Auto-generated method stub
		int totalBoogies=seats[trainIndex].length-1;
		boolean isLastSeatLeft=seats[trainIndex][totalBoogies][seats[trainIndex][totalBoogies].length-1];
		
		if(isLastSeatLeft) {
			System.out.println("No seats available..!");
			return;
		}
		
		for(int i=0; i<totalBoogies; i++) {
			for(int j=0;j<seats[trainIndex][i].length;j++) {
				if(!seats[trainIndex][i][j]) {
					seats[trainIndex][i][j]=true;
					System.out.println("your seat is booked Successfully ");
					
					userProfile.setTrainName(trainnames[trainIndex]);
					userProfile.setBoogieNumber(i);
					userProfile.setSeatNumber(j);
					return;
				}
			}
			
		}
		
	}
	private int loginId = 1000;
	private int generateLoginId() {
		return ++loginId;
		
	}
	
	private UserProfile findUserByLoginId(int loginId) {
        if (userProfiles.containsKey(loginId)) {
			return userProfiles.get(loginId);
		}
        return null;
    }
	private void displayProfile(Scanner sc) {
		System.out.println("Enter a login_Id");
		int loginId=sc.nextInt();
		
		UserProfile userProfile = findUserByLoginId(loginId);
		
		if(userProfile == null) {
			System.out.println("Invalid login Id");
			return;
		}
		
		System.out.println(userProfile);
	}
	
	private void displayAllProfile(Scanner sc) {
		
		if(userProfiles==null) {
			System.out.println("No User registered..!");
    		return;
		}
		
		for(Entry<Integer, UserProfile> userProfile : userProfiles.entrySet()) {
    		System.out.println(userProfile.getValue());
    	}
		
	}
	
	private void displayTrainStatus() {
		
		 for (int i = 0; i < seats.length; i++) {
	        	
	        	int totalSeats = 0, totalBookedSeets = 0;
				for (int j = 0; j < seats[i].length; j++) {
					
					int totalBoogieSeats = 0, totalBookedBoogieSeats = 0;
					for (int k = 0; k < seats[i][j].length; k++) {
						
						totalSeats++;
						totalBoogieSeats++;
						if(seats[i][j][k]) {
							totalBookedSeets++;
							totalBookedBoogieSeats++;
						}
					}
					System.out.println(trainnames[i] + ", " +"Boogie " + (j+1) + " - " + " Total seats " + totalBoogieSeats + ", Booked seats " + totalBookedBoogieSeats + ", Remaining seats " + (totalBoogieSeats-totalBookedBoogieSeats));
				}
				System.out.println(trainnames[i] + " - " + "Total seats " + totalSeats +", Booked seats " + totalBookedSeets + ", Remaining seats " + (totalSeats-totalBookedSeets) + "\n");
			}
		
	}
	

}

class UserProfile{
	int loginId;
	String name;
	int age;
	String trainName;
	int boogieNumber=-1;
	int seatNumber=-1;
	
	
	public UserProfile(int loginId, String name, int age) {
		this.loginId = loginId;
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "UserProfile [loginId=" + loginId + ", name=" + name + ", age=" + age + ", trainName=" + trainName
				+ ", boogieNumber=" + (boogieNumber+1) + ", seatNumber=" + (seatNumber+1) + "]";
	}

	
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
	public void setBoogieNumber(int boogieNumber) {
		this.boogieNumber = boogieNumber;
	}
	
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	
}


