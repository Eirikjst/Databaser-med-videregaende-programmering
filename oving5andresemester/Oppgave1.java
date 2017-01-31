package oving5andresemester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel label = new JLabel("Dette er en tekst");
	JButton SansSerif, Serif, Monospaced, Dialog;
	
	public Window(){
		setTitle("Oppgave 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		addButtons();
		add(label);
		pack();
	}
	
	public void addButtons(){
		SansSerif = addButton("SansSerif");
		Serif = addButton("Serif");
		Monospaced = addButton("Monospaced");
		Dialog = addButton("Dialog");
	}
	
	private JButton addButton(String content){
		JButton button = new JButton(content);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().matches("SansSerif")){
					label.setFont(new Font("SansSerif", 0, 20));
				} else if (e.getActionCommand().matches("Serif")){
					label.setFont(new Font("Serif", 0, 20));
				} else if (e.getActionCommand().matches("Monospaced")){
					label.setFont(new Font("Monospaced", 0, 20));
				} else if (e.getActionCommand().matches("Dialog")){
					label.setFont(new Font("Dialog", 0, 20));
				}
			}
		});
		add(button);
		return button;
	}
}

public class Oppgave1 {
	public static void main(String[] args){
		Window window = new Window();
		window.setSize(400, 200);
		window.setVisible(true);
	}
}
