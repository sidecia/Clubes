package com.clubes.imagencentral.clubes.tools;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Lau on 29/01/2015.
 */
public class Json {
    public static abstract class HTTPMethods
    {
        final public static int HttpPost  = 1;
        final public static int HttpGet  = 2;
    }
    public static JSONObject getJson(String url,int method, ArrayList<NameValuePair> body){

        InputStream is = null;
        String result = "";
        JSONObject jsonObject = null;
        // HTTP
        try {
            HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
            HttpUriRequest request = null;
            if(method == Json.HTTPMethods.HttpGet) {
                request = new HttpGet(url);
            }
            else if(method == Json.HTTPMethods.HttpPost) {
                request = new HttpPost(url);

                if(body != null)
                    ((HttpPost)request).setEntity(new UrlEncodedFormEntity(body));
            }

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(Exception e) {
            return null;
        }

        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("JSONResult", result);
        } catch(Exception e) {
            return null;
        }

        // Convert string to object
        try {
            jsonObject = new JSONObject(result);
        } catch(JSONException e) {
            return null;
        }

        return jsonObject;

    }

}
