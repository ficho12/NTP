package servicios;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class Atleta extends Thread {

	Client client=ClientBuilder.newClient();
	String Ip = null;
	WebTarget target;
	
	int dorsal;
	long tiempo;
	
	Atleta(int dorsal,String Ip){
		URI uri = UriBuilder.fromUri("http://" + Ip + ":8080/Carrera100m/").build();
		target  = client.target(uri);
		this.dorsal=dorsal;
		this.tiempo = tarda();
	}
	
	public void run() {
	
			target.path("Carrera100/preparados").request(MediaType.TEXT_PLAIN).get(String.class); //Da error aquí
			target.path("Carrera100/listos").request(MediaType.TEXT_PLAIN).get(String.class);
			
			//Duerme el tiempo aleatorio
			try {
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			target.path("Carrera100/llegada").queryParam("dorsal", this.dorsal).request(MediaType.TEXT_PLAIN).get(String.class);
			target.path("Carrera100/resultados").request(MediaType.TEXT_PLAIN).get(String.class);
	
	}
	
	public long tarda() {
		return  (long)(9.56 + Math.random()*2)*1000;
	}
}
