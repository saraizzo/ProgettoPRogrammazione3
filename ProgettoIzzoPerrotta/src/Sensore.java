
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Sensore {
		private String tipo;//monitoraggio o intervento 
		private Stato state;
		private int id;
		private String Tipologia;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    

		public Sensore(String tipo) {
			this.tipo = tipo;
			this.state = Stato.OFF;
		}
		
		
		public String getTipologia() {
			return this.Tipologia;
		}
		
		public void setTipologia(String Tipologia) {
			this.Tipologia = Tipologia;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public String getTipo() {
			return tipo;
		}

		public String getState() {
			return state.toString();
		}

		public void setState(Stato state) {
			this.state = state;
		}
		
		
		public void SalvaValori() {
			
		};
		
		public abstract void aggiungi();
		


	}
