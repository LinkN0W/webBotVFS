package org.example.jsonMethods;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonHandlerFactory {

    private JsonHandlerFactory(){

    }
    public static JsonHandler createJsonHandler(String jsonStr){
        if(jsonStr.startsWith("{"))
            return new JsonStringHandler(jsonStr);
        else if(jsonStr.startsWith("["))
            return new JsonArrayHandler(jsonStr);
        else return null;
    }

}
