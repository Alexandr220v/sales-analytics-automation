package com.salesanalytics.qa.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config.properties"
})
public interface AppConfig extends Config {

    AppConfig INSTANCE = ConfigFactory.create(AppConfig.class);

    @Config.Key("api.base.url")
    String apiBaseUrl();

    @Config.Key("ui.base.url")
    String uiBaseUrl();

    @Config.Key("ui.headless")
    @Config.DefaultValue("false")
    boolean uiHeadless();
}
