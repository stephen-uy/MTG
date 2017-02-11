package com.android.stephen.mtgpos.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Mark Ferdie Catabona on 6/13/2016.
 */
public class JSONObjectParser {

    public static ArrayList<HashMap<String, String>> parseHistoryJSONResponse(JSONObject response) throws JSONException {

        ArrayList<HashMap<String, String>> mainMap = new ArrayList<>();

        if(response.has("data")) {
            JSONArray dataArray = response.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                HashMap<String, String> map = new HashMap<>();
                JSONObject obj = dataArray.getJSONObject(i);
                Iterator<String> iter = obj.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    String value = obj.getString(key);

                    map.put(key, value);
                }
                mainMap.add(map);
            }
        }

        String transactionResponseCode = response.getString("transaction_response_code");
        String transactionMessage = response.getString("transaction_message");
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("transaction_response_code",transactionResponseCode);
        responseMap.put("transaction_message",transactionMessage);

        mainMap.add(responseMap);

        return mainMap;
    }

    /**
     * This parses a common JSONObject format result with inner object
     * example: "{\"data\":{\"pin\":\"2685\"},\"transaction_response_code\":\"0\",\"transaction_message\":\"Success!\"}"
     * @return
     */
    public static HashMap<String, String> parseFromJSONObject(JSONObject jsonResponse){
        JSONObject data = null;
        String responseCode = null;
        String responseMessage = null;
        String partnerUserRoleName = null;
        String partnerUserRoleId = null;


        try {
            responseCode = jsonResponse.optString("transaction_response_code");
            responseMessage = jsonResponse.optString("transaction_message");
            if(jsonResponse.has("data") && jsonResponse.getJSONObject("data")!=null) {
                data = jsonResponse.getJSONObject("data");
                if(data.has("partner_user_role")) {
                    partnerUserRoleName = data.getJSONObject("partner_user_role").getString("Name");
                    partnerUserRoleId = data.getJSONObject("partner_user_role").getString("PartnerRoleId");
                }

                Log.i("testJSON","data: "+data.toString());
            }

            Log.i("testJSON","response_code: "+responseCode);
            Log.i("testJSON","response_message: "+responseMessage);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String,String> map = new HashMap<>();

        try {
            if(jsonResponse.has("data") && jsonResponse.getJSONObject("data") != null) {

                Iterator<String> iter = data.keys();
                while(iter.hasNext()) {
                    String temp = iter.next();
                    try {
                        if(!temp.equals("partner_user_role")) {
                            map.put(temp,data.getString(temp));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(partnerUserRoleName!=null && !partnerUserRoleName.isEmpty() && partnerUserRoleId!=null && !partnerUserRoleId.isEmpty()) {
            map.put("PartnerUserRoleName",partnerUserRoleName);
            map.put("PartnerUserRoleId",partnerUserRoleId);
        }
        map.put("transaction_response_code",responseCode);
        map.put("transaction_message",responseMessage);
        return map;
    }


    /**
     * This parses a simple JSONObject format without inner object
     * example: "{\"transaction_response_code\":\"0\",\"transaction_message\":\"Success!\"}"
     * @param jsonResponse
     * @return
     */
    public static HashMap<String, String> parseFromSimpleJSONObject(JSONObject jsonResponse){
        Iterator<String> iter = jsonResponse.keys();

        HashMap<String,String> map = new HashMap<>();

        while(iter.hasNext()) {
            String temp = iter.next();
            try {
                map.put(temp,jsonResponse.getString(temp));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
