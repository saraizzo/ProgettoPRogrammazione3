

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SensoreIntervento extends Sensore{


	public SensoreIntervento(SensoreMonitoraggio s) {
		super("Intervento");
		this.setTipologia (s.getTipologia());
		this.setId(s.getId());
		this.setState(Stato.OFF);
	}

	public void Intervento(SensoreMonitoraggio s) {
		Queue<SensoreMonitoraggio> queueAllarme = new LinkedList<>();
		queueAllarme.add(s);
		Thread tCurr = Thread.currentThread();
		tCurr.setName("Thread Principale");
		this.setState(Stato.ON);
		this.SalvaValori();
		GUI.setLabelAllarme("L'allarme "+queueAllarme.peek().getTipologia()+" sta rientrando attendere..");
		if (queueAllarme.peek().getValue() > queueAllarme.peek().getMax()) {
			while (queueAllarme.peek().getValue() > (int)queueAllarme.peek().getMax()) {
				queueAllarme.peek().setValue(queueAllarme.peek().getValue() - 1);
				try {
					tCurr.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else if (queueAllarme.peek().getValue() < queueAllarme.peek().getMin()) {
			while (queueAllarme.peek().getValue() < queueAllarme.peek().getMin()) {
				queueAllarme.peek().setValue(queueAllarme.peek().getValue() + 1);
				try {
					tCurr.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		GUI.setLabelAllarme("Allarme "+queueAllarme.peek().getTipologia()+" rientrato");
		try {
			tCurr.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		queueAllarme.peek().AllarmeOff();
		queueAllarme.peek().EliminaAllarme();
		queueAllarme.remove();
		this.setState(Stato.OFF);
		System.out.println("Valori riportati a livelli sicuri: " + s.getValue() + "\nStato allarme: " + s.getAllarme());
	}

	public void reset(ArrayList<SensoreMonitoraggio> a) {
		SensoreMonitoraggio S = a.get(this.getId());
		S.setValue(0);
		S.setState(Stato.OFF);
		System.out.println("il sensore con id " + S.getId() + " di " + S.getTipo() + " è stato resettato e spento" 
				);
	}


	@Override
	public void SalvaValori () {
		String sensore = "Sensore " + this.getTipo() + " di " + this.getTipologia().toLowerCase() +  " è " +this.getState() + " id " + this.getId() + "; orario: " + formatter.format(date) + "\n";
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/saraizzo/eclipse-workspace/ProgettoIzzoPerrotta/file" + this.getId() + ".txt", true));
			bw.write(sensore);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void aggiungi() {
		
	}


	/*@Override
	public void printValues() {
		String s;
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
