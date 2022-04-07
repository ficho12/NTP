package servicios;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class Cliente {

	public static void main(String[] args) {
		
		int numServidores = args.length;
		ArrayList<Integer> IP = new ArrayList<Integer>();
		
		if(args.length<2 || args.length>4)
		{
			System.out.println("Error.\n\n"
					+ "Uso: Introducir tantos parametros como IPs se quieran usar\n");
			return; 
		}
		
		for(int i = 0; i < numServidores; i++)
		{
			try {
				IP.add(Integer.parseInt(args[i]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("\n\nError 1: Introduzca IPs válidas, no: " + args[i] +"\n\n"
						+ "Uso: Introducir tantos parametros como IPs se quieran usar\n");
				return;
			}
		}
		
		// Hacer un objeto par
		// Aplicar Marzullo al final
		
		for(int i = 0; i < numServidores; i++)		//Para cada máquina
		{
			Client client=ClientBuilder.newClient();
			URI uri=UriBuilder.fromUri("http://" + IP.get(i) + ":8080/Servidor/").build();	//"http://localhost:8080/Servidor/"
			WebTarget target = client.target(uri);											//"http://" + Ip + ":8080/Servidor/"
			
			for(int j = 0; j<8; j++)				//8 repeticiones
			{
				/*
				 * t0<-tiempo()
					{t1,t2} <- máquina.pedirTiempo() //petición REST
					t3<-tiempo()
					o <- determinarOffset(t0,t1,t2,t3)
					d <- determinarDelay (t0,t1,t2,t3)
					si ( d < mejorPar.d ) mejorPar <- {d,o}
				 */
				
				target.path("Carrera100/pedirTiempo").request(MediaType.TEXT_PLAIN).get(String.class); 


			}
		}
		
		return;
	}
}
