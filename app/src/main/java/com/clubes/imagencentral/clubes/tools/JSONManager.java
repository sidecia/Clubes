package com.clubes.imagencentral.clubes.tools;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * 
 * @author Ollie
 *
 */
public class JSONManager
{	
	/**
	 * Enumeraci�n de los tipos de respuesta del JSON
	 */
	public enum JSONType {
		JsonObject, JsonArray, String
	}
	
	/**
	 * Enumeraci�on de los m�todos HTTP soportados
	 */
	public enum HTTPMethod {
		Post, Get, Rest
	}
	/**
	 * Tama�o del buffer de lectura
	 */
	public static final int BufferReaderSize = 8;
	
	/**
	 * Tiempo m�ximo de espera para realizar una conexi�n.
	 */
	public static final int ConnectionTimeOut = 25000;
	
	/**
	 * Tiempo m�ximo de espera para recibir alg�n dato dentro del flujo de datos
	 */
	public static final int ReadTimeOut = 35000;
	
	/**
	 * Establece si se mostrar�n mensajes en el Log
	 */
	private static boolean isDebugMode = true;
	
	/**
	 * Interpreta los argumentos de una ArrayList<NameValuePair> para establecerlos como argumentos para la petici�n
	 * @param body Lista de argumentos
	 * @return Una cadena de la forma: arg1=val1&arg2=val2&arg3=val3
	 */
	
	public static String getBuildParameters(ArrayList<NameValuePair> body)
	{	
		if(body != null)
		{
			StringBuilder params = new StringBuilder("");
			
			for(int i = 0; i < body.size(); i++)
			{
				params.append("&");
				params.append( body.get(i).getName() + "=" + body.get(i).getValue() );
			}
			
			if(isDebugMode) {
				Log.v( JSONManager.class.getSimpleName() , params.toString());
			}
			return params.toString();
		}
		return "";
	}

	/**
	 * Obtiene JSON de una URL especifica a traves de una peticion HTTP
	 * Modo de uso:
	 * <p>Para obtener un JSONObject:
	 * <pre> {@code JSONObject jObject = (JSONObject)JSONManager.getJSONfromURL( url, JSONTypes.JsonObject, HTTPMethods.HttpGet, null); }</pre>
	 * </p>
	 * - Para obtener un JSONArray:
	 * <pre>
	 * JSONArray jArray = (JSONArray)JSONManager.getJSONfromURL( url, JSONTypes.JsonArray, HTTPMethods.HttpGet, null);
	 * </pre>
	 * - Para obtener un String:
	 * <pre>
	 * String jString = (String)JSONManager.getJSONfromURL( url, JSONTypes.String, HTTPMethods.HttpGet, null);
	 * </pre>
	 * 
	 * @param url La direccion http desde donde se obtendra el JSON
	 * @param type Tipo de objeto en el cual se regresar� la respuesta JSON
	 * @param method Metodo HTTP para obtener los datos
	 * @param body Argumentos para la petici�n (Solo para POST)
	 * @return El Objecto que representa la respuesta JSON con el tipo especificado en type
	 */
	public static Object getJSONfromURL(String url, JSONType type, HTTPMethod method, ArrayList<NameValuePair> body ) throws Exception
	{
		String result = "";
		
		if(isDebugMode) {
			Log.v( JSONManager.class.getSimpleName() , url);
		}

		URL url_sender = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url_sender.openConnection();
        
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout( ConnectionTimeOut );
        connection.setReadTimeout( ReadTimeOut );

        if(method == HTTPMethod.Get) {
        	connection.setRequestMethod("GET");
        }
        else if(method == HTTPMethod.Post)
        {
        	connection.setRequestMethod("POST");
        	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        	// si hay parametros, se envian despues de los headers
        	if( body != null )
        	{
	        	String urlParameters = getBuildParameters(body);
	        	connection.setDoOutput(true);
	        	
	    		DataOutputStream writer_output = new DataOutputStream( connection.getOutputStream() );
	    		writer_output.writeBytes( urlParameters );
	    		writer_output.flush();
	    		writer_output.close();
        	}
        }
        
        connection.connect();
        
        int status = connection.getResponseCode();

        switch (status)
        {
            case 200:
            case 201:
            	
            	BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream() ), BufferReaderSize);
    			StringBuilder sb = new StringBuilder();
    			
    			String line = null;
    			
    			while ((line = reader.readLine()) != null)
    				sb.append(line + "\n");
    			
    			reader.close();
    			
    			result = sb.toString();
    			
    			if( TextUtils.isEmpty(result) || result.equals("null")) {
    				throw new Exception ("Sin resultados");
    			}
    			
    			if(isDebugMode) {
    				Log.e(JSONManager.class.getSimpleName(), "JSON: " + result);
    			}
        }
        
		switch (type)
		{
			case JsonObject:
			default:
				return new JSONObject(result);
			
			case JsonArray:
				return new JSONArray(result);
				
			case String:
				return result;
		}
	}
	
	public static boolean hasValueInObject(JSONObject object, String field, String value)
	{
		try
		{
			return object != null && hasValidKey(object, field) && object.getString(field).equals(value);
		}
		catch (JSONException e) {e.printStackTrace();}
		
		return false;
	}
	
	/** Verifica si en el JSON hay una clave valida */ 
	public static boolean hasValidKey(JSONObject object, String key)  {
		try
		{
			return object != null && object.has(key) && !TextUtils.isEmpty(object.getString(key));
		}
		catch (JSONException e) {e.printStackTrace();}
		
		return false;
	}
	
	public static void enableHttpResponseCache(Activity activity)
	{
		try
		{
			long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
			File httpCacheDir = new File( activity.getCacheDir(), "http");
			Class.forName("android.net.http.HttpResponseCache")
				 .getMethod("install", File.class, long.class)
		         .invoke(null, httpCacheDir, httpCacheSize);
			
			//Log.i("enableHttpResponseCache", "HTTP response cache installed");
		  }
		catch (Exception httpResponseCacheNotAvailable) {
		    //Log.d("enableHttpResponseCache", "HTTP response cache is unavailable.");
		}
	}
}