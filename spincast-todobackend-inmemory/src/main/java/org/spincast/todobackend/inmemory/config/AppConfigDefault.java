package org.spincast.todobackend.inmemory.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spincast.plugins.config.SpincastConfigDefault;
import org.spincast.plugins.config.SpincastConfigPluginConfig;

import com.google.inject.Inject;

/**
 * Implementation of our application's configurations class.
 * 
 * We extend SpincastConfigPropsFileBased which is a class provided by the 
 * "Spincast Config" plugin and which allows to define
 * configurations in an external .yaml file.
 */
public class AppConfigDefault extends SpincastConfigDefault implements AppConfig {

    @Inject
    protected AppConfigDefault(SpincastConfigPluginConfig spincastConfigPluginConfig) {
        super(spincastConfigPluginConfig);
    }

    protected final Logger logger = LoggerFactory.getLogger(AppConfigDefault.class);

    @Override
    public int getHttpServerPort() {
        return getInteger("spincast.server.port", super.getHttpServerPort());
    }

    @Override
    public String getPublicUrlBase() {
        return getString("spincast.publicAccess.urlBase", "http://localhost:" + getHttpServerPort());
    }

    @Override
    public String getTodoUrlTemplate() {

        //==========================================
        // A template for a Todo's URL looks like 
        // "http://example.com/todos/{{id}}".
        //==========================================
        return getPublicUrlBase() + "/{{id}}";
    }

}
