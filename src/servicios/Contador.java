package servicios;

public class Contador {
	
	private static int c = 0;
	public synchronized static void restart() { c=0; }
	public synchronized static void increment() { c++; }
	public synchronized static void decrement() { c--; }
	public synchronized static int value() { return c; }
	
}
