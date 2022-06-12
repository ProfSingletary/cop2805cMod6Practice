import java.sql.*;
import java.util.Scanner;

public class Mod6Practice {

    // precondition: database created with name (specified below),
    // user name and password is same as database name
    static final String DB_NAME = "cop2805c";
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
            "(CustomerID varchar(6) PRIMARY KEY NOT NULL," +
            "LastName varchar(20) NOT NULL," +
            "FirstName varchar(20) NOT NULL," +
            "Address varchar(20) NOT NULL," +
            "City varchar(20) NOT NULL," +
            "State varchar(2) NOT NULL," +
            "Zip varchar(5) NOT NULL)";

    static final String SQL_DROP_TABLE = "DROP TABLE Customer";
    static final String SQL_INSERT_ROW1 = "INSERT INTO Customer VALUES(" +
            "'A10001', 'Smith', 'John', '1 Elm St.', 'Jacksonville', 'FL', '32242')";
    static final String SQL_INSERT_ROW2 = "INSERT INTO Customer VALUES(" +
            "'A10002', 'Brown', 'Sally', '2 Oak Dr.', 'Orlando', 'FL', '32808')";
    static final String SQL_SELECT = "SELECT * FROM Customer";

    // heading for table data output
    static final String TABLE_HEADING = "CUSTID " +
            "LAST NAME              " +
            "FIRST NAME             " +
            "ADDRESS                " +
            "CITY                   " +
            "STATE  " +
            "ZIP";

    // column names
    static final String TABLE_COL_CUSTID = "CustomerID";
    static final String TABLE_COL_LASTNAME = "LastName";
    static final String TABLE_COL_FIRSTNAME = "FirstName";
    static final String TABLE_COL_ADDRESS = "Address";
    static final String TABLE_COL_CITY = "City";
    static final String TABLE_COL_STATE = "State";
    static final String TABLE_COL_ZIP = "Zip";

    // table format string
    static final String TABLE_FORMAT = "%-7.7s%-20.20s%-20.20s%-20.20s%-20.20s%-6.6s%6.6s\n";

    // need a scanner to pause execution to examine table before dropping it
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // https://h2database.com/html/cheatSheet.html
        try(Connection con = DriverManager.getConnection(CONN_URL)) {
            Statement stmt = con.createStatement(); // this can be reused
            ResultSet rs = null;

            log("Check for valid connection: " + con.isValid(0));

            // create the DB
            // H2 embedded requires DB name in connection URL
            //stmt.executeUpdate("CREATE DATABASE " + DB_NAME + ";");
            log("database created");

            // create the table
            stmt.executeUpdate(SQL_CREATE_TABLE);
            log("Table created");

            // drop the table and DB
            stmt.executeUpdate(SQL_DROP_TABLE);
            log("Customer table dropped");

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
