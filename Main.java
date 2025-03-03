import java.sql.*;
import java.util.Scanner;

public class Main {

    public static int getId() {
        int latestId = 1000;
        try {
            String url = "jdbc:mysql://localhost:3306/Login";
            String userName = "root";
            String Pass = "7892921017";
            Connection con = DriverManager.getConnection(url, userName, Pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select MAX(id) from Info");
            if(rs.next()) {
                int id = rs.getInt(1);
                latestId = id == 0 ? 1000 : id + 1;
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latestId;
    }

    public static void Insert(String name, String password) {
        try {
            String url = "jdbc:mysql://localhost:3306/Login";
            String userName = "root";
            String Pass = "7892921017";
            Connection con = DriverManager.getConnection(url, userName, Pass);

            int id = getId();
            String query = "insert into Info value (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, password);

            int row = st.executeUpdate();
            if(row > 0) {
                System.out.println("Signed up Successfully\nYour UserId : " + id);
            }
            st.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void getInfo(int id, String password) {

        try {
            String url = "jdbc:mysql://localhost:3306/Login";
            String userName = "root";
            String Pass = "7892921017";
            Connection con = DriverManager.getConnection(url, userName, Pass);
            Statement st = con.createStatement();
            String query = "Select * from info where id = "+id;

            ResultSet rs = st.executeQuery(query);
            String name = "";
            String Received_password = "";
            if(rs.next()) {
                name = rs.getString(2);
                Received_password = rs.getString(3);
            } else {
                System.out.println("No data Found");
                rs.close();
                st.close();
                con.close();
                return;
            }
            if(!Received_password.equals(password)) {
                System.out.println("Password Incorrect");
                rs.close();
                st.close();
                con.close();
                return;
            }
            System.out.println("Logged In Successfully");
            System.out.println("Id : "+id+"\nName : "+name+"\nPassWord : "+Received_password);
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int ch;
        do {
            System.out.println("Welcome");
            System.out.print("1.Sign up\n2.Login\nSelect one from above : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 : {
                    System.out.print("Enter your name : ");
                    String name  = sc.nextLine();

                    System.out.print("Create password : ");
                    String password = sc.nextLine();
                    Insert(name, password);
                    break;
                }
                case 2 : {
                    System.out.print("Enter your id : ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter your Password : ");
                    String password = sc.nextLine();
                    getInfo(id, password);
                    break;
                }
            }
            System.out.print("Do you want to continue ? (1/0) : ");
            ch = sc.nextInt();
        } while(ch == 1);
    }
}