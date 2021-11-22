import java.util.*;

public class ProgettoIzzoPerrotta {
	public static ArrayList<Sensore> SM;
	public static void main(String[] args) {
		SM = new ArrayList<>();		
//		GUI interfacciaGrafica = GUI.getIstance();
		SM.add(new SensoreMonitoraggio(15, 35, "Temperatura"));
		SM.add(new SensoreMonitoraggio(10, 20, "Umidità"));
		GUI interfacciaGrafica = GUI.getIstance();
	
		while(true) {
			System.out.println(GUI.getMode());
			if(interfacciaGrafica.getMode()){
				for(int i = 0; i < SM.size();i++) {
					((SensoreMonitoraggio) SM.get(i)).check();
				}
			}
		}
	
	}
}






