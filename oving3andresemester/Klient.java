package oving3andresemester;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Tidspunkt.java
 *
 * Klasse som håndterer tidspunkt gitt som et heltall av typen long.
 *
 * NB! Konstruktøren foretar ingen kontroll av om tidspunktet er gyldig!
 */

class Tidspunkt implements Comparable<Tidspunkt> {
  private final long tid; // format ååååmmddttmm

  public Tidspunkt(long tid) {
    this.tid = tid;
  }

  public long getTidspunkt() {
    return tid;
  }

  /**
   * Formaterer tidspunktet slik: dd-mm-åååå kl ttmm
   */
  public String toString() {
    /*
     * Foretar trygg omforming til mindre type,
     * dato og klokkeslett er hver for seg innenfor tallområdet til int.
      */
    int dato = (int) (tid / 10000);
    int klokkeslett = (int) (tid % 10000);
    int år = dato / 10000;
    int mndDag = dato % 10000;
    int mnd = mndDag / 100;
    int dag = mndDag % 100;

    String tid = "";
    if (dag < 10) {
      tid += "0";
    }
    tid += dag + "-";
    if (mnd < 10) {
      tid += "0";
    }
    tid += mnd + "-" + år + " kl ";
    if (klokkeslett < 1000) {
      tid += "0";
    }
    tid += klokkeslett;
    return tid;
  }

  public int compareTo(Tidspunkt detAndre) {
    if (tid < detAndre.tid) {
      return -1;
    } else if (tid > detAndre.tid) {
      return 1;
    } else {
      return 0;
    }
  }

  /* Tester klassen Tidspunkt */
  public static void main(String[] args) {
    System.out.println("Totalt antall tester: 2");
    Tidspunkt t1 = new Tidspunkt(200301201200L);
    Tidspunkt t2 = new Tidspunkt(200301070230L);
    Tidspunkt t3 = new Tidspunkt(200301070230L);
    if (t1.compareTo(t2) > 0 &&
        t2.compareTo(t1) < 0 &&
        t3.compareTo(t2) == 0 &&
        t2.compareTo(t3) == 0) {
          System.out.println("Tidspunkt: Test 1 vellykket.");
    }
    if (t1.toString().equals("20-01-2003 kl 1200") &&
        t2.toString().equals("07-01-2003 kl 0230") &&
        t3.toString().equals("07-01-2003 kl 0230")) {
          System.out.println("Tidspunkt: Test 2 vellykket.");
    }
  }
}

/**
 * Kunde.java
 * Inneholder kundedata.
 */
class Kunde {
  private final String navn;
  private final String tlf;

/**
 * Konstruktør:
 * Både navn og telefon må oppgis, de kan ikke være verken null eller tomme strenger.
 */
  public Kunde(String navn, String tlf) {
    if (navn == null || navn.trim().equals("")) {
      throw new IllegalArgumentException("Navn må oppgis.");
    }
    if (tlf == null || tlf.trim().equals("")) {
      throw new IllegalArgumentException("Tlf må oppgis.");
    }
    this.navn = navn.trim();
    this.tlf = tlf.trim();
  }

  public String getNavn() {
    return navn;
  }

  public String getTlf() {
    return tlf;
  }

  public String toString() {
    return navn + ", tlf " + tlf;
  }
}

/**
 * Resevasjon.java
 *
 * Et objekt inneholder data om en reservasjon.
 * Operasjoner for å hente ut data, og for å sjekke om overlapp
 * med annen reservasjon.
 */

class Reservasjon {
  private final Tidspunkt fraTid;
  private final Tidspunkt tilTid;
  private final Kunde kunde;

  /**
   * Konstruktør:
   * fraTid må være før tilTid.
   * Ingen av argumentene kan være null.
   */
  public Reservasjon(Tidspunkt fraTid, Tidspunkt tilTid, Kunde kunde) {
    if (fraTid == null || tilTid == null) {
      throw new IllegalArgumentException("Fra-tid og/eller til-tid er null");
    }
    if (fraTid.compareTo(tilTid) >= 0) {
      throw new IllegalArgumentException("Fra-tid er lik eller etter til-tid");
    }
    if (kunde == null) {
      throw new IllegalArgumentException("Kunde er null");
    }
    this.fraTid = fraTid;
    this.tilTid = tilTid;
    this.kunde = kunde;
  }

  public Tidspunkt getFraTid() {
    return fraTid;
  }

  public Tidspunkt getTilTid() {
    return tilTid;
  }

  /**
   * Metoden returnerer true dersom tidsintervallet [sjekkFraTid, sjekkTilTid] overlapper
   * med det tidsintervallet som er i det reservasjonsobjektet vi er inne i [fraTid, tilTid].
   * Overlapp er ikke definert hvis sjekkFraTid eller sjekkTilTid er null.
   * Da kaster metoden NullPointerException.
   */
  public boolean overlapp(Tidspunkt sjekkFraTid, Tidspunkt sjekkTilTid) {
    return (sjekkTilTid.compareTo(fraTid) > 0 && sjekkFraTid.compareTo(tilTid) < 0);
  }

  public String toString() {
    return "Kunde: " + kunde.getNavn() + ", tlf: " + kunde.getTlf() + ", fra " +
                       fraTid.toString() + ", til " + tilTid.toString();
  }

  /**
   * Metode som prøver klassen Reservasjon.
   */
  public static void main(String[] args) {
    Kunde k = new Kunde("Anne Hansen", "12345678");
    System.out.println("Totalt antall tester: ");
    Reservasjon r1 = new Reservasjon(new Tidspunkt(200302011000L), new Tidspunkt(200302011100L), k);
    Reservasjon r2 = new Reservasjon(new Tidspunkt(200301211000L), new Tidspunkt(200301211030L), k);
    Reservasjon r3 = new Reservasjon(new Tidspunkt(200302011130L), new Tidspunkt(200302011300L), k);
    Reservasjon r4 = new Reservasjon(new Tidspunkt(200302010900L), new Tidspunkt(200302011100L), k);
    if (r1.toString().equals("Kunde: Anne Hansen, tlf: 12345678, fra 01-02-2003 kl 1000, til 01-02-2003 kl 1100") &&
        r2.toString().equals("Kunde: Anne Hansen, tlf: 12345678, fra 21-01-2003 kl 1000, til 21-01-2003 kl 1030") &&
        r3.toString().equals("Kunde: Anne Hansen, tlf: 12345678, fra 01-02-2003 kl 1130, til 01-02-2003 kl 1300") &&
        r4.toString().equals("Kunde: Anne Hansen, tlf: 12345678, fra 01-02-2003 kl 0900, til 01-02-2003 kl 1100")) {
          System.out.println("Reservasjon: Test 1 vellykket.");
    }

    if (r1.overlapp(new Tidspunkt(200302011000L), new Tidspunkt(200302011100L)) &&
       !r1.overlapp(new Tidspunkt(200302021000L), new Tidspunkt(200302021100L)) &&
        r1.overlapp(new Tidspunkt(200302011030L), new Tidspunkt(200302011100L)) &&
        r1.overlapp(new Tidspunkt(200302010930L), new Tidspunkt(200302011030L))) {
         System.out.println("Reservasjon: Test 2 vellykket.");
    }
    // Flg. setning kaster exception (fra-tid lik til-tid)
    //Reservasjon r5 = new Reservasjon(new Tidspunkt(200302011100L), new Tidspunkt(200302011100L), k);
    // Flg. setning kaster exception (fra-tid > til-tid)
    //Reservasjon r5 = new Reservasjon(new Tidspunkt(200302011130L), new Tidspunkt(200302011100L), k);
  }
}

class Konferansesenter{
	
	private ArrayList<Rom> rom = new ArrayList<Rom>();

	public boolean reserverRom(long fraTid, long tilTid, int antallPersoner, String navn, String tlfNummer){
		
		for (int i = 0; i < rom.size(); i++){
			if(rom.get(i).getRomstørrelse() >= antallPersoner){
				if(rom.get(i).ledigTidspunkt(fraTid, tilTid)== false){
					return false;
				}
				if (rom.get(i).reserver(fraTid, tilTid, antallPersoner, navn, tlfNummer) == true){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean regNyttRom(int romnummer, int antallpersoner){
		for(int i = 0; i < rom.size(); i++){
			if (rom.get(i).getRomnummer() == romnummer){
				return false;
			}
		}
		rom.add(new Rom(romnummer, antallpersoner));
		return true;
	}
	
	public int antallRom(){
		return rom.size();
	}
	
	public String informasjonIndeks(int indeks){
		return rom.get(indeks).toString();
	}
	
	public String informasjonRomnr(int nummer){
		String output = "";
		int nr = 0;
		for (int i = 0; i < rom.size(); i++){
			if (rom.get(i).getRomnummer() == nummer){
				output += rom.toString();
				nr = i;
			}
		}
		return "Størrelse "+ rom.get(nr).getRomstørrelse() + output;
	}
	
	public int antallReservasjoner(){
		int output = 0;
		for(int i = 0; i < rom.size(); i++){
			output += rom.get(i).antallReservasjoner();
		}
		return output;
	}
	
	public boolean romstørrelse(int antallPersoner){
		for (int i = 0; i < rom.size(); i++){
			if (rom.get(i).getRomstørrelse() > antallPersoner){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		String output = "";
		
		for (int i = 0; i < rom.size(); i++){
			output += "Romnummer:" + rom.get(i).getRomnummer() + ", størrelse: " + rom.get(i).getRomstørrelse() + ".\n"; 
		}
		
		return output;
	}
}

class Rom{
	private ArrayList<Reservasjon>reservasjon = new ArrayList<Reservasjon>();
	private int romNummer;
	private int romStørrelse;
	
	public Rom(int romNummer, int romStørrelse){
		this.romNummer = romNummer;
		this.romStørrelse = romStørrelse;
	}
	
	public int getRomnummer(){
		return romNummer;
	}
	
	public int getRomstørrelse(){
		return romStørrelse;
	}
	
	public boolean reserver(long fraTid, long tilTid, int antallPersoner, String navn, String tlfNummer){
		Tidspunkt fra = new Tidspunkt(fraTid);
		Tidspunkt til = new Tidspunkt(tilTid);
		Kunde k = new Kunde(navn, tlfNummer);
		Reservasjon r = new Reservasjon(fra, til, k);
		reservasjon.add(r);
		return true;
	}

	public boolean ledigTidspunkt(long fraTid, long tilTid){
		Tidspunkt fra = new Tidspunkt(fraTid);
		Tidspunkt til = new Tidspunkt(tilTid);
		for (int i = 0; i < reservasjon.size(); i++){
			if(reservasjon.get(i).overlapp(fra, til) == true){
				return false;
			}
		}
		return true;
	}
	
	public int antallReservasjoner(){
		int output = 0;
		for (int i = 0; i < reservasjon.size(); i++){
			if(reservasjon.get(i) != null){
				output++;
			}
		}
		return output;
	}
	
	public String informasjonRomnr(int nummer){
		return reservasjon.toString();
	}
	
	public String toString(){
		String output = "";
		
		output += "\n" + reservasjon.toString();
		
		for (int i = 0; i < reservasjon.size(); i++){
			if(reservasjon.get(i) != null){
				output += "\n" + reservasjon.toString();
			}
		}
		return output;
	}
	
	public static void main(String[] args){
		Rom rom = new Rom(500, 20);
		
		long fra1 = 201701251000L; // 2017-01-25 kl 1000
		long fra3 = 201701251300L; // 2017-01-25 kl 1300
		long til1 = 201701251200L; // 2017-01-25 kl 1200
		long til3 = 201701251600L; // 2017-01-25 kl 1600
		
		rom.reserver(201701251000L, 201701251200L, 10, "Per", "123");
		
		if (rom.ledigTidspunkt(fra1, til1) == false){
			System.out.println("Rommet er opptatt ved dette tidspunktet");
		}
		
		if (rom.ledigTidspunkt(fra3, til3) == true){
			System.out.println("Rommet er ledig ved dette tidspunktet");
		}
		
		System.out.println("Antall reservasjoner " + rom.antallReservasjoner());
		
		if (rom.reserver(fra1, til1, 5, "knut", "678") == true && rom.ledigTidspunkt(fra1, til1) == false){
			System.out.println("Rommet er reservert ved dette tidspunktet");
		}
		if (rom.reserver(fra3, til3, 10, "Ole", "789")){
			System.out.println("Rommet er reservert");
		}
		System.out.println("Antall reservasjoner " + rom.antallReservasjoner());
	}
}

class Input{
    public Input(){
    }
    
    @SuppressWarnings("resource")
	public int getInt(String dialog){
        
        int inputTall = 0;
        System.out.println(dialog);
        Scanner sc = new Scanner(System.in);
	    inputTall = sc.nextInt();
        return inputTall;
    }
    
    @SuppressWarnings("resource")
	public long getLong(String dialog){
        
        long inputTall = 0;
        System.out.println(dialog);
        Scanner sc = new Scanner(System.in);
        inputTall = sc.nextLong();
        return inputTall;
    }
    
    @SuppressWarnings("resource")
    public String getString(String dialog){
    
        String inputString = "";
		System.out.println(dialog);
		Scanner sc = new Scanner(System.in);
		inputString = sc.nextLine();

		return inputString;
    }
}

public class Klient {
	public static void main(String[] args){
		
		Konferansesenter ks = new Konferansesenter();
		Input in = new Input();
		
		ks.regNyttRom(100, 10);
		ks.regNyttRom(101, 10);
		ks.regNyttRom(102, 20);
		ks.regNyttRom(103, 20);
		ks.regNyttRom(104, 40);
		ks.regNyttRom(105, 40);
		ks.regNyttRom(106, 60);
		ks.regNyttRom(107, 60);
		ks.regNyttRom(108, 80);
		
		String valg = "1: Registrer nytt rom "+
					  "\n2: Antall reservasjoner"+
					  "\n3: Reserver rom"+
					  "\n4: Informasjon om alle registrerte kunder (indeks)"+
					  "\n5: Informasjon om alle registrerte kunder (romnr)"+
					  "\n6: Avslutt program";
		
		boolean noExit = true;
		
		while(noExit != false){
			switch(in.getInt(valg)){
			
				case 1: int romnr = in.getInt("Romnummer: ");
						int størrelse = in.getInt("Størrelse på rommet");
						ks.regNyttRom(romnr, størrelse);
						break;
						
				case 2: System.out.println("Antall reservasjoner: " + ks.antallReservasjoner());
						break;
						
				case 3: String navn = in.getString("Navn: ");
						String tlfnummer = in.getString("Telefon nummer: ");
						boolean noExit2 = true;
						while (noExit2){
							int antallPersoner = in.getInt("Antall personer: ");
							if (ks.romstørrelse(antallPersoner) == false){
								System.out.println("Ingen rom med plass til så mange personer.");
								continue;
							} else {
								
								boolean ok = false;
								do{
									try{
										long tidFra = in.getLong("Ønsket reservasjon tidspunkt fra(åååå-mm-dd kl ttmm): ");
										long tidTil = in.getLong("Til (åååå-mm-dd kl ttmm): ");
										
										if (ks.reserverRom(tidFra, tidTil, antallPersoner, navn, tlfnummer) == true){
											System.out.println("Ditt rom er reservert");
											ok = true;
										} else {
											System.out.println("Noe gikk galt under reservasjonen, prøv igjen");
										}
									} catch (IllegalArgumentException e){
										System.out.println(e);
										continue;
									}
										ok = true;
									} while (!ok);
								
								noExit2 = false;
								}
						}
						break;
						
				case 4: int indeks = in.getInt("Indeks: ");
						System.out.println(ks.informasjonIndeks(indeks));
						break;
						
				case 5: int romnrinfo = in.getInt("Romnr: ");
						System.out.println(ks.informasjonRomnr(romnrinfo));
						break;
						
				case 6: noExit = false;
						break;
						
				default: System.out.println("Ikke et valg på listen, prøv igjen");
						 break;
			}
		}
		
		System.out.println(ks.toString());
		
	}
}