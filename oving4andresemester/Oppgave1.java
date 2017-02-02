package oving4andresemester;

import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Database1{
	  public Connection connect() throws SQLException, ClassNotFoundException{
		  String brukernavn = DataLeser1.lesTekst("Brukernavn: ");
		  String passord = DataLeser1.lesTekst("Passord: ");
		  String databasedriver = "com.mysql.jdbc.Driver";
		  Class.forName(databasedriver);  // laster inn driverklassen
			  
		  String databasenavn = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + brukernavn + "?user=" + brukernavn + "&password=" + passord;
		  Connection forbindelse = DriverManager.getConnection(databasenavn);
		  return forbindelse;
	  }
	  
	  public Statement statement(Connection forbindelse) throws SQLException{
		  Statement setning = forbindelse.createStatement();
		  return setning;
	  }
	  
	  public void disconnect(Statement setning, Connection forbindelse) throws SQLException{
		  setning.close();
		  forbindelse.close();
	  }
	  
	  public String isbn(Statement setning) throws SQLException{
		  String isbn = DataLeser1.lesTekst("ISBN: ");
		  String sqlsetn = "select forfatter, tittel from boktittel where isbn = '"+isbn+"'";
		  String sqlsetn1 = "select eks_nr from eksemplar where isbn = '"+isbn+"'";
		  String output = "";
		  String output1 = "";
		  try {
			  ResultSet res = setning.executeQuery(sqlsetn);
			  while(res.next()){
				  String forfatter = res.getString("forfatter");
				  String tittel = res.getString("tittel");
				  output += "Forfatter: "+forfatter+", tittel: "+tittel;
			  }
			  
			  res.close();
			  ResultSet res1 = setning.executeQuery(sqlsetn1);
			  int antall = 0;
			  while(res1.next()){
				  antall++;
			  }
			  
			  res1.close();
			  output1 = Integer.toString(antall);
		  } catch (SQLException e){
			  return "Noe gikk galt";
		  }
		  return output+", antall eksemplarer: "+ output1;
	  }
}

class DataLeser1 {
	  /**
	   * Leser en tekst fra brukeren. Blank tekst godtas ikke.
	   * Teksten "trimmes" før den returneres til klienten.
	   */
	  public static String lesTekst(String ledetekst) {
		  String tekst = showInputDialog(ledetekst);
		  while (tekst == null || tekst.trim().equals("")) {
			  showMessageDialog(null, "Du må oppgi data.");
			  tekst = showInputDialog(ledetekst);
		  }
		  return tekst.trim();
	  }
}

public class Oppgave1 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Database1 db = new Database1();
		Connection forbindelse = db.connect();
		Statement setning = db.statement(forbindelse);
		showMessageDialog(null, db.isbn(setning));
		db.disconnect(setning, forbindelse);
	}
}
