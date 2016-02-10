package com.example.alin.newsreader;

import java.util.Map;

/**
 * Created by Alin on 2/6/2016.
 */
public interface AsyncResponse<T> {
    void onResponse(Map<String, String> response);
}
