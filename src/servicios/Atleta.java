package servicios;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
			target.path("Carrera100/preparados");
			System.out.println("Atleta Preparado");
			target.path("Carrera100/listos");
			System.out.println("Atleta Listo");
			
			//Duerme el tiempo aleatorio
			try {
				Thread.sleep((int)tiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ArrayList<String> lista = new ArrayList<String>();
			lista.add(""+dorsal);
			lista.add(""+tiempo/1000);
			target.path("Carrera100/llegada").queryParam("item", lista);
			System.out.println("Atleta Llegó");
	
	}
	
	public float tarda() {
		return (float) (9.56 + Math.random()*2);
	}
}
