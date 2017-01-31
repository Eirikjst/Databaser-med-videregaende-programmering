package oving6andresemester;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static javax.swing.JOptionPane.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Valuta{
	private String valutaNavn;
	private double kursMotNok;
	private int enhet;

	public Valuta(String valutaNavn, double kursMotNok, int enhet){
		this.valutaNavn = valutaNavn;
		this.kursMotNok = kursMotNok;
		this.enhet = enhet;
	}
	
	public String getNavn(){
		return valutaNavn;
	}
	
	public Double getKursMotNok(){
		return kursMotNok;
	}
	
	public int getEnhet(){
		return enhet;
	}
}

class Window extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private int valgFra;
	private int valgTil;
	@SuppressWarnings("rawtypes")
	private DefaultListModel list = new DefaultListModel();
	private JButton leggTilValuta = new JButton("Legg til ny valuta");
	private JButton omregning = new JButton("Beregn");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JList fraValuta = new JList(list);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JList tilValuta = new JList(list);
	
	Valuta[] valutaliste = { 
		    new Valuta("Euro", 8.10, 1), new Valuta("US Dollar", 6.23, 1), 
		    new Valuta("Britiske pund", 12.27, 1), new Valuta("Svenske kroner", 88.96, 100), 
		    new Valuta("Danske kroner", 108.75, 100), new Valuta("Yen", 5.14, 100),
		    new Valuta("Islandske kroner", 9.16, 100), new Valuta("Norske kroner", 100, 100)
		};

	public Window(){
		setTitle("Valutaberegner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new TekstPanel(), BorderLayout.NORTH);
		add(new ListValuta(), BorderLayout.CENTER);
		add(new Buttons(), BorderLayout.SOUTH);
		pack();
	}
	
	private class Buttons extends JPanel{
		private static final long serialVersionUID = 1L;
		public Buttons(){
			setLayout(new BorderLayout());
			add(leggTilValuta, BorderLayout.NORTH);
			leggTilValuta.addActionListener(new ButtonListener());
			add(omregning, BorderLayout.SOUTH);
			omregning.addActionListener(new ButtonListener());
		}
	}
	
	private class TekstPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		public TekstPanel(){
			setLayout(new BorderLayout());
			add(new Label("Velg fra valuta til valuta"), BorderLayout.NORTH);
			add(new Label("Fra valuta"), BorderLayout.WEST);
			add(new Label("Til valuta"), BorderLayout.EAST);
		}
	}
	
	private class ListValuta extends JPanel{
		private static final long serialVersionUID = 1L;
		public ListValuta(){
			addList();
			setLayout(new GridLayout());
			fraValuta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollFra = new JScrollPane(fraValuta);
			fraValuta.addListSelectionListener(new ListListenerFra());
			tilValuta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollTil = new JScrollPane(tilValuta);
			tilValuta.addListSelectionListener(new ListListenerTil());
			add(scrollFra, BorderLayout.WEST);
			add(scrollTil, BorderLayout.EAST);
		}
	}
	
	private class ListListenerFra implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e){
			valgFra = fraValuta.getSelectedIndex();
		}
	}
	
	private class ListListenerTil implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e){
			valgTil = tilValuta.getSelectedIndex();
		}
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().matches("Legg til ny valuta")){
				if (leggTilValuta() == true){
					showMessageDialog(null, "Valuta lagt til");
				} else {
					showMessageDialog(null, "Valutaen er allerede lagt til");
				}
			} else if (e.getActionCommand().matches("Beregn")){
				double beløp = DataLeser.lesHeltall("Skriv inn beløp");
				showMessageDialog(null, beregnValuta(beløp));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addList(){
		String[] navn = new String[valutaliste.length];
		for (int i = 0; i < valutaliste.length; i++){
			navn[i] = valutaliste[i].getNavn();
		}
		for(int i = 0; i < navn.length; i++){
			if(valutaliste[i] != null){
				list.addElement(navn[i]);
			}
		}
	}
	
	private String beregnValuta(double beløp){
		int indeks1 = valgFra;
		int indeks2 = valgTil;
		double output = beløp * ((valutaliste[indeks1].getKursMotNok() / (double)valutaliste[indeks1].getEnhet()) / (valutaliste[indeks2].getKursMotNok() / (double)valutaliste[indeks2].getEnhet()));
		return beløp+" "+valutaliste[indeks1].getNavn()+" tilsvarer "+output+" "+valutaliste[indeks2].getNavn()+".";
	}
		
	@SuppressWarnings("unchecked")
	private boolean leggTilValuta(){
		String navn = DataLeser.lesTekst("Navn på valuta: ");
		for(int i = 0; i < valutaliste.length; i++){
			if(valutaliste[i].getNavn().matches(navn)){
				return false;
			}
		}
		double kurs = DataLeser.lesHeltall("Kurs på valuta mot NOK: ");
		int enhet = DataLeser.lesHeltall("Enhet på valuta(1 eller 100)");
		for(int i = 0; i < valutaliste.length; i++){
			if(valutaliste[i] != null){
				utvidTabell(valutaliste);
			}
		}
		int indeks = 0;
		for(int i = 0; i < valutaliste.length; i++){
			if (valutaliste[i] == null){
				valutaliste[i] = new Valuta(navn, kurs, enhet);
				indeks = i;
			}
		}
		list.addElement(valutaliste[indeks].getNavn());
		return true;
	}
	
	private void utvidTabell(Valuta[] gammelTabell){
		Valuta[] nyTabell = new Valuta[gammelTabell.length + 5];
		for (int i = 0; i < gammelTabell.length; i++){
			nyTabell[i] = gammelTabell[i];
		}
		valutaliste = nyTabell;
	}
}

class DataLeser{
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

public class OmregningValuta {
	public static void main(String[] args){
		Window window = new Window();
		window.setSize(250, 400);
		window.setVisible(true);
	}
}