package br.ufrn.imd.ccrmi;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class CurrencyResponse {

	public static final String ACCESS_KEY = "f60e4668da4b6a583e6af0a82cb81089";
	public static final String BASE_URL = "http://apilayer.net/api/";
	public static final String FROM = "&from=";
	public static final String TO = "&to=";
	public static final String AMOUNT = "&amount=1";
	public static final String ENDPOINT = "live";
	public static final String CONVERT = "convert";
	public static final String STATIC_DOLAR = "USD";
	static CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void sendLiveRequest(String from, String to) {

		// The following line initializes the HttpGet Object with the URL in order to
		// send a request
		HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY);
		// HttpGet get = new HttpGet(BASE_URL + CONVERT + "?access_key=" + ACCESS_KEY +
		// FROM + from + TO + to + AMOUNT);
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

			// the following line converts the JSON Response to an equivalent Java Object
			JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

			System.out.println("Live Currency Exchange Rates");

			// Parsed JSON Objects are accessed according to the JSON resonse's hierarchy,
			// output strings are built

			System.out.println("1 Converting " + STATIC_DOLAR + " in " + from + ": "
					+ exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + from));
			Double from2 = 1/exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + from);
			System.out.println(from + " IN USD " + 1/exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + from));
			System.out.println("2 Convertion " + STATIC_DOLAR + " in " + to + ": "
					+ exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + to));
			System.out.println(to + " IN USD " + 1/exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + to));
			Double to2 = 1/exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + to);
			System.out.println(from + " IN " + to + ": " + from2/to2 );
			//System.out.println("3 converting " + from + " in " + to + ": " + 1/from + );
			System.out.println("\n");
			response.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// sendLiveRequest() function is executed
	public static void main(String[] args) throws IOException {
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

		Object objetoFrom = JOptionPane.showInputDialog(null, "Convert Source", "CurrencyConverter",
				JOptionPane.DEFAULT_OPTION, null, values, "AED");
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

		if (objetoTo != null) {
			selectedTo = objetoTo.toString().substring(0, 3);
			System.out.println(selectedTo);
		} else {
			System.out.println("User cancelled");
		}

		sendLiveRequest(selectedFrom, selectedTo);
		/*
		 * httpClient.close(); new BufferedReader(new
		 * InputStreamReader(System.in)).readLine();
		 */
		// httpClient.close();
		// new BufferedReader(new InputStreamReader(System.in)).readLine();
	}
}
