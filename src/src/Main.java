package src;

public class Main {

	public static void main(String[] args) {
		Display display = new Display(800,600);
		display.init();
		while(true){
			display.update();
		}
	}

}
