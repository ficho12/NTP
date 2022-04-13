package servicios;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import clases.Par;

public class Cliente {

	public static void main(String[] args) {
		
		int numServidores = args.length;
		ArrayList<String> IP = new ArrayList<String>();
		long t0=0,t1=0,t2=0,t3=0;
		String respuesta;
		String separado[];
		ArrayList<Par> pares;
		
		if(args.length<1 || args.length>3)
		{
			System.out.println("Error.\n\n"
					+"Uso: Introducir tantos parametros como IPs se quieran usar\n");
			return; 
		}
		
		for(int i = 0; i < numServidores; i++)
		{
			try {
				IP.add(args[i]);
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
			System.out.println(IP.get(i));
			URI uri=UriBuilder.fromUri("http://" + IP.get(i) + ":8080/NTP/").build();	//"http://localhost:8080/NTP/"
			WebTarget target = client.target(uri);										//"http://" + Ip + ":8080/Servidor/"
			
			pares = new ArrayList<Par>();
			Par mejorPar=null;
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
				mejorPar = new Par(9999,9999);
				
				t0 = System.nanoTime();

				respuesta = target.path("Servidor/pedirTiempo").request(MediaType.TEXT_PLAIN).get(String.class);
				
				t3 = System.nanoTime();
				
				separado = respuesta.split("+");

				try {
					t1 = Long.parseLong(separado[0]);
					t2 = Long.parseLong(separado[1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					System.out.println("\n\nError 2: No se pudo convertir el valor de pedirTiempo del servidor " + IP.get(i)
							+ "\nEn la iteracióon " +j);
				}  
				
				long o = determinarOffset(t0,t1,t2,t3);
				long d = determinarDelay(t0,t1,t2,t3);
				
				pares.add(new Par(o,d));

				//if()	si ( d < mejorPar.d ) mejorPar <- {d,o}
				
				// Version sin Marzullo
				if(d<mejorPar.delay) {
					mejorPar = new Par(o,d);
				}
				////////////

			}
			
				System.out.println("Mejor par sin aplicar marzullo: Offset: "+mejorPar.offset+" Delay: "+ mejorPar.delay);
				// Version con Marzullo
				mejorPar = marzullo(pares);
				System.out.println("Mejor par aplicando marzullo: Offset: "+mejorPar.offset+" Delay: "+ mejorPar.delay);
				////////////
				
				
		}
		
		return;
	}
	
	static long determinarOffset(long t0, long t1, long t2, long t3) {
		return (t1-t0 + t2-t3)/2;
	}
	static long determinarDelay(long t0, long t1, long t2, long t3) {
		return t1-t0 + t3-t2;
	}
	static Par marzullo(ArrayList<Par> pares) {
		int mejor = 0, contador = 0;
		
		
		return new Par(0,0);
	}
	
}
