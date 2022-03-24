package servicios;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class Atleta extends Thread {

	Client client=ClientBuilder.newClient();
	URI uri=UriBuilder.fromUri("http://localhost:8080/Carrera100m/").build();
	WebTarget target = client.target(uri);
	
	int dorsal;
	long tiempo;
	
	Atleta(int dorsal){
		
		this.dorsal=dorsal;
		this.tiempo = tarda();
	}
	
	public void run() {
	
			target.path("Carrera100/preparados").request(MediaType.TEXT_PLAIN).get(String.class);
			target.path("Carrera100/listos").request(MediaType.TEXT_PLAIN).get(String.class);
			
			//Duerme el tiempo aleatorio
			try {
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			target.path("Carrera100/llegada").queryParam("dorsal", dorsal).request(MediaType.TEXT_PLAIN).get(String.class);
			target.path("Carrera100/resultados").request(MediaType.TEXT_PLAIN).get(String.class);
	
	}
	
	public long tarda() {
		return  (long)(9.56 + Math.random()*2)*1000;
	}
}
