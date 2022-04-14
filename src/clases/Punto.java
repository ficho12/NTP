package clases;

public class Punto implements Comparable<Punto>{

	public long tiempo;
	public boolean posicion; //True = Izq, false = Dch
	
	public Punto(long t, boolean pos) {
		this.tiempo=t;
		this.posicion=pos;
	}
	
	public long getTiempo()
	{
		return this.tiempo;
	}

	public int compareTo(Punto compararPunto) {
	
		long compararTiempo = ((Punto) compararPunto).getTiempo(); 
		
		//ascending order
		return (int) ((int)this.tiempo - compararTiempo);
		
		//descending order
		//return compareQuantity - this.quantity;
		
	}	
	
}
