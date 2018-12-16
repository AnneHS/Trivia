package com.example.anneh.trivia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// Class to include parameters that have to be included in the POST request
public class PostRequest extends StringRequest {
    private Map<String, String> params;

    // Constructor
    public PostRequest( Map<String, String> params, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.params = params;
    }

    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}

