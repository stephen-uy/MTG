package com.android.stephen.mtgpos.callback;

import com.android.stephen.mtgpos.utils.Task;

import java.util.HashMap;
import java.util.List;

public interface VolleyCallback {
    void onResponseReady(Task task, HashMap<String, String> response);
    void onResponseReady(Task task, List<HashMap<String, String>> response);
}
