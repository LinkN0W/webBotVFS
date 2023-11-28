package org.example.jsonMethods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public abstract class JsonHandler  {

    JSONObject json;
    JSONArray jsonArray;


    JsonHandler(String jsonStr){
        if(jsonStr.startsWith("{"))
            json = new JSONObject(jsonStr);
        else if(jsonStr.startsWith("["))
            jsonArray = new JSONArray(jsonStr);
    }

    public abstract Object getElementByKey(String key);

    public abstract List<?> getAllElements();

    public abstract Object getEntity();
}
