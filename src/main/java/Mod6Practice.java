// Mod6Practice.java
// D. Singletary
// 6/15/22
// JDBC Demo

import java.sql.*;
//import java.util.Scanner;

public class Mod6Practice {

    // precondition: database created with name (specified below),
    // user name and password is same as database name
    static final String DB_NAME = "RetailStore";
    //static final String USER_NAME = DB_NAME;
    //static final String USER_PW = DB_NAME;

    // class name for driver
    // Apache Derby
    //final String CLASS_NAME = "org.apache.derby.jdbc.ClientDriver";
    // SQL Server
    //final String CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    // derby connection url
    //final String CONN_URL = "jdbc:derby://localhost:1527/" + DB_NAME;

    // SQL Server connection url, use integratedSecurity=true for
    // Windows (vs. SQL) authentication
    //final String CONN_URL = "jdbc:sqlserver://localhost:1433;" +
    //        ";integratedSecurity=true;";
    //"databaseName=" + DB_NAME + ";integratedSecurity=true;";

    // H2 connection url
    static final String CONN_URL = "jdbc:h2:mem:" + DB_NAME;  // don't forget trailing : !

    // declare the SQL statements we will be executing
    static final String SQL_CREATE_TABLE = "CREATE TABLE Customer " +
            "(CustomerID int PRIMARY KEY NOT NULL," +
            "LastName varchar(20) NOT NULL," +
            "FirstName varchar(20) NOT NULL," +
            "Address varchar(20) NOT NULL," +
            "City varchar(20) NOT NULL," +
            "State varchar(2) NOT NULL," +
            "Zip varchar(5) NOT NULL)";

    static final String SQL_DROP_TABLE = "DROP TABLE Customer";
    static final String SQL_INSERT_ROW1 = "INSERT INTO Customer VALUES(" +
            "'100001', 'Smith', 'John', '1 Elm St.', 'Jacksonville', 'FL', '32242')";
    static final String SQL_INSERT_ROW2 = "INSERT INTO Customer VALUES(" +
            "'100002', 'Brown', 'Sally', '2 Oak Dr.', 'Orlando', 'FL', '32808')";
    static final String SQL_SELECT = "SELECT * FROM Customer";

    // table format string
    static final String TABLE_FORMAT = "%-7.7s%-20.20s%-20.20s%-20.20s%-20.20s%-6.6s%6.6s\n";

    // need a scanner to pause execution to examine table before dropping it
    //static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // https://h2database.com/html/cheatSheet.html
        try(Connection con = DriverManager.getConnection(CONN_URL)) {
            Statement stmt = con.createStatement(); // this can be reused

            log("Check for valid connection: " + con.isValid(0));

            // create the DB
            // H2 embedded requires DB name in connection URL
            //stmt.executeUpdate("CREATE DATABASE " + DB_NAME + ";");
            log("database created");

            // create the table
            stmt.executeUpdate(SQL_CREATE_TABLE);
            log("Table created");

            // insert some data
            stmt.executeUpdate(SQL_INSERT_ROW1);
            stmt.executeUpdate(SQL_INSERT_ROW2);
            log("records added");

            ResultSet rs = stmt.executeQuery(SQL_SELECT); // get everything
            log("query results:");

            while (rs.next())
            {
                // All of our table fields are Strings, call "getString" to
                // get each field from the ResultSet and save it for display
                int custID = rs.getInt("CustomerID");
                String lastName = rs.getString("LastName");
                String firstName = rs.getString("FirstName");
                String address = rs.getString("Address");
                String city = rs.getString("City");
                String state = rs.getString("State");
                String zip = rs.getString("Zip");
                //System.out.printf("%-7.7s%-20.20s%-20.20s%-20.20s%-20.20s%-6.6s%6.6s\n",
                System.out.printf(TABLE_FORMAT,
                        custID, lastName, firstName, address, city, state, zip);
            }

            // drop the table and DB
            log("dropping table");
            stmt.executeUpdate(SQL_DROP_TABLE);
            log("table dropped");

            // H2 embedded drops the table automatically
            //stmt.executeUpdate("DROP DATABASE " + DB_NAME + ";");
            log("Database dropped");
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }
    public static void log(Object o) {
        System.out.println(o);
    }
}
