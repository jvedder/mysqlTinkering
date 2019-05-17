package mysqltinkering;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;

/*
 * Docs at https://dev.mysql.com/doc/connectors/en/connector-j-5.1.html
 */

public final class Main
{

    // NOTE: Do not hard-code passwords as shown here
    private static final String CONN_STRING = "jdbc:mysql://localhost/test?user=tester&password=passfail&useSSL=false";
    
    private static final Calendar cal = Calendar.getInstance(); 

    public static void main(String[] args) throws Exception
    {
        try
        {
            Connection conn = null;
            Statement stmt = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            boolean b = false;
            String sql = "";

            // Load the mysql JBDC Driver
            // Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();

            // Connect to mysql
            conn = DriverManager.getConnection(CONN_STRING);

//            System.out.println("### PREPARE STATEMENT");
//            sql = "INSERT INTO points (x,y) VALUES (?,?);";
//            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            // printResultSetMetaData(pstmt.getMetaData());
//
//            System.out.println("### EXECUTE ");
//            pstmt.setInt(1, 3);
//            pstmt.setInt(2, 3);
//            b = pstmt.execute();
//            System.out.println("execute() return value: " + b);
//
//            System.out.println("### GET RESULT SET");
//            rs = pstmt.getResultSet();
//            printResultSet(rs);
//            if (rs != null) rs.close();
//
//            System.out.println("### GET GENERATED KEYS");
//            rs = pstmt.getGeneratedKeys();
//            printResultSet(rs);
//            if (rs != null) rs.close();
//            
//            pstmt.close();
//
//            System.out.println("### CREATE STATEMENT ");
//            stmt = conn.createStatement();
//            System.out.println("### EXECUTE QUERY:");
//            sql = "SELECT x,y FROM points;";
//            rs = stmt.executeQuery(sql);
//            
//            System.out.println("### RESULT SET:");
//            printResultSet(rs);
//            if (rs != null) rs.close();
//            
//            stmt.close();
            
            
            System.out.println("### CREATE STATEMENT 2");
            stmt = conn.createStatement();
            System.out.println("### EXECUTE QUERY 2:");
            sql = "SELECT * FROM devices";
            rs = stmt.executeQuery(sql);
            
            System.out.println("### RESULT SET 2:");
            printResultSet(rs);
            if (rs != null) rs.close();
            
            stmt.close();

            conn.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public static void printResultSet(ResultSet rs)
    {
        if (rs == null)
        {
            System.out.println("ResultSet is null.");
            return;
        }

        try
        {
            
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            
            System.out.println("---------");
            printResultSetMetaData(md);
            System.out.println("---------");

            
            /**
             * First, Print the column names
             */
            for (int col = 1; col <= cols; col++)
            {
                if (col > 1) System.out.print(",");
                System.out.print(md.getColumnName(col));
            }
            System.out.println();

            while (rs.next())
            {
                /**
                 * Print out each row
                 */
                for (int col = 1; col <= cols; col++)
                {
                    if (col > 1) System.out.print(",");
                    switch (md.getColumnType(col))
                    {

                        case Types.BOOLEAN:
                            boolean b = rs.getBoolean(col);
                            System.out.print(b);
                            break;
                        case Types.DOUBLE:
                        case Types.REAL:
                            double d = rs.getDouble(col);
                            System.out.print(d);
                            break;
                        case Types.FLOAT:
                            float f = rs.getFloat(col);
                            System.out.print(f);
                            break;  
                        case Types.INTEGER:
                        case Types.BIT:
                            int i = rs.getInt(col);
                            System.out.print(i);
                            break; 
                        case Types.BIGINT:
                            long l = rs.getLong(col);
                            System.out.print(l);
                            break; 
                        case Types.CHAR:
                        case Types.LONGVARCHAR:
                        case Types.VARCHAR:
                            String s = rs.getString(col);
                            System.out.print(s);
                            break; 
                        case Types.DATE:
                            Date dt = rs.getDate(col,cal);
                            System.out.print(dt.toString());
                            break; 
                        case Types.TIME:
                            Time tm = rs.getTime(col,cal);
                            System.out.print(tm);
                            break; 
                        case Types.TIMESTAMP:
                            Timestamp ts = rs.getTimestamp(col,cal);
                            System.out.print(ts);
                            break; 
                        default:
                            System.out.print("[Type " + md.getColumnType(col) + "]");
                    }
                    
                }
                System.out.println();
            }
            System.out.println("---------");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return;
        }

    }

    public static void printResultSetMetaData(ResultSetMetaData md)
    {

        if (md == null)
        {
            System.out.println("ResultSetMetaData is null");
            return;
        }

        try
        {
            int cols = md.getColumnCount();
            System.out.println("Columns: " + cols);

            for (int col = 1; col <= cols; col++)
            {
                System.out.println();
                System.out.println("COLUMN # " + col);
                System.out.println("    CatalogName=" + md.getCatalogName(col));
                System.out.println("    SchemaName=" + md.getSchemaName(col));
                System.out.println("    TableName=" + md.getTableName(col));
                System.out.println("    ColumnName=" + md.getColumnName(col));
                System.out.println("    ColumnLabel=" + md.getColumnLabel(col));
                System.out.println("    ColumnType=" + md.getColumnType(col));
                System.out.println("    ColumnTypeName=" + md.getColumnTypeName(col));
                System.out.println("    ColumnClassName=" + md.getColumnClassName(col));
                System.out.println("    ColumnDisplaySize=" + md.getColumnDisplaySize(col));
                System.out.println("    Precision=" + md.getPrecision(col));
                System.out.println("    Scale=" + md.getScale(col));
                System.out.println("    isAutoIncrement=" + md.isAutoIncrement(col));
                System.out.println("    isCaseSensitive=" + md.isCaseSensitive(col));
                System.out.println("    isCurrency=" + md.isCurrency(col));
                System.out.println("    isDefinitelyWritable=" + md.isDefinitelyWritable(col));
                System.out.println("    isNullable=" + md.isNullable(col));
                System.out.println("    isReadOnly=" + md.isReadOnly(col));
                System.out.println("    isSearchable=" + md.isSearchable(col));
                System.out.println("    isSigned=" + md.isSigned(col));
                System.out.println("    isWritable=" + md.isWritable(col));
            }
        }
        catch (SQLException e)
        {
            System.out.println("   EXCEPTION: " + e.getClass().getName() + ";" + e.getMessage());
        }

    }
}
