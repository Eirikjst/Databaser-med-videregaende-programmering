package oving4andresemester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import static javax.swing.JOptionPane.*;

class Klient{
	
	  public static void main(String[] args) throws Exception {
		  String brukernavn = DataLeser.lesTekst("Brukernavn: ");  // DataLeser, se nedenfor
		  String passord = DataLeser.lesPassord();

		  String databasedriver = "com.mysql.jdbc.Driver";
		  Class.forName(databasedriver);  // laster inn driverklassen

		  String databasenavn = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + brukernavn + "?user=" + brukernavn + "&password=" + passord;
		  Connection forbindelse = DriverManager.getConnection(databasenavn);

		  Statement setning = forbindelse.createStatement();
		  
		  boolean noExit = true;
		  int valg = DataLeser.lesHeltall("1: les inn ISBN\n2: lån bok\n3: Lever bok\n4: avslutt");
		  
		  while(noExit){
			  switch(valg){
			  	case 1: String input = DataLeser.lesTekst("ISBN: ");
				  		String sqlsetn1 = "select forfatter, tittel from boktittel where isbn = '"+input+"'";
				  		String sqlsetn2 = "select eks_nr from eksemplar where isbn = '"+input+"'";
				  		System.out.println("Kjører følgende sql setning: "+sqlsetn1+" og "+sqlsetn2);
						ResultSet res1 = setning.executeQuery(sqlsetn1);
			  			while(res1.next()){
			  				String forfatter = res1.getString("forfatter");
							String tittel = res1.getString("tittel");
		  					System.out.println("Forfatter: "+forfatter+", tittel: "+tittel);
		  				}
			  			res1.close();
			  			ResultSet res2 = setning.executeQuery(sqlsetn2);
			  			while(res2.next()){
			  				int eks_nr = res2.getInt("eks_nr");
			  				System.out.println("Antall eksemplarer: "+eks_nr);
			  			}
			  			res2.close();
			  			break;
			  		
			  	case 2: String navn = DataLeser.lesTekst("Navn: ");
			  			String isbn = DataLeser.lesTekst("ISBN: ");
			  			int eksemplarnr = DataLeser.lesHeltall("Eksemplar nummer: ");
			  			String sqlsetn = "update eksemplar set laant_av = '"+navn+"' where isbn = '"+isbn+"' and eks_nr = "+eksemplarnr+" and laant_av is null";
			  			System.out.println("Kjører følgende sql setning: "+sqlsetn);
			  			setning.executeUpdate(sqlsetn);
			  			break;
			  	
			  	case 3: String isbn1 = DataLeser.lesTekst("ISBN: ");
			  			int eksemplarnr1 = DataLeser.lesHeltall("Eksemplar:");
			  			String sqlsetn3 = "update eksemplar set laant_av = null where isbn = '"+isbn1+"' and eks_nr = "+eksemplarnr1;
			  			System.out.println("Kjører følgende sql setning: "+sqlsetn3);
			  			setning.executeUpdate(sqlsetn3);
			  			break;
			  			
			  	case 4: noExit = false;
			  			break;
			  			
			  	default: System.out.println("Noe gikk galt :/");
			  			 break;
			  }
		  }
		  setning.close();
		  forbindelse.close();
	  }
}

class DataLeser {
	  /**
	   * Leser passord fra brukeren.
	   * Teksten "trimmes" før den returneres til klienten.
	   */
	  public static String lesPassord() {
		  JLabel jPassword = new JLabel("Passord: "); // forenklet: http://www.asjava.com/swing/joptionpane-showinputdialog-with-password/
		  JTextField password = new JPasswordField();
		  Object[] obj = {jPassword, password};
		  showConfirmDialog(null, obj, "Please input password for JOptionPane showConfirmDialog", OK_CANCEL_OPTION);
		  return password.getText().trim();
		  }

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