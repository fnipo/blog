package com.wordpress.felipenipo.network;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class NetworkQueue {

	private static final String CONTENT_TYPE_PARAM_KEY = "Content-Type";
	private static final String CONTENT_TYPE_PARAM_VALUE = "application/json";
	private static final String ACCEPT_PARAM_KEY = "Accept";
	private static final String ACCEPT_PARAM_VALUE = "application/json";
	
	private static NetworkQueue mInstance;
	private Context mApplicationContext;
	private RequestQueue mRequestQueue;
	
	// Prevent instantiation
	private NetworkQueue() {
		super();
	}
	
	// Singleton
	public static NetworkQueue getInstance() {
		if(mInstance == null) {
			mInstance = new NetworkQueue();
		}
		return mInstance;
	}
	
	public void init(Application application) {
		if(mApplicationContext == null) {
			mApplicationContext = application.getApplicationContext();
			mRequestQueue = Volley.newRequestQueue(mApplicationContext);
		}
	}
	
	public void cancelRequestsByTag(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
	
	public void cancelAllRequests() {
		mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
	}
	
	public void doGet(String url, Object tag,
		NetworkRequestCallback<JSONObject> networkRequestCallback) {
		JsonObjectRequest jsonObjectRequest = buildJSONRequest(
				Request.Method.GET,
				url,
				null,
				tag,
				networkRequestCallback);
		doRequest(jsonObjectRequest);
	}
	
	public void doGetArray(String url, Object tag,
		NetworkRequestCallback<JSONArray> networkRequestCallback) {
		// The Method is supposed to be GET
		JsonArrayRequest jsonArrayRequest = buildJSONRequest(
			url,
			tag,
			networkRequestCallback);
		doRequest(jsonArrayRequest);
	}
	
	public void doPost(String url, JSONObject jsonObject, Object tag,
		NetworkRequestCallback<JSONObject> networkRequestCallback) {
		JsonObjectRequest jsonObjectRequest = buildJSONRequest(
			Request.Method.POST,
			url,
			jsonObject,
			tag,
			networkRequestCallback); 
		doRequest(jsonObjectRequest);
	}
	
	private JsonObjectRequest buildJSONRequest(int method, String url, JSONObject jsonObject, Object tag,
		final NetworkRequestCallback<JSONObject> networkRequestCallback) {
		Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				notifyOnResponse(response, networkRequestCallback);
			}
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				notifyOnErrorResponse(error, networkRequestCallback);
			}
		};
		
		JsonObjectRequest request = new JsonObjectRequest(method, url, jsonObject, listener, errorListener) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return buildHeaders();
			}
		};
		request.setTag(tag);
		return request;
	}
	
	private JsonArrayRequest buildJSONRequest(String url, Object tag,
		final NetworkRequestCallback<JSONArray> networkRequestCallback) {
		Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				notifyOnResponse(response, networkRequestCallback);
			}
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				notifyOnErrorResponse(error, networkRequestCallback);
			}
		};
		
		// The Method is supposed to be GET
		JsonArrayRequest request = new JsonArrayRequest(url, listener, errorListener) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return buildHeaders();
			}
		};
		request.setTag(tag);
		return request;
	}
	
	private Map<String, String> buildHeaders() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(CONTENT_TYPE_PARAM_KEY, CONTENT_TYPE_PARAM_VALUE);
		headers.put(ACCEPT_PARAM_KEY, ACCEPT_PARAM_VALUE);
		return headers;
	}
	
	private <T> void doRequest(Request<T> request) {
		mRequestQueue.add(request);
	}
	
	private <T> void notifyOnResponse(T response,
		NetworkRequestCallback<T> networkRequestCallback) {
		if(networkRequestCallback != null) {
			networkRequestCallback.onRequestResponse(response);
		}
	}
	
	private <T> void notifyOnErrorResponse(Exception error,
		NetworkRequestCallback<T> networkRequestCallback) {
		if(networkRequestCallback != null) {
			networkRequestCallback.onRequestError(error);
		}
	}
}
