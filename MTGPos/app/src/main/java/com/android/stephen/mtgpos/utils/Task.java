package com.android.stephen.mtgpos.utils;

public enum Task {
    ITEMS("itemmtg"),
    AGENTS("agents"),
    UPDATEAGENT("update_agent_record"),
    ADDAGENT("add_agent"),
    FORGOT_PASSWORD("forgot_password"),
    CHANGE_PASSWORD("change_password");

    private String value;

    public String getValue() {
        return value;
    }

    Task(String value) {
        this.value = value;
    }
}
