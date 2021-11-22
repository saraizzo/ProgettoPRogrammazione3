public class InputOutputException extends Exception {
	public InputOutputException() {
		super("minimo e massimo non validi!!");
	}
	public InputOutputException(String sensore) {
		super("Sensore "+sensore+" non presente!!");
	}
}
