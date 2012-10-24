package tracer;
import java.sql.*;
import java.util.ArrayList;

/**
 * 
 * @author Matthew Wood
 *
 * @about The CreateDatabase class is used to insert array types into an
 * existing database. Use the create(ArrayList<String>, ArrayList<String>, String)
 * method and to insert items from two ArrayList types and the file name
 * from the string path name into the database. An important note is that this
 * class uses the local machine to connect to the database so the computer this code
 * runs on needs to be connected to the database before the create() method is called.
 */

public class CreateDatabase {
	//STEP 1. Import required packages

	// JDBC driver name and database URL
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost:3306/cse4214";	//port 3306 on localhost (computer run on)
																				//have to be connected to database before called
	//  Database credentials
	private static final String USER = "cse4214";
	private static final String PASS = "ab1234";
	
	/**
	 * @about Takes two ArrayLists of type string and a path name 
	 * and inserts the items from the arrays and the file name into
	 * the database.
	 * @return type void
	 */
	   
	public void create(ArrayList<String> codeArrayToPush, ArrayList<String> commentArrayToPush, String pathName) {
		Connection conn = null;
	   
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//manage path name - retrieve file name
			String fileName = "";
			int j = pathName.length() - 1;
			while(pathName.charAt(j) != '\\'){
				fileName += pathName.charAt(j);
				j--;
			}
			
			j = fileName.length() - 1;
			String temp = "";
			while(j >= 0){
				temp += fileName.charAt(j);
				j--;
			}
	      
			fileName = temp;	//resulting file name
			
			//STEP 4: Execute inserts
			System.out.println("Creating statements...");
	      
			PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO File VALUES (?)");
			stmt1.setString(1, fileName);	
			stmt1.execute();	//execute single mysql insert
			stmt1.close();
	      
			conn.setAutoCommit(false);
			PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO Code VALUES (?,?)");
			for(int i = 0; i < codeArrayToPush.size(); i++){
				stmt2.setString(1, fileName);
				stmt2.setString(2, codeArrayToPush.get(i).toString());
				stmt2.addBatch();	//execute batch (bulk) mysql inserts
			}
			stmt2.executeBatch();
			conn.commit();
			stmt2.close();
	      
			PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO Comment VALUES (?,?)");
			for(int i = 0; i < commentArrayToPush.size(); i++){
				stmt3.setString(1, fileName);
				stmt3.setString(2, commentArrayToPush.get(i).toString());
				stmt3.addBatch();	//execute batch (bulk) mysql inserts
		    }
			stmt3.executeBatch();
			conn.commit();
			stmt3.close();
	      
	      
			conn.close();
		}catch(SQLException se){		//error handling section
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		System.out.println("Goodbye!");	
	}//end main
}//end CreateDatabase

