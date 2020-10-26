package com.example.webapiclient;

public class RestService {
    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
    private static final String URL = "http://10.0.2.2/ApiInstitute/";
    private retrofit.RestAdapter restAdapter;
    private InstituteService apiService;

    public RestService()
    {
        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(InstituteService.class);
    }

    public InstituteService getService()
    {
        return apiService;
    }
}
