package servicios;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class IniciarAtleta {
/*
 *	Uso: 1er parametro: Ip destino del arbitro de la carrera (Solo la Ip de la máquina no la Uri ni el puerto)
 *	2o parametro: Nº de atletas a correr en está maquina
 *	3er parametro(opcional): Introducir una r si se desea reiniciar el servidor antes de crear los atletas
 *	4o parametro(opcional): Si se quiere reiniciar la carrera si tiene que especificar el numero de atletas
 *	participantes en este parametro
 *	Ejemplos de ejecucion: 
 *	192.168.0.2 2 r 4
 *	localhost 4
 */

	public static void main(String[] args) {
		
		int numAtletas;

		if(args.length<2 || args.length>4)
		{
			System.out.println("Error.\n\n"
					+ "Uso: 1er parametro: Ip destino del arbitro de la carrera (Solo la Ip de la máquina no la Uri ni el puerto)\n"
					+ "2o parametro: Nº de atletas a correr en está maquina\n"
					+ "3er parametro(opcional): Introducir una r si se desea reiniciar el servidor antes de crear los atletas\n"
					+ "4o parametro(opcional): Si se quiere reiniciar la carrera si tiene que especificar el numero de atletas participantes en este parametro\n"
					+ "Ejemplos de ejecucion: 192.168.0.2 2 r 4\n"
					+ "localhost 4\n\n");
			return; 
		}else if(args.length >=2)
		{
			
			Client client=ClientBuilder.newClient();
			URI uri=UriBuilder.fromUri("http://" + args[0] + ":8080/Carrera100m/").build();	//"http://localhost:8080/Carrera100m/"
			WebTarget target = client.target(uri);											//"http://" + Ip + ":8080/Carrera100m/"
			
			List<Atleta> listaAtletas = new ArrayList<Atleta>();
			int numeroAtletas;
			
			try {
				numeroAtletas = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("\n\nError 1: Introduzca un número correcto de atletas, no: " + args[1] +"\n\n");
				return;
			}
						
			if(args.length >2)
			{
				if(args[2].equals("r"))
				{
					numAtletas = Integer.parseInt(args[3]);
					try {
						target.path("Carrera100/reinicio").queryParam("numAtletas", numAtletas).request(MediaType.TEXT_PLAIN).get(String.class);
						//target.path("Carrera100/llegada").queryParam("dorsal", this.dorsal).request(MediaType.TEXT_PLAIN).get(String.class);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("\n\nError 2: Introduzca un número correcto de atletas máximos para el reinicio, no: " +numAtletas + " " + args[3] +"\n\n");
						return;
					}
				}
			}
			
			for(int i=0;i<numeroAtletas;i++) {
				listaAtletas.add(new Atleta(args[0]));
				listaAtletas.get(i).start();
			}
		}
		
		return;
	}
}
