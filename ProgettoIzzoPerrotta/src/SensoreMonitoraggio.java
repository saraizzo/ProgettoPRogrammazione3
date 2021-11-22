
import java.io.BufferedWriter;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class SensoreMonitoraggio extends Sensore {

	private double max, min;

	private Stato Allarme = Stato.OFF;

	private static ArrayList<Stato> ListaA = new ArrayList<>();

	private float value; 	

	private static int idStatico = 0;

	private static ArrayList<SensoreIntervento> SI = new ArrayList<>();

	public SensoreMonitoraggio(double min, double max, String Tipologia) {
		super("Monitoraggio");
		this.setTipologia(Tipologia);
		this.setState(Stato.ON);
		this.max = max;
		this.min = min;
		this.setId(idStatico);
		SI.add(new SensoreIntervento(this));
		idStatico++;
	}

	public SensoreMonitoraggio() {
		super("Monitoraggio");
	}

	public static int getCount() {
		return idStatico;
	}


	public float getValue(){
		return value;
	}


	public void setValue(float value) {
		this.value = value;
	}


	public void AllarmeOff() {
		this.Allarme = Stato.OFF;
	}


	public Stato getAllarme() {
		return this.Allarme;
	}


	public double getMax() {
		return this.max;
	}


	public double getMin() {
		return this.min;
	}


	public void EliminaAllarme() {
		this.ListaA.remove(this.Allarme);
	}

	public void check() {
		this.value = (float)Math.random()*30; 
		if (this.getValue() > this.max ) {
			GUI.setMode(true);
			GUI.attivato(this.getTipologia());
			this.Allarme = Stato.ON;
			this.ListaA.add(this.Allarme);
			SI.get(this.getId()).setState(Stato.ON);
			System.out.println("valori rilevati troppo alti: " + this.getValue() + "\nStato allarme: " + this.Allarme);
			this.SalvaValori();

			SI.get(this.getId()).Intervento(this);

		}
		else if (this.getValue() < this.min) {
			GUI.setMode(true);
			GUI.attivato(this.getTipologia());
			this.Allarme = Stato.ON;
			this.ListaA.add(this.Allarme);
			SI.get(this.getId()).setState(Stato.ON);
			System.out.println("valori rilevati troppo bassi: " + this.getValue() + "\nStato allarme: " + this.Allarme);
			this.SalvaValori();

			SI.get(this.getId()).Intervento(this);

		}
		else 
			this.SalvaValori();

		if (this.ListaA.indexOf(this.Allarme) != 0) 
			System.out.println("Allarme messo in attesa");
	}


	@Override
	public void SalvaValori () {

		String Sensore = "Sensore " + this.getTipo() + " di " + this.getTipologia().toLowerCase() + " id " + this.getId();
		String statistica = ": stato allarme: " + this.getAllarme().toString() + "; valore rilevato: " + this.getValue() + "; orario rilevamento: " + formatter.format(date) + "\n";
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/saraizzo/eclipse-workspace/ProgettoIzzoPerrotta/file" + this.getId() + ".txt", true));
			bw.write(Sensore + statistica);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void aggiungi() {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void printValues() {
		String s;
		int cont = 0;

			try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/saraizzo/eclipse-workspace/ProgettoIzzoPerrotta/sara" + this.getId() + ".txt"));
			while (true) {
				s = br.readLine();
				if (s == null)
					break;
				System.out.println(s);
			}
			br.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}*/


}
