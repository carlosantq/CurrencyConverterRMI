package br.ufrn.imd.ccrmi;

import java.io.EOFException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.sun.media.jfxmedia.logging.Logger;

public class Client {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// Referência de objeto para o stub do servidor. Usado para chamada de métodos
		// remotos.
		IConvert stub = (IConvert) Naming.lookup("rmi://localhost/CurrencyConverter");

		String[] values = { "DKK - Coroa Dinamarquesa", "NOK - Coroa Norueguesa", "SEK - Coroa Sueca",
				"CZK - Coroa Tcheca", "GBP - Libra Esterlina", "TRY - Lira Turca", "INR - Rúpia Indiana",
				"IDR - Rúpia Indonésia", "PKR - Rúpia Paquistanesa", "THB - Baht Tailandês", "USD - Dólar Americano",
				"AUD - Dólar Australiano", "CAD - Dólar Canadense", "SGD - Dólar de Cingapura",
				"HKD - Dólar de Hong Kong", "TWD - Dólar de Taiwan", "NZD - Dólar Neozelandês", "EUR - Euro",
				"HUF - Forint Húngaro", "CHF - Franco Suíço", "JPY - Iene Japonês", "ILS - Novo Shekel Israelense",
				"CLP - Peso Chileno", "PHP - Peso Filipino", "MXN - Peso Mexicano", "ZAR - Rand Sul-africano",
				"BRL - Real Brasileiro", "MYR - Ringgit Malaio", "RUB - Rublo Russo", "KRW - Won Sul-coreano",
				"CNY - Yuan Renminbi Chinês", "PLN - Zloty Polonês" };

		String[] values2 = { "SELECIONE", "DKK - Coroa Dinamarquesa", "NOK - Coroa Norueguesa", "SEK - Coroa Sueca",
				"CZK - Coroa Tcheca", "GBP - Libra Esterlina", "TRY - Lira Turca", "INR - Rúpia Indiana",
				"IDR - Rúpia Indonésia", "PKR - Rúpia Paquistanesa", "THB - Baht Tailandês", "USD - Dólar Americano",
				"AUD - Dólar Australiano", "CAD - Dólar Canadense", "SGD - Dólar de Cingapura",
				"HKD - Dólar de Hong Kong", "TWD - Dólar de Taiwan", "NZD - Dólar Neozelandês", "EUR - Euro",
				"HUF - Forint Húngaro", "CHF - Franco Suíço", "JPY - Iene Japonês", "ILS - Novo Shekel Israelense",
				"CLP - Peso Chileno", "PHP - Peso Filipino", "MXN - Peso Mexicano", "ZAR - Rand Sul-africano",
				"BRL - Real Brasileiro", "MYR - Ringgit Malaio", "RUB - Rublo Russo", "KRW - Won Sul-coreano",
				"CNY - Yuan Renminbi Chinês", "PLN - Zloty Polonês" };

		double value = Double.parseDouble(JOptionPane.showInputDialog("Value"));
		
		
		
		Object objetoFrom = JOptionPane.showInputDialog(null, "Convert Source", "CurrencyConverter",
				JOptionPane.DEFAULT_OPTION, null, values, "DKK");
		String selectedFrom = null;
		String selectedTo = null;
		if (objetoFrom != null) {
			selectedFrom = objetoFrom.toString().substring(0, 3);
			System.out.println(selectedFrom);
		} else {
			System.out.println("User cancelled");
		}
		
		Object objetoTo = JOptionPane.showInputDialog(null, "Convert Source", "CurrencyConverter",
				JOptionPane.DEFAULT_OPTION, null, values2, "SELECIONE");
		List<Double> result = new ArrayList<Double>();
		if (objetoTo != null && !objetoTo.equals("SELECIONE")) {
			selectedTo = objetoTo.toString().substring(0, 3);
			Double resultado = 0.0;
			try{
				resultado = stub.currencyAToB(value, selectedFrom, selectedTo);
			} catch (UnmarshalException e) {
				System.out.println("ops, I did it again. A API chegou ao limite e está cansada. Contate o grupo desenvolvedor para que seja "
						+ "dada uma nova chave de acesso.");
				System.exit(0);
			}
			
			System.out.println(selectedFrom + " to "+ selectedTo + ": " + resultado);
		} else if (objetoTo.equals("SELECIONE")) {
			
			try{
				result = stub.currencyAToAll(value, selectedFrom);
			} catch (UnmarshalException e) {
				System.out.println("ops, I did it again. A API chegou ao limite e está cansada. Contate o grupo desenvolvedor para que seja "
						+ "dada uma nova chave de acesso.");
				System.exit(0);
			}
			for (int i = 0; i < result.size(); i++) {
				System.out.println(result.get(i));
			}
		} else {
			System.out.println("User cancelled");
		}


	}
}
