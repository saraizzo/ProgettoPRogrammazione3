import javax.swing.JOptionPane;

public class ComponentDecorator extends Decorator {

	public ComponentDecorator(Sensore sensore) {
		super(sensore);
		
	}
	
	@Override
	public void aggiungi() {
		JOptionPane.showMessageDialog(null, "Componente aggiunto");
	}

	
	

}
