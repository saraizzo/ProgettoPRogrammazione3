
public abstract class Decorator extends Sensore{
	
	protected Sensore sensore;

	public Decorator(Sensore sensore) {
		super(sensore.getTipo());
		this.sensore = sensore;
		
	}

	@Override
	public void aggiungi(){
		this.sensore.aggiungi();
	}
}
