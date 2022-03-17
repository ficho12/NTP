package servicios;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

public class Atleta extends Thread {

	Client client=ClientBuilder.newClient();
	URI uri=UriBuilder.fromUri("http://localhost:8080/Carrera100m").build();
	WebTarget target = client.target(uri);
	
	int dorsal;
	float tiempo;
	
	Atleta(int dorsal){
		
		this.dorsal=dorsal;
		this.tiempo = tarda()*1000;
	}
	
	public void run() {
		
			System.out.println("Atleta Creado");
			target.path("Carrera100/preparado");
			target.path("Carrera100/listos");
			//Duerme el tiempo aleatorio
			try {
				Thread.sleep((int)tiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			target.path("Carrera100/llegada").queryParam("num", ""+dorsal +tiempo);
	
	}
	
	public float tarda() {
		return (float) (9.56 + Math.random()*2);
	}
}
