package oving1andresemester;

import java.util.Scanner;
import java.time.*;

class Bord {
	
	private String[] bord;
	
	public Bord(int antallBord){
		this.bord = new String[antallBord];
	}
	
	public int ledigeBord(){
		int output = 0;
		for(int i = 0; i < bord.length; i++){
			if(bord[i] == null){
				output++;
			}
		}
		return output;
	}
	
	public int antallReserverteBord(){
		int output = 0;
		for(int i = 0; i < bord.length; i++){
			if(bord[i] != null){
				output++;
			}
		}
		return output;
	}
	
	public boolean fjernReservasjon(int bordNr){
		bord[bordNr] = null;
		return true;
	}
	
	public boolean reserverBord(int bordNr, String navn){
		if (opptattBord(bordNr) == true) return false;
		bord[bordNr] = navn;
		return true;
	}
	
	public String navnReservert(int nr){
		return bord[nr];
	}
	
	public int antallBord(){
		return bord.length;
	}
	
	public boolean opptattBord(int opptatt){
		return (bord[opptatt] != null);
	}
}

class Restaurant {

	Bord bord;
	private String navn;
	private int etableringsar;
	
	public Restaurant(String navn, int etableringsar, int antallBord){
		this.bord = new Bord(antallBord);
		this.navn = navn;
		this.etableringsar = etableringsar;
	}
	
	public String getNavnRestaurant(){
		return navn;
	}
	
	public String setNavnRestaurant(String nyttNavn){
		return this.navn = nyttNavn;
	}
	
	public int getEtableringsar(){
		return etableringsar;
	}
	
	public int getAlderRestaurant(){
		LocalDate dato = LocalDate.now();
		LocalDate etablert = LocalDate.of(etableringsar, 1, 5);
		
		long dagerSidenEtablert = dato.toEpochDay() - etablert.toEpochDay();

		return (int) (dagerSidenEtablert/365);
	}
	
	public int getAntallReserverteBord(){
		return bord.antallReserverteBord();
	}
	
	public int getAntallLedigeBord(){
		return bord.ledigeBord();
	}
	
	public boolean reserverBord(String navn, int antallBord, int bordNr){
		
		if (antallBord > bord.ledigeBord()){
			return false;
		}
		
		if (bord.opptattBord(bordNr) == true){
			return false;
		}
		
		for (int i = 0; i <= antallBord; i++){
			bord.reserverBord(bordNr, navn);
		}
		return true;
	}
	
	public boolean plass(int antallBord){
		if(antallBord > getAntallLedigeBord()){
			return false;
		} else {
			return true;
		}
	}	
	
	public boolean fjern(int nr){
		bord.fjernReservasjon(nr);
		return true;
	}
	
	public int[] finnReservasjoner(String navn){
		int[] antallBord = new int[0];
		for (int i = 0; i < bord.antallBord(); i++){
			if(navn.equals(bord.navnReservert(i))){
				antallBord = utvidTabell(antallBord);
				antallBord[antallBord.length - 1] = i;		
			}
		}
		return antallBord;
		
	}
	
	public boolean opptattBord(int opptatt){
		return bord.opptattBord(opptatt);
	}
	
	private int[] utvidTabell(int[] gammelTabell){
		int[] nyTabell = new int[gammelTabell.length +1];
		
		for(int i = 0; i <gammelTabell.length; i++){
			nyTabell[i] = gammelTabell[i];
		}
		return nyTabell;
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
    public String getString(String dialog){
    
        String inputString = "";
		System.out.println(dialog);
		Scanner sc = new Scanner(System.in);
		inputString = sc.nextLine();

		return inputString;
    }
}

class Klient{
	public static void main(String[] args){
	
		Input input = new Input();
		Restaurant restaurant = new Restaurant("Enstjerners middag", 1995, 15);
		
		boolean noExit = true;
		int meny;
		
		String valg = "1: Navn på resturant" + "\n" +
					  "2: Skift navn på resturant" + "\n" +
					  "3: Året resturanten er etabablert, og alder på restauranten" + "\n" +
					  "4: Antall ledige- og reserverte bord" + "\n" +
					  "5: Reserver et bestemt antall bord" + "\n" +
					  "6: Hvilken bord har en person reservert" + "\n" + 
					  "7: Fjern reservasjoner" + "\n" +
					  "8: Avslutt";
		
		System.out.println("Velkommen til restauranten Enstjerners middag\n");	
		
		while(noExit){
			
			meny = input.getInt(valg);
			
			switch(meny){
				case 1: System.out.println("Navn på restaurant: " + restaurant.getNavnRestaurant()+"\n");
						break;
						
				case 2: String velgnyttnavn = input.getString("Hvilket navn vil du bytte til?");
						restaurant.setNavnRestaurant(velgnyttnavn);
						System.out.println("");
						break;
						
				case 3: System.out.println("Restauranten ble etablert i år: "+restaurant.getEtableringsar()+" og er " + restaurant.getAlderRestaurant()+" år gammel.");
						break;
						
				case 4: System.out.println("Antall ledige bord er "+restaurant.getAntallLedigeBord()+", og antall reserverte bord er "+restaurant.getAntallReserverteBord()+"\n");
						break;
						
				case 5: String navn = input.getString("Navn: ");
						int antallBord = input.getInt("Antall bord du vil reservere");
						if(restaurant.plass(antallBord) == false){
							System.out.println("Det er ikke så mange ledige bord, prøv igjen");
						} else {
							int teller = antallBord;
							for (int i = 0; i < teller; i++){
								int bordNr = input.getInt("Velg bordnummer du vil reservere");
								if(restaurant.opptattBord(bordNr) == true){
									System.out.println("Bord opptatt, velg et annet.");
									teller++;
								}
								restaurant.reserverBord(navn, antallBord, bordNr);
							}
						}
							
						break;
						
				case 6: String navn1 = input.getString("Navnet på den som har reservert");
						int[] output = restaurant.finnReservasjoner(navn1);
						System.out.println(navn1 +" har reservert ");
						for (int i = 0; i < output.length; i++){
							System.out.println(output[i]);
						}
						break;
						
				case 7: int antall = input.getInt("Hvor mange bord vil du fjerne fra listen?");
						int[] fjern = new int[antall];
						
						for (int i = 0; i < fjern.length; i++){
							int indeks = input.getInt("Hvilken bord vil du fjerne fra listen?");
							restaurant.fjern(indeks);
						}
						
						break;
						
				case 8: noExit = false;
						break;
						
				default: System.out.println("Ikke gyldig operasjon\n");
						 break;
			}
		}
	}
}