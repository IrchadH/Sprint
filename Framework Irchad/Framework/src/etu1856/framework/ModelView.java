package etu1856.framework;

import java.util.HashMap;

public class ModelView {
    private String view;
    private HashMap<String, Object> data;

    public void addItem(String key, Object value) {
        this.data.put(key, value);
    }

    public ModelView() {
        data = new HashMap<>();
    }

    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }
    public HashMap<String, Object> getData() {
        return data;
    }
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

}
