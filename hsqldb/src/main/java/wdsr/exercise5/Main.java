package wdsr.exercise5;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wdsr.exercise5.hsqldb.MyHsqlServer;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static final String username = "SA";
    public static final String password = "";
    public static final String url = "jdbc:hsqldb:hsql://127.0.0.1:9001/test-db";
    
  	public static void main(String[] args) throws InterruptedException, SQLException {
		MyHsqlServer dbServer = new MyHsqlServer(9020, "test-db", "mem:test-db");
		dbServer.start();
		log.info("Database started");
		
		Connection con = DriverManager.getConnection(url,username,password);
		
		Statement stmt = con.createStatement();
		
		//Tworzenie tabeli i jej zapelnianie milionem rekordow
	    /*ResultSet rs = stmt.executeQuery("CREATE TABLE UserData(id int, firstName varchar(25), lastName varchar(25))");
	    
	    for(int i = 0; i<1000000;i++){
	    	stmt.executeQuery("INSERT INTO UserData VALUES ("+i+",'Damian"+i+"','Biedrzycki"+i+"')");
	    }  */		
	
		stmt.executeQuery("DROP INDEX test");
		
		Date startDate = new Date();
		ResultSet rs1 = stmt.executeQuery("SELECT id, firstName, lastName FROM UserData WHERE id = 950000");
		Date endDate = new Date();
		float data = endDate.getTime()-startDate.getTime();
		
		while(rs1.next()){
			System.out.println(rs1.getString("firstName")+" "+rs1.getString("lastName"));
		}
		
		System.out.println("Czas wykonania bez indeksu "+data);
		
		
		stmt.executeQuery("CREATE INDEX test ON UserData(id)");
		
		Date startDate1 = new Date();
		ResultSet rs2 = stmt.executeQuery("SELECT id, firstName, lastName FROM UserData WHERE id = 950000");
		Date endDate1 = new Date();
		float data1 = endDate1.getTime()-startDate1.getTime();
		
		while(rs2.next()){
			System.out.println(rs2.getString("firstName")+" "+rs2.getString("lastName"));
		}
		
		System.out.print("Czas wykonania z indeksem "+data1);
		
		
		rs1.close();
		rs2.close();
		stmt.close();
		con.close();

		
	}
}
