package br.ufrn.imd.ccrmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Classe onde está toda a lógica de conversão de moedas, incluindo o acesso a API. Implementa a interface IConvert.
 * @author carlosant
 * @author pedrohcavalcante
 */
@SuppressWarnings("serial")
public class Converter extends UnicastRemoteObject implements IConvert {

	//Atributos utilizados no acesso da API
	public static final String ACCESS_KEY = "f60e4668da4b6a583e6af0a82cb81089";
	public static final String BASE_URL = "http://apilayer.net/api/";
	public static final String FROM = "&from=";
	public static final String TO = "&to=";
	public static final String AMOUNT = "&amount=1";
	public static final String ENDPOINT = "live";
	public static final String CONVERT = "convert";
	public static final String STATIC_DOLAR = "USD";
	static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 * Construtor padrão
	 * @throws RemoteException
	 */
	protected Converter() throws RemoteException {
		super();
	}

	/**
	 * Converte um dado valor de uma moeda A para uma moeda B.
	 * @param value - valor a ser convertido
	 * @param from - moeda de origem
	 * @param to - moeda de destino
	 * @return Valor convertido
	 * @throws RemoteException
	 */
	@Override
	public Double currencyAToB(Double value, String from, String to) throws RemoteException {
		Double result = sendLiveRequest(from, to);
		if (result == null) {
			System.exit(0);
		}
		return value * result;
	}

	/**
	 * Converte o valor de uma moeda A para todas as 20 moedas da base de dados.
	 * @param value - valor a ser convertido
	 * @param from - moeda de origem
	 * @return Lista com valores convertidos
	 * @throws RemoteException
	 */
	@Override
	public List<Double> currencyAToAll(Double value, String from) throws RemoteException {
		List<Double> result = new ArrayList<Double>();
		result.add(value * sendLiveRequest(from, "DKK"));
		result.add(value * sendLiveRequest(from, "NOK"));
		result.add(value * sendLiveRequest(from, "SEK"));
		result.add(value * sendLiveRequest(from, "CZK"));
		result.add(value * sendLiveRequest(from, "GBP"));
		result.add(value * sendLiveRequest(from, "TRY"));
		result.add(value * sendLiveRequest(from, "INR"));
		result.add(value * sendLiveRequest(from, "IDR"));
		result.add(value * sendLiveRequest(from, "PKR"));
		result.add(value * sendLiveRequest(from, "THB"));
		result.add(value * sendLiveRequest(from, "USD"));
		result.add(value * sendLiveRequest(from, "AUD"));
		result.add(value * sendLiveRequest(from, "CAD"));
		result.add(value * sendLiveRequest(from, "SGD"));
		result.add(value * sendLiveRequest(from, "HKD"));
		result.add(value * sendLiveRequest(from, "TWD"));
		result.add(value * sendLiveRequest(from, "NZD"));
		result.add(value * sendLiveRequest(from, "EUR"));
		result.add(value * sendLiveRequest(from, "HUF"));
		result.add(value * sendLiveRequest(from, "CHF"));
		result.add(value * sendLiveRequest(from, "JPY"));
		result.add(value * sendLiveRequest(from, "ILS"));
		result.add(value * sendLiveRequest(from, "CLP"));
		result.add(value * sendLiveRequest(from, "PHP"));
		result.add(value * sendLiveRequest(from, "MXN"));
		result.add(value * sendLiveRequest(from, "ZAR"));
		result.add(value * sendLiveRequest(from, "BRL"));
		result.add(value * sendLiveRequest(from, "MYR"));
		result.add(value * sendLiveRequest(from, "RUB"));
		result.add(value * sendLiveRequest(from, "KRW"));
		result.add(value * sendLiveRequest(from, "CNY"));
		result.add(value * sendLiveRequest(from, "PLN"));

		return result;
	}

	/**
	 * Método auxiliar criado para extrair da API o valor de 1 unidade da moeda de origem e da de destino 
	 * em dólares, para então ser feita a conversão de uma moeda para a outra.
	 * @param value - valor a ser convertido
	 * @param from - moeda de origem
	 * @return o valor convertido de uma moeda para outra ou NULL no caso de requisições com erro da API
	 */
	private Double sendLiveRequest(String from, String to) {

		//Variáveis auxiliares
		Double from2 = 1.0;
		Double to2 = 1.0;
		
		// Inicializa o objeto HttpGet com a URL para mandar a requisição para a API
		HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY);
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

			// Converte a resposta JSON em um objeto equivalente em Java
			JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

			//Lança mensagem no console de que o acesso à API foi iniciado
			System.out.println("Live Currency Exchange Rates");
			
			//Variável utilizada para capturar se a requisição à API teve status ou não
			boolean status = exchangeRates.getBoolean("success");

			if (!status) {
				String codigoErro = exchangeRates.getJSONObject("error").getString("code");

				String infoErro = exchangeRates.getJSONObject("error").getString("info");

				System.out.println("API reached its peak of access.");
				System.out.println("Error: " + codigoErro);
				System.out.println("Message: " + infoErro);
				System.exit(0);
			}
			
			
			//Valor equivalente à 1 dólar na moeda de origem
			System.out.println("Converting " + STATIC_DOLAR + " in " + from + ": "
					+ exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + from));
			
			//Divisão para transformar o valor recuperado da API em 1 unidade da moeda de origem
			from2 = 1 / exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + from);
			
			//Impressão no console 
			System.out.println("1 unity of " + from + " in USD: " + from2);
			
			//Valor equivalente à 1 dólar na moeda de destino
			System.out.println("Converting " + STATIC_DOLAR + " in " + to + ": "
					+ exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + to));
			
			//Divisão para transformar o valor recuperado da API em 1 unidade da moeda de destino
			to2 = 1 / exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + to);
			
			//Impressão no console 
			System.out.println("1 unity of " + to + " in USD: " + to2);
			
			//Impressão no console do resultado da conversão da moeda de origem para a de destino
			System.out.println(from + " IN " + to + ": " + from2 / to2);

			response.close();
			return from2 / to2;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}
}
