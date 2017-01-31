package oving5andresemester;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Window1 extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel label = new JLabel("Resultatet av omregningen kommer her");
	private JTextField textfield = new JTextField("Skriv inn ditt beløp her", 20);
	JButton NorskeKroner, SvenskeKroner;
	
	
	public Window1(){
		setTitle("Oppgave 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		add(textfield);
		add(label);
		addButtons();
		pack();
	}
	
	public void addButtons(){
		NorskeKroner = addButton("Til norsk");
		SvenskeKroner = addButton("Til svensk");
	}
	
	private JButton addButton(String content){
		JButton button = new JButton(content);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().matches("Til norsk")){
					label.setText(ValutaOmregning(textfield.getText(), 1.06));
				} else if (e.getActionCommand().matches("Til svensk")){
					label.setText(ValutaOmregning(textfield.getText(), 0.94));
				}
			}
		});
		add(button);
		return button;
	}
	
	private String ValutaOmregning(String input, double kurs){
		String output = "";
		try{
			double valuta = Double.valueOf(input) * kurs;
			String omregnetValuta = Double.toString(valuta);
			output = omregnetValuta;
		} catch (NumberFormatException e){
			String feilmld = "Kan ikke tolke som et tall, prøv igjen";
			output = feilmld;
		}
		return output;
	}
}


public class Oppgave2 {
	public static void main(String[] args){
		Window1 window = new Window1();
		window.setVisible(true);
	}
}