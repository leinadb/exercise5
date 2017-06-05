
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {
	
	public static final String username = "SA";
    public static final String password = "";
    public static final String url = "jdbc:hsqldb:hsql://127.0.0.1:9026/test-db";
    
	public static void main(String[] args) throws InterruptedException, SQLException{		

		
		
		Connection con = DriverManager.getConnection(url,username,password);
		
		//Tworzenie tabeli Student 
		//String sql ="CREATE TABLE Student (id int, name varchar(50), sex varchar(50), age int, level int, PRIMARY KEY (id));";
		//Tworzenie tabeli faculty
		//String sql1 = "CREATE TABLE Faculty (id int, name varchar(50), PRIMARY KEY (id));";
		//Tworzenie tabeli Class
		//String sql2 = "CREATE TABLE Class (id int, name varchar(50), facultyId int, PRIMARY KEY(id), FOREIGN KEY (facultyId) REFERENCES Faculty(id));";
		//Tworzenie tabeli Enrollment
		//String sql3 = "CREATE TABLE Enrollment (studentId int, classId int, FOREIGN KEY (studentId) REFERENCES Student(id), FOREIGN KEY (classId) REFERENCES Class(id));";
		
		//Dodawanie danych do tabeli Student
		//String sql = "INSERT INTO Student(id,name,sex,age,level)VALUES(1,'John Smith','male',23,2),(2, 'Rebecca Milson','female',27,3),(3,'George Heartbraker','male',19,1),(4,'Deepika Chopra','female',25,3);";
		//Dodawanie danych do tabeli Faculty
		//String sql1 = "INSERT INTO Faculty(id,name)VALUES(100,'Engineering'),(101,'Philosophy'),(102,'Law and administration'),(103,'Languages');";
		//Dodawanie danych do tabeli Class
		//String sql2 = "INSERT INTO Class(id,name,facultyId) VALUES(1000,'Introduction to labour law',102),(1001,'Graph algoritms',100),(1002,'Existentialism in 20th century',101),(1003,'English grammar',103),(1004,'From Plato to Kant',101);";
		//Dodawanie danych do tabeli Enrollment
		//String sql3 = "INSERT INTO Enrollment(studentId, classId) VALUES(1,1000),(1,1002),(1,1003),(1,1004),(2,1002),(2,1003),(4,1000),(4,1002),(4,1003);";
			
		/*con.prepareStatement(sql).executeUpdate();
		con.prepareStatement(sql1).executeUpdate();
		con.prepareStatement(sql2).executeUpdate();
		con.prepareStatement(sql3).executeUpdate();*/
	
		//Zapytanie 1
		String sql = "SELECT id, name FROM Student";		
		
		ResultSet rs1 = con.prepareStatement(sql).executeQuery();
		
		System.out.println("(1) Numery i nazwiska wszystkich osób zarejestrowanych jako studenci: ");
		while(rs1.next()){			
			System.out.println("id = "+rs1.getString("id")+" name = "+rs1.getString("name"));
		}
		
		//Zapytanie 2		
		String sql1 ="SELECT s.id, s.name FROM Student s WHERE s.id NOT IN (SELECT studentId FROM Enrollment) ";
		
		ResultSet rs2 = con.prepareStatement(sql1).executeQuery();
		
		System.out.println("(2) Numery i nazwiska wszystkich osób, które nie s¹ zapisane na ¿aden przedmiot");
		while(rs2.next()){			
			System.out.println("id = "+rs2.getString("id")+" name = "+rs2.getString("name"));
		}
		
		//Zapytanie 3
		String sql2 = "SELECT s.id,s.name FROM Student s INNER JOIN Enrollment e ON s.id = e.studentId WHERE s.sex='female' AND e.classId = 1002;";		
				
		ResultSet rs3 = con.prepareStatement(sql2).executeQuery();
				
		System.out.println("(3) Numery i nazwiska osób p³ci ¿eñskiej ucz¹cych siê o egzystencjaliŸmie w 20 wieku ");
		while(rs3.next()){			
			System.out.println("id = "+rs3.getString("id")+" name = "+rs3.getString("name"));
		}
		
		//Zapytanie 4
		//String sql3 = "SELECT f.name as fname, Count(e.studentId) as sc, c.name as cname FROM FACULTY f INNER JOIN Class c ON c.facultyId = f.id INNER JOIN Enrollment e ON e.classId = c.id GROUP BY f.name, c.name HAVING Count(e.studentId)=0";
		String sql3 ="SELECT c.name  FROM Class c WHERE NOT EXISTS(SELECT e.classId FROM Enrollment e WHERE e.classId = c.id);";
		ResultSet rs4 = con.prepareStatement(sql3).executeQuery();
		
		System.out.println("(4) Nazwy wszystkich wydzia³ów, na których przedmioty nikt siê nie zapisa³ ");
		while(rs4.next()){			
			System.out.println("name = "+rs4.getString("name"));
		}
		
		//Zapytanie 5
		String sql4 ="SELECT MAX(s.age) as sage FROM Student s INNER JOIN Enrollment e ON s.id = e.studentId WHERE e.classId = 1000 ";
		
		ResultSet rs5 = con.prepareStatement(sql4).executeQuery();
		
		System.out.println("(5) Wiek najstarszej osoby ucz¹cej siê o prawie pracy ");
		while(rs5.next()){			
			System.out.println("age = "+rs5.getString("sage"));
		}
		
		//Zapytanie 6
		String sql5 ="SELECT c.name, Count(studentId) as studs From Enrollment e INNER JOIN Class c ON c.id = e.classId GROUP BY c.name HAVING Count(e.studentId) >=2 ";
		ResultSet rs6 = con.prepareStatement(sql5).executeQuery();
		
		System.out.println("(6) Nazwy przedmiotów, na które zapisa³y siê przynajmniej dwie osoby ");
		while(rs6.next()){			
			System.out.println(" name="+rs6.getString("name")+" students="+rs6.getString("studs"));
		}
		
		//Zapytanie 7
		String sql6 ="SELECT AVG(s.age) as sage ,s.level as slevel FROM Student s WHERE s.id IN(SELECT id FROM Student) GROUP BY s.level ";
		ResultSet rs7 = con.prepareStatement(sql6).executeQuery();
		
		System.out.println("(7) Poziomy osób studiuj¹cych i œredni wiek osób na ka¿dym poziomie ");
		while(rs7.next()){			
			System.out.println("level = "+rs7.getString("slevel")+" avg age="+rs7.getString("sage"));
		}

		con.close();
	}

}

