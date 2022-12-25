package com.oliverchico.mobileproject.tmdb;

public class ConfigurationService extends BaseService {

    private static ConfigurationService instance;
    private ConfigurationInterface configurationInterface;

    private ConfigurationService() {
        super();
        this.configurationInterface = retrofit.create(ConfigurationInterface.class);
    }

    public static ConfigurationService getInstance() {
        if (instance == null) {
            instance = new ConfigurationService();
        }

        return instance;
    }

    public ConfigurationService getConfiguration() {
        request = configurationInterface.getConfiguration(API_KEY);
        return this;
    }

}
