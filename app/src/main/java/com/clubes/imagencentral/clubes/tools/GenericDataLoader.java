package com.clubes.imagencentral.clubes.tools;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;


public class GenericDataLoader extends AsyncTask<Void, Void, Exception>
{
	// M�todo predeterminado http
	JSONManager.HTTPMethod method = JSONManager.HTTPMethod.Get;
	
	// URL
	String url = "";
	
	// Listener 
	private OnPostExecuteListener execute_listener = null;
	
	// Parametros
	ArrayList<NameValuePair> body = null;
	
	// Object
	JSONObject object;
	
	/**
	 * Interfaz
	 * @author Oliver Strevel
	 *
	 */
	public static interface OnPostExecuteListener {
        void onPostExecute(JSONObject result, Exception exception);
        void onPreExecute();
    }
	
	public GenericDataLoader(String url, JSONManager.HTTPMethod method, ArrayList<NameValuePair> body, OnPostExecuteListener listener)
	{
		this.body = body;
		this.execute_listener = listener;
		this.url = url;
		this.method = method;
	}
	
	@Override
	protected void onPreExecute() 
	{
		if(execute_listener != null) {
			execute_listener.onPreExecute();
		}
	}
	
	@Override
	protected Exception doInBackground(Void... params)
	{
		try
		{
			object = (JSONObject)JSONManager.getJSONfromURL(url, JSONManager.JSONType.JsonObject, method, body);
		}
		catch (Exception e) {
			return e;
		}

		return null;
	}
	
	@Override
	protected void onPostExecute(Exception error)
	{
		if(execute_listener != null) {
			execute_listener.onPostExecute(object, error);
		}
	}
}
