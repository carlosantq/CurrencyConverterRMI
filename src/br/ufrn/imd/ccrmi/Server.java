package br.ufrn.imd.ccrmi;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * blah blah blah
 * @author carlosantq
 * @author pedrohcavalcante
 *
 */
public class Server {
	
	/**
	 * Main method.
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public static void main (String [] args) throws RemoteException, MalformedURLException {
		//Execução do módulo de referência remota
		LocateRegistry.createRegistry(1099);
		
		//Instanciação do objeto
		Converter converter = new Converter();
		
		//Registro no módulo de referência remota
		Naming.rebind("rmi://localhost/CurrencyConverter", converter);
		
		System.out.println("Running!");
	}
}
