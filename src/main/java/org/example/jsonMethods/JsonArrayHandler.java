package org.example.jsonMethods;

import java.util.List;

class JsonArrayHandler extends JsonHandler{
    JsonArrayHandler(String jsonStr) {
        super(jsonStr);
    }

    @Override
    public Object getElementByKey(String key) {
        return null;
    }

    @Override
    public List<?> getAllElements() {
        return null;
    }

    @Override
    public Object getEntity() {
        return null;
    }
}
