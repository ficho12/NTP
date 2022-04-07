package servicios;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("Servidor")//ruta a la clase
public class Servidor {
	
	@GET //tipo de petición HTTP
	@Produces(MediaType.TEXT_PLAIN)
	@Path("reinicio")
	public String pedirTiempo()
	{
		String tiempo;
		
		
		
		/*
		 	pedirTiempo()
			t1 <- tiempo()
			sleep(aleatorio)
			t2 <- tiempo()
			devolver(t1,t2)
		 */
		
		try {
			Thread.sleep((long)(Math.random()*2)*1000); //Duerme entre 0 y 2s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return tiempo;
	}
}
