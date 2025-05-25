/*
1. Download MySql/J connector from mysql website's download section. 
2. For Windows choose option Platform independent and download zip version.
3. Unzip and extract jar file: mysql-connector-j-8.3.0.jar
4. Paste this jar file to your folder where you are creating source code.
Note: You can place anywhere else also.

5.Now during compile and  run set the classpath of your jar file.

suppose I have placed this jar file in D:\ drive and my source code is also at same location then

To compile:   D:\>javac -cp mysql-connector-j-8.3.0.jar; StatementAndPreparedStatement.java

To run: D:\>java -cp mysql-connector-j-8.3.0.jar; StatementAndPreparedStatement
note: at time of run apply space between ; and classfile
*/

import java.sql.*;
public class StatementAndPreparedStatement
{
public static void main(String args[])
{
Connection con=null;
ResultSet rs=null;
try{
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
System.out.println("Connection created");
Statement st=con.createStatement();
//st.executeUpdate("insert into emp(empid,name)values (4, 'pqr' )");

/* rs=st.executeQuery("select * from emp");
while(rs.next())
{
System.out.print(rs.getInt(1));
System.out.println(rs.getString(2));
}*/

/*rs=st.executeQuery("select * from emp where empid=1");
while(rs.next()){
System.out.print(rs.getInt(1));
System.out.println(rs.getString(2));
}*/

/*st.executeUpdate("update emp set name='yyyyyy' where empid=1");
rs=st.executeQuery("select * from emp");
while(rs.next())
{
System.out.print(rs.getInt(1));
System.out.println(rs.getString(2));
}*/
st.executeUpdate("delete from emp  where empid=1");
rs=st.executeQuery("select * from emp");
while(rs.next())
{
System.out.print(rs.getInt(1));
System.out.println(rs.getString(2));
}

//---------------------------------------------------------

/* PreparedStatement*/

/* INSERT RECORD
PreparedStatement st=con.prepareStatement("insert into emp(empid,name)values(?,?)");
st.setInt(1,Integer.parseInt(args[0]));
st.setString(2,args[1]);
st.executeUpdate();
*/

/* UPDATE RECORD
PreparedStatement st=con.prepareStatement("update emp set name=? where empid=?");
st.setString(1,args[0]);
st.setInt(2,Integer.parseInt(args[1]));
st.executeUpdate();
*/

/* DELETE RECORD
PreparedStatement st=con.prepareStatement("delete from emp where empid=?");

st.setInt(1,Integer.parseInt(args[0]));
st.executeUpdate();
*/

// Display Record
/*
st=con.prepareStatement("select * from emp");
ResultSet rs=st.executeQuery();
while(rs.next())
{
System.out.print(rs.getInt(1));
System.out.println('\t'+rs.getString(2));
}*/
con.close();
}
catch(Exception e)
{
System.out.println(e);
}

}

}