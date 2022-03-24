package servicios;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class MainCarrera {

	public static void main(String[] args) {
		
		Client client=ClientBuilder.newClient();
		URI uri=UriBuilder.fromUri("http://localhost:8080/Carrera100m/").build();
		WebTarget target = client.target(uri);
		
		List<Atleta> listaAtletas = new ArrayList<Atleta>();
		int numeroAtletas = 4;		//Pasar por parámetro a reinicio
		
		target.path("Carrera100/reinicio").request(MediaType.TEXT_PLAIN).get(String.class);
		
		for(int i=0;i<numeroAtletas;i++) {
			listaAtletas.add(new Atleta(i));
			listaAtletas.get(i).start();
		}
		
		return;
	}
}
