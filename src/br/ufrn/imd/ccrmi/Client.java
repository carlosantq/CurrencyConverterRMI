package br.ufrn.imd.ccrmi;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Classe cliente onde serão feitos os pedidos de conversão de moedas.
 * @author carlosant
 * @author pedrohcavalcante
 */
public class Client {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// Referência de objeto para o stub do servidor. Usado para chamada de métodos remotos.
		IConvert stub = (IConvert) Naming.lookup("rmi://localhost/CurrencyConverter");

		//Moedas de origem para conversão
		String[] values = { "DKK - Coroa Dinamarquesa", "NOK - Coroa Norueguesa", "SEK - Coroa Sueca",
				"CZK - Coroa Tcheca", "GBP - Libra Esterlina", "TRY - Lira Turca", "INR - Rúpia Indiana",
				"IDR - Rúpia Indonésia", "PKR - Rúpia Paquistanesa", "THB - Baht Tailandês", "USD - Dólar Americano",
				"AUD - Dólar Australiano", "CAD - Dólar Canadense", "SGD - Dólar de Cingapura",
				"HKD - Dólar de Hong Kong", "TWD - Dólar de Taiwan", "NZD - Dólar Neozelandês", "EUR - Euro",
				"HUF - Forint Húngaro", "CHF - Franco Suíço", "JPY - Iene Japonês", "ILS - Novo Shekel Israelense",
				"CLP - Peso Chileno", "PHP - Peso Filipino", "MXN - Peso Mexicano", "ZAR - Rand Sul-africano",
				"BRL - Real Brasileiro", "MYR - Ringgit Malaio", "RUB - Rublo Russo", "KRW - Won Sul-coreano",
				"CNY - Yuan Renminbi Chinês", "PLN - Zloty Polonês" };

		//Moedas de destino da conversão. NOTA: Ao deixar na opção "SELECIONE", a moeda de origem será convertido para todas as outras.
		String[] values2 = { "SELECIONE", "DKK - Coroa Dinamarquesa", "NOK - Coroa Norueguesa", "SEK - Coroa Sueca",
				"CZK - Coroa Tcheca", "GBP - Libra Esterlina", "TRY - Lira Turca", "INR - Rúpia Indiana",
				"IDR - Rúpia Indonésia", "PKR - Rúpia Paquistanesa", "THB - Baht Tailandês", "USD - Dólar Americano",
				"AUD - Dólar Australiano", "CAD - Dólar Canadense", "SGD - Dólar de Cingapura",
				"HKD - Dólar de Hong Kong", "TWD - Dólar de Taiwan", "NZD - Dólar Neozelandês", "EUR - Euro",
				"HUF - Forint Húngaro", "CHF - Franco Suíço", "JPY - Iene Japonês", "ILS - Novo Shekel Israelense",
				"CLP - Peso Chileno", "PHP - Peso Filipino", "MXN - Peso Mexicano", "ZAR - Rand Sul-africano",
				"BRL - Real Brasileiro", "MYR - Ringgit Malaio", "RUB - Rublo Russo", "KRW - Won Sul-coreano",
				"CNY - Yuan Renminbi Chinês", "PLN - Zloty Polonês" };

		Double value = 0.0;
		
		//Valida o valor a ser utilizado na conversão.
		while (value <= 0.0) {
			try {
				value = Double.parseDouble(JOptionPane.showInputDialog("Insert a value:"));
			}catch(NullPointerException ne) {
				System.exit(0);
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Wrong format. Please insert a  valid number.");
			}
		}
		
		//Tela de selecão da moeda de origem
		Object objetoFrom = JOptionPane.showInputDialog(null, "Convert Source", "CurrencyConverter",
				JOptionPane.DEFAULT_OPTION, null, values, "DKK");
		String selectedFrom = null;
		String selectedTo = null;
		if (objetoFrom != null) {
			selectedFrom = objetoFrom.toString().substring(0, 3);
			System.out.println(selectedFrom);
			System.out.println(value);
		} else {
			System.out.println("User cancelled.");
			System.exit(0);
		}
		
		//Tela de seleção da moeda de destino (ou para o caso de não selecionar nenhuma)
		Object objetoTo = JOptionPane.showInputDialog(null, "Convert Source", "CurrencyConverter",
				JOptionPane.DEFAULT_OPTION, null, values2, "SELECIONE");
		
		//Lista auxiliar utilizada para receber o retorno da conversão de uma moeda para todas
		List<Double> result = new ArrayList<Double>();
		
		if (objetoTo != null && !objetoTo.equals("SELECIONE")) {
			selectedTo = objetoTo.toString().substring(0, 3);
			Double resultado = 0.0;
			try{
				//Chamada do método remoto de conversão de uma moeda para outra
				resultado = stub.currencyAToB(value, selectedFrom, selectedTo);
			} catch (UnmarshalException e) {
				System.out.println("Oops, I did it again. The API reached its limit and is tired. Contact the developer group to get a new access key.");
				System.exit(0);
			}catch (ConnectException ce) {
				System.out.println("Server is off. Bye.");
				System.exit(0);
			}
			
			System.out.println(selectedFrom + " to "+ selectedTo + ": " + resultado);
		} else if (objetoTo.equals("SELECIONE")) {
			
			try{
				//Chamada do método remoto de conversão de uma moeda para todas as outras
				result = stub.currencyAToAll(value, selectedFrom);
			} catch (UnmarshalException e) {
				System.out.println("Oops, I did it again. The API reached its limit and is tired. Contact the developer group to get a new access key.");
				System.exit(0);
			} catch (ConnectException ce) {
				System.out.println("Server is off. Bye.");
				System.exit(0);
			}
			
			for (int i = 0; i < result.size(); i++) {
				System.out.println(values[i] + ": " + result.get(i));
			}
		} else {
			System.out.println("User cancelled.");
			System.exit(0);
		}


	}
}
