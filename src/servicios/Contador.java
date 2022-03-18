package servicios;

import javax.inject.Singleton;

@Singleton
public class Contador {
	
	private static int c = 0;
	public synchronized static int restart() { c=0; return 0;}
	public synchronized static int increment() { c++; return c;}
	public synchronized static int decrement() { c--; return c;}
	public synchronized static int value() { return c;}
	
}
