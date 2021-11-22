import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class GUI implements ActionListener {
	private static JFrame frame;
	private static JPanel panel;
	private static JLabel label;
	private static JButton collaudoButton;
	private static JButton attivatoButton;
	private static JButton insertSensoreButton;
	private static JButton addComponentButton;
	private static JButton resetButton;
	private static JButton printValueButton;
	private static Boolean mode; //0 Collaudo 1 Attivato
	private static JLabel allarme;
	private static JLabel[] labels; 
	private static GUI istance;
	private GUI() {
		GUI.setMode(false);
		GUI.frame = new JFrame();

		GUI.panel = new JPanel();

		GUI.panel.setBorder(BorderFactory.createEmptyBorder(150,150,150,150));
		GUI.panel.setLayout(null);
		GUI.panel.setBackground(Color.pink);
		GUI.collaudoButton = new JButton("Collaudo");
		GUI.collaudoButton.addActionListener(this);
		GUI.collaudoButton.setBounds(10,10,90,20);
		GUI.attivatoButton = new JButton("Attivato");
		GUI.attivatoButton.addActionListener(this);
		GUI.attivatoButton.setBounds(10, 40, 90, 20);
		GUI.insertSensoreButton = new JButton("Inserisci sensore");
		GUI.insertSensoreButton.setBounds(10,100,200,20);
		GUI.addComponentButton = new JButton("Aggiungi componente");
		GUI.addComponentButton.setBounds(10, 130, 200, 20);
		GUI.resetButton = new JButton("Resetta tutti i sensori");
		GUI.resetButton.setBounds(250,100,200,20);
		GUI.printValueButton = new JButton("Visualizza gli attuali valori");
		GUI.printValueButton.setBounds(250, 130, 200, 20);
		GUI.insertSensoreButton.addActionListener(this);
		GUI.addComponentButton.addActionListener(this);
		GUI.resetButton.addActionListener(this);
		GUI.printValueButton.addActionListener(this);
		GUI.panel.add(GUI.insertSensoreButton);
		GUI.panel.add(GUI.addComponentButton);
		GUI.panel.add(GUI.resetButton);
		GUI.panel.add(GUI.printValueButton);
		GUI.insertSensoreButton.setVisible(false);
		GUI.addComponentButton.setVisible(false);
		GUI.resetButton.setVisible(false);
		GUI.printValueButton.setVisible(false);
		GUI.label = new JLabel("Modalità:");
		GUI.label.setBounds(130, 25, 120, 20);
		GUI.allarme = new JLabel();
		GUI.allarme.setBounds(250, 25, 350, 20);
		GUI.panel.add(GUI.allarme);
		GUI.allarme.setVisible(false);
		panel.add(GUI.collaudoButton);
		panel.add(GUI.attivatoButton);

		GUI.panel.add(GUI.label);

		GUI.frame.add(GUI.panel, BorderLayout.CENTER);
		GUI.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.frame.setTitle("Sistema Domotico");
		GUI.frame.pack();
		GUI.frame.setSize(1000, 1080);
		GUI.frame.setVisible(true);


	}
	
	public static GUI getIstance() {
		if(istance == null) istance = new GUI();
		return istance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == GUI.collaudoButton) collaudo();
		else if(e.getSource() == GUI.attivatoButton) attivato(null);
		else if(e.getSource() == GUI.insertSensoreButton) {
			try {
				insertSensore();
			} catch (InputOutputException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == GUI.addComponentButton) addComponent();
		else if(e.getSource() == GUI.resetButton) reset();
		else if(e.getSource() == GUI.printValueButton) printValueSensore();

	}

	public static void collaudo() {
		GUI.setMode(false);
		GUI.allarme.setVisible(false);
		GUI.label.setText("Modalità: Collaudo");
		GUI.insertSensoreButton.setVisible(true);
		GUI.addComponentButton.setVisible(true);
		GUI.resetButton.setVisible(true);
		GUI.printValueButton.setVisible(true);
	}

	public static void attivato(String AllarmeAttivo) {
		GUI.setMode(true);
		GUI.label.setText("Modalità: Attivato");
		GUI.allarme.setText("Allarme attivo per il sensore: "+AllarmeAttivo);
		GUI.allarme.setForeground(Color.red);
		GUI.allarme.setVisible(true);
		GUI.insertSensoreButton.setVisible(false);
		GUI.addComponentButton.setVisible(false);
		GUI.resetButton.setVisible(false);
		GUI.printValueButton.setVisible(false);
	}

	public static void insertSensore() throws InputOutputException {
		try {
			String typeSensore = JOptionPane.showInputDialog("Inserisci la tipologia di sensore");
			String minValueString = JOptionPane.showInputDialog("Inserisci il valore minimo");
			String maxValueString  = JOptionPane.showInputDialog("Inserisci il valore massimo");

			float minValue = Float.parseFloat(minValueString);
			float maxValue = Float.parseFloat(maxValueString);
			if(minValue >= maxValue) throw new InputOutputException();
			
			SensoreMonitoraggio newSensore = new SensoreMonitoraggio(minValue, maxValue, typeSensore);
			ProgettoIzzoPerrotta.SM.add(newSensore);
			System.out.println(newSensore.getId()+" "+newSensore.getTipologia()+" "+SensoreMonitoraggio.getCount());
			
		}catch(InputOutputException e){
			JOptionPane.showMessageDialog(null, e,"Eccezione", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void addComponent() {
		try {
			int index = 0;
			Boolean founded = false;
			String typeSensore = JOptionPane.showInputDialog("A quale sensore vuoi aggiungere la componente?");
			/**int i = 0;
			while (ProgettoIzzoPerrotta.SM.get(i).getTipologia().toLowerCase() != typeSensore.toLowerCase()) {
				i++;
				break;
			}**/
			for(int i = 0;i < ProgettoIzzoPerrotta.SM.size();i++) {
				if(ProgettoIzzoPerrotta.SM.get(i).getTipologia().toLowerCase().equals(typeSensore.toLowerCase())) {
					founded = true;
					index = i;
					break;
				}
			}
			if (!founded) throw new InputOutputException(typeSensore);
			String newComponent = JOptionPane.showInputDialog("Inserisci il componente");
			ComponentDecorator Comp = new ComponentDecorator(ProgettoIzzoPerrotta.SM.get(index));
			//ProgettoIzzoPerrotta.SM.get(i).aggiungi();
			Comp.aggiungi();
		}catch(InputOutputException e) {
			JOptionPane.showMessageDialog(null, e,"Eccezione", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void reset() {
		File file;
		for (int i = 0; i < GUI.labels.length; i++) {
			file = new File ("/Users/saraizzo/eclipse-workspace/ProgettoIzzoPerrotta/file" + i + ".txt");
			file.delete();
		}
		removeAllComponents();
	}

	private void removeAllComponents() {
		GUI.panel.removeAll();
		GUI.panel.add(GUI.insertSensoreButton);
		GUI.panel.add(GUI.addComponentButton);
		GUI.panel.add(GUI.resetButton);
		GUI.panel.add(GUI.printValueButton);
		GUI.panel.add(GUI.collaudoButton);
		GUI.panel.add(GUI.attivatoButton);
		GUI.panel.add(GUI.allarme);
		GUI.panel.add(GUI.label);
	}

	public static void printValueSensore() {
		GUI.labels =  new JLabel[SensoreMonitoraggio.getCount()];
		String msg;
		int scost = 0;//scostamento
		JLabel l1  = new JLabel();
		GUI.panel.add(l1);
		l1.setBounds(10, 160, 350, 12);
		try {
			for (int i = 0; i < GUI.labels.length; i++) {
				BufferedReader br = new BufferedReader(
						new FileReader("/Users/saraizzo/eclipse-workspace/ProgettoIzzoPerrotta/file" + i + ".txt"));
				while((msg = br.readLine())!=null) {
					l1.setText("I valori rilevati sono:");
					GUI.labels[i] = new JLabel();
					GUI.labels[i].setText(msg);
					GUI.labels[i].setBounds(10, 180+scost, 1000, 25);
					GUI.panel.add(GUI.labels[i]);
					scost +=30;
				}	
				br.close();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Boolean getMode() {
		return GUI.mode;
	}

	public static void setMode(Boolean mode) {
		GUI.mode = mode;
	}

	public static void setLabelAllarme(String msg) {
		GUI.allarme.setText(msg);
	}
}