package servicios;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("Servidor")//ruta a la clase
public class Servidor extends Thread {
	
	@GET //tipo de petición HTTP
	@Produces(MediaType.TEXT_PLAIN)
	@Path("pedirTiempo")
	public String pedirTiempo()
	{		
		long t1 = System.currentTimeMillis();
		
		/*
		 	pedirTiempo()
			t1 <- tiempo()
			sleep(aleatorio)
			t2 <- tiempo()
			devolver(t1,t2)
		 */
		
		dormir();
		
		
		long t2 = System.currentTimeMillis();

		
		return Long.toString(t1) + "#" + Long.toString(t2);
	}

	private void dormir() {
		try {
			Thread.sleep((long)Math.random()*2000); //Duerme entre 0 y 2s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
