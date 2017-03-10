package com.android.stephen.mtgpos.utils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.android.stephen.mtgpos.callback.VolleyCallback;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by Mark Ferdie Catabona on 7/27/2016.
 * This class utilizes Google's Volley library for networking by google.
 * <h1>Note</h1>
 * <body>
 *     This is not a static class
 * because it should be instantiated
 * to set some values before making a request
 * as this supports HTTP and HTTPS connections.
 * This utilizes Task an API enums.
 * </body>
 */
public class HttpVolleyConnector {

    private final String TAG = this.getClass().getSimpleName();
    //    private static int timeoutConnection = 15000;
    private static int timeoutSocket = 500000;
    private static Context context;
    public LinkedHashMap<String, String> map;
    public LinkedList<LinkedHashMap<String, String>> hashMapList;
    private String error_response = "{\"response_code\":\"2\",\"response_message\":\"Server Error\"}";
    private RequestQueue requestQueue;

    private boolean isHttps = false;


    /**
     * Set this to true if using HTTPS connection.
     * This tells the class to create HTTPSConnection.
     * @param isHttps
     */
    public void setIsHttps(boolean isHttps) {
        this.isHttps = isHttps;
    }

    /**
     * For POST Method. Params mandatory. if no params set it to null.
     *
     * @param c
     * @param callback
     * @param api
     * @param task
     * @param params
     */
    public void wPost(Context c, VolleyCallback callback, API api, Task task, ContentValues params, boolean isArray) {
        createRequest(c, callback, api, Request.Method.POST , task, params, isArray);
    }

    /**
     * For Get Method with params
     * @param c
     * @param callback
     * @param api
     * @param task
     * @param params
     */
    public void wGet(Context c, VolleyCallback callback, API api, Task task, ContentValues params, boolean isArray) {
        createRequest(c, callback, api, Request.Method.GET , task, params, isArray);
    }

    /**
     * For GET Method with no params
     * @param c
     * @param callback
     * @param api
     * @param task
     */
    public void wGet(Context c, VolleyCallback callback, API api, Task task, boolean isArray) {
        createRequest(c, callback, api, Request.Method.GET , task, null, isArray);
    }

    /**
     * This creates a request {POST,GET}
     * @param c - the context of caller object/class.
     * @param api - values for this are our web api controllers
     * @param method - values for this are constants Request.Method interface.
     * @param task - method inside controller. ex. server/agent_api/{login}
     * @param params - these are the values to be passed on to server.
     * @param isArray
     */
    private void createRequest(Context c, VolleyCallback callback, API api, int method, Task task, ContentValues params, boolean isArray) {
        this.context = c;
        System.setProperty("http.keepAlive", "false");
        if(!Connectivity.isConnected(c)) {
            JSONObject obj = null;
//            Log.i("params",params.toString());
            try {
                obj = (JSONObject) new JSONTokener(error_response.trim()).nextValue();
                map = JSONObjectParser.parseFromSimpleJSONObject(obj);
                hashMapList.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (isArray)
                callback.onResponseReady(task, hashMapList);
            else
                callback.onResponseReady(task, map);
            return;
        }
       // Log.i("HttpConnectVolley","pointed to: "+ ServerConstants.SERVER_URL + task.getValue());
        if (isArray)
            connectUsingVolleyArray(c,task,params,callback,method,api);
        else
            connectUsingVolley(c,task,params,callback,method,api);
    }

    private void connectUsingVolley(Context c, final Task task, final ContentValues params, final VolleyCallback callback, final int method, API api) {
        Log.i("connectUsingVolley","postData = "+params);
        String url = GlobalVariables.URL + api.getApi() + "/" +task.getValue() + "/";
        Log.i(TAG,"URL = "+url);
        if(method == Request.Method.GET) {
            url += assembleUrlForGet(params, task);
            Log.i(TAG,"URL = "+url);
        } else {
//            url += assembleUrlForGet(params, task);
//            Log.i(TAG,"URL_POST = "+url);
        }
        Log.i("method","after method");
        StringRequest stringRequest = new StringRequest
                (method, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("HTTPConnect_volley","Response: "+response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        map = new LinkedHashMap<>();
                        hashMapList = new LinkedList<>();
                        try {
//                            jsonArray = new JSONArray(response);
//                            for(int i=0;i<jsonArray.length();i++) {
//                                obj = jsonArray.getJSONObject(i);
                                obj = (JSONObject) new JSONTokener(response.trim()).nextValue();
                                map = JSONObjectParser.parseFromSimpleJSONObject(obj);
//                                hashMapList.add(map);
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResponseReady(task, map);
//                        callback.onResponseReady(task, hashMapList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("HTTPConnect_volley","VolleyError: "+error.getLocalizedMessage());
                        JSONObject obj = null;
                        try {
                            obj = (JSONObject) new JSONTokener(error_response.trim()).nextValue();
                            map = JSONObjectParser.parseFromSimpleJSONObject(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResponseReady(task, map);
                        error.printStackTrace();
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<>();
                if (method == Method.POST)
                    headers.put("Content-Type", "application/json");
                else
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", GlobalVariables.BASIC_AUTH);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if(method == Request.Method.GET) {
                    return super.getParams();
                }

                Map<String, String> map = new HashMap<>();
                    for(String key : params.keySet()) {
//                        if (task.getValue().equalsIgnoreCase(Task.SAVE_NEW_STOCK.getValue())){
//                            if (!key.equalsIgnoreCase(Parameters.USERNAME.getValue())){
//                                map.put(key,params.getAsString(key));
//                            }
//                        } else {
//                            map.put(key, params.getAsString(key));
//                        }
                        map.put(key,params.getAsString(key));
                    }
                Log.i(getClass().getSimpleName(),"params = "+map);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeoutSocket,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(isHttps) {
            requestQueue = Volley.newRequestQueue(c, hurlStack);
        } else {
            requestQueue = Volley.newRequestQueue(c);
        }
        stringRequest.setTag(task);
        requestQueue.add(stringRequest);

        try {
            Log.i("HTTPConnect_volley","body = "+stringRequest.getBody());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        try {
            Log.i("HTTPConnect_volley","headers = "+stringRequest.getHeaders());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    private void connectUsingVolleyArray(Context c, final Task task, final ContentValues params, final VolleyCallback callback, final int method, API api) {
        Log.i("connectUsingVolley","postData = "+params);
        String url = GlobalVariables.URL + api.getApi() + "/" +task.getValue() + "/";
        Log.i(TAG,"URL = "+url);
        if(method == Request.Method.GET) {
            url += assembleUrlForGet(params, task);
            Log.i(TAG,"URL = "+url);
        }
        Log.i("method","after method");
        StringRequest stringRequest = new StringRequest
                (method, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("HTTPConnect_volley","Response: "+response);
                        JSONObject obj;
                        JSONArray jsonArray;
                        map = new LinkedHashMap<>();
                        hashMapList = new LinkedList<>();
                        try {
                            jsonArray = new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++) {
                                obj = jsonArray.getJSONObject(i);
//                            obj = (JSONObject) new JSONTokener(response.trim()).nextValue();
                                map = JSONObjectParser.parseFromSimpleJSONObject(obj);
                                hashMapList.add(map);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        callback.onResponseReady(task, map);
                        callback.onResponseReady(task, hashMapList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("HTTPConnect_volley","VolleyError: "+error.getLocalizedMessage());
                        JSONObject obj = null;
                        try {
                            obj = (JSONObject) new JSONTokener(error_response.trim()).nextValue();
                            map = JSONObjectParser.parseFromSimpleJSONObject(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResponseReady(task, map);
                        error.printStackTrace();
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", GlobalVariables.BASIC_AUTH);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if(method == Request.Method.GET) {
                    return super.getParams();
                }

                Map<String, String> map = new HashMap<>();
                for(String key : params.keySet()) {
                    map.put(key, params.getAsString(key));
                }
                Log.i(getClass().getSimpleName(),"params = "+map);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeoutSocket,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(isHttps) {
            requestQueue = Volley.newRequestQueue(c, hurlStack);
        } else {
            requestQueue = Volley.newRequestQueue(c);
        }
        stringRequest.setTag(task);
        requestQueue.add(stringRequest);

        try {
            Log.i("HTTPConnect_volley","body = "+stringRequest.getBody());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        try {
            Log.i("HTTPConnect_volley","headers = "+stringRequest.getHeaders());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    public void cancelAll(Task task){
        requestQueue.cancelAll(task);
    }

    private String assembleUrlForGet(ContentValues params, Task task) {
        String getParams = "?";
        if(params==null) {
            return "";
        }
        if (task.getValue().equalsIgnoreCase(Task.STORE_DETAILS.getValue())){
            getParams += Parameters.USERNAME.getValue() + "=" + params.getAsString(Parameters.USERNAME.getValue()) + "&";
            getParams += Parameters.PASSWORD.getValue() + "=" + params.getAsString(Parameters.PASSWORD.getValue()) + "&";
            getParams += Parameters.MAC_ADDRESS.getValue() + "=" + params.getAsString(Parameters.MAC_ADDRESS.getValue()) + "&";
        } else if (task.getValue().equalsIgnoreCase(Task.SAVE_NEW_STOCK.getValue())) {
            getParams += Parameters.USERNAME.getValue() + "=" + params.getAsString(Parameters.USERNAME.getValue()) + "&";
        } else {
            for (String key : params.keySet()) {
                getParams += key + "=" + params.getAsString(key) + "&";
            }
        }
        return getParams.substring(0, getParams.length()-1);
    }

    private HurlStack hurlStack = new HurlStack() {
        @Override
        protected HttpURLConnection createConnection(URL url) throws IOException {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
            try {
                httpsURLConnection.setSSLSocketFactory(Helper.getSSLSocketFactory(context));
                httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return httpsURLConnection;
        }
    };

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //return true; // verify always returns true, which could cause insecure network traffic due to trusting TLS/SSL server certificates for wrong hostnames
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify(hostname, session);
            }
        };
    }

    /**
     * set ServerConstants.CERTIFICATE_PATH to get rid of the exception.
     * @return
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
//    private SSLSocketFactory getSSLSocketFactory() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        InputStream caInput = context.getResources().getAssets().open(ServerConstants.CERTIFICATE_PATH); // this cert file stored in \app\src\main\res\raw folder path
//
//        Certificate ca = cf.generateCertificate(caInput);
//        caInput.close();
//
//        // Create a KeyStore containing our trusted CAs
//        String keyStoreType = KeyStore.getDefaultType();
//        KeyStore keyStore = null;
//        try {
//            keyStore = KeyStore.getInstance(keyStoreType);
//            keyStore.load(null, null);
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//        keyStore.setCertificateEntry("ca", ca);
//
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keyStore);
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);
//
//        return sslContext.getSocketFactory();
//    }
}

