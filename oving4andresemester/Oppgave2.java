package oving4andresemester;

import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Database2{
	  public Connection connect() throws SQLException, ClassNotFoundException{
		  String brukernavn = DataLeser2.lesTekst("Brukernavn: ");
		  String passord = DataLeser2.lesTekst("Passord");
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
	  
	  public String tilgjengeligeBøker(Statement setning) throws SQLException{
		  String sqlsetn = "select isbn, eks_nr from eksemplar where laant_av is null";
		  String output = "";
		  try{
			  ResultSet res = setning.executeQuery(sqlsetn);
			  while(res.next()){
				  int eks_nr = res.getInt("eks_nr");
				  String isbn = res.getString("isbn");
				  output += "ISBN: "+isbn+", eksemplar nr: "+eks_nr+"\n";
			  }
		  }catch (SQLException e){
			  return "Noe gikk galt";
		  }
		  return output;
	  }
	  
	  public boolean lånBok(Statement setning) throws SQLException{
		  String navn = DataLeser2.lesTekst("Navn: ");
		  String isbn = DataLeser2.lesTekst("ISBN: ");
		  int eksemplarnr = DataLeser2.lesHeltall("Eksemplar nummer: ");
		  
		  try{
			  String sqlsetn = "update eksemplar set laant_av = '"+navn+"' where isbn = '"+isbn+"' and eks_nr = "+eksemplarnr+" and laant_av is null";
			  if (setning.executeUpdate(sqlsetn) == 0){
				  return false;
			  }
			  return true;
		  } catch (SQLException e){
			  return false;
		  }
	  }
	  
	  public boolean leverBok(Statement setning) throws SQLException{
		  String isbn = DataLeser2.lesTekst("ISBN: ");
		  int eksemplarnr = DataLeser2.lesHeltall("Eksemplar:");
		  
		  try {
			  String sqlsetn = "update eksemplar set laant_av = null where isbn = '"+isbn+"' and eks_nr = "+eksemplarnr;
			  if (setning.executeUpdate(sqlsetn) == 0){
				  return false;
			  }
			  return true;
		  } catch (SQLException e){
			  return false;
		  }
	  }
}

class DataLeser2 {
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
	  
	  public static int lesHeltall(String ledetekst) {
		  int tall = 0;
		  boolean ok = false;
		  String melding = ledetekst;
		  do {
			  String tallSomTekst = lesTekst(melding);
		      try {
		    	  tall = Integer.parseInt(tallSomTekst);
		    	  ok = true;
		      } catch (NumberFormatException e) {
		    	  melding = "Du skrev: " + tallSomTekst
		    			  + ".\nDet er et ugyldig heltall. Prøv på nytt.\n" + ledetekst;
		      }
		  } while (!ok);
		  
		  return tall;
	  }
}

public class Oppgave2 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Database2 db = new Database2();
		Connection forbindelse = db.connect();
		Statement setning = db.statement(forbindelse);
		
		boolean ok = true;
		
		while(ok){
			switch(DataLeser2.lesHeltall("1: Sjekk tilgjengelig bøker\n2: Lån bok\n3: Lever bok\n4: Avslutt")){
				case 1: System.out.println(db.tilgjengeligeBøker(setning));
						break;
			
				case 2: if (db.lånBok(setning) == true){
							showMessageDialog(null, "Boken er lånt");
						} else {
							showMessageDialog(null, "Noe gikk galt...");
						}
						break;
				
				case 3: if (db.leverBok(setning) == true){
							showMessageDialog(null, "Boken er levert");
						} else {
							showMessageDialog(null, "Noe gikk galt...");
						}
						break;
						
				case 4: db.disconnect(setning, forbindelse);
						ok = false;
						break;
					
				default: System.out.println("Ikke et tilgjengelig valg, prøv igjen");
						 break;
			}
		}
	}
}
