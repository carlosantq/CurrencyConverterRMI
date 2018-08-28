package br.ufrn.imd.ccrmi;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Client {
	public static void main (String [] args) throws MalformedURLException, RemoteException, NotBoundException {
		//Referência de objeto para o stub do servidor. Usado para chamada de métodos remotos.
		IConvert stub = (IConvert) Naming.lookup("rmi://localhost/CurrencyConverter");
		
		//GBP to BRL
		System.out.println("GBP to BRL: " + stub.currencyAToB(10.0, 5, 27));
		
		//BRL to GBP
		System.out.println("BRL to GBP: " + stub.currencyAToB(10.0, 27, 5));
		
		//TODO: Uma forma de mostrar os nomes de cada uma das moedas do lado da conversão
		List<Double> result = new ArrayList<Double>();
		
		//all to GBP
		result = stub.currencyAToAll(10.0, 5);
		System.out.println("all to GBP:");
		for (int i=0; i<result.size(); i++) {
			System.out.println(result.get(i));
		}
		
		//all to BRL
		result = stub.currencyAToAll(10.0, 27);
		System.out.println("all to BRL:");
		for (int i=0; i<result.size(); i++) {
			System.out.println(result.get(i));
		}
		
		//all to USD
		result = stub.currencyAToAll(10.0, 11);
		System.out.println("all to USD:");
		for (int i=0; i<result.size(); i++) {
			System.out.println(result.get(i));
		}
		
	}
}
