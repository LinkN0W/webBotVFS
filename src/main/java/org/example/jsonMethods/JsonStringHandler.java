package org.example.jsonMethods;

import org.example.jsonMethods.JsonHandler;

import java.util.List;

class JsonStringHandler extends JsonHandler {


    JsonStringHandler(String jsonStr) {
        super(jsonStr);
    }

    @Override
    public String getElementByKey(String key) {
        return !json.isNull(key) ? json.getString(key) : null ;
    }

    @Override
    public List<String> getAllElements() {
        return null;
    }

    @Override
    public String getEntity() {
        return json.toString();
    }
}
