package com.ramesh.weatherapp.retrofit;

import java.io.Serializable;

/**
 * Created by Ramesh Kumar on 8/30/17.
 */

public class HttpResponse implements Serializable {

    private static final long serialVersionUID = -142826603598620309L;
    int responseCode = 0;
    String responseData = "";
    String responsMessage = "";

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getResponsMessage() {
        return responsMessage;
    }

    public void setResponsMessage(String responsMessage) {
        this.responsMessage = responsMessage;
    }
}
