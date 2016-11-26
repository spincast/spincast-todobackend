package org.spincast.todobackend.inmemory.config;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spincast.core.guice.MainArgs;
import org.spincast.core.utils.SpincastUtils;
import org.spincast.plugins.configpropsfile.SpincastConfigPropsFileBased;
import org.spincast.plugins.configpropsfile.SpincastConfigPropsFileBasedConfig;

import com.google.inject.Inject;

/**
 * Implementation of our application's configurations class.
 * 
 * We extend SpincastConfigPropsFileBased which is a class provided by the 
 * "Spincast Properties File Config" plugin and which allows to define
 * configurations in an external .properties file.
 */
public class AppConfigDefault extends SpincastConfigPropsFileBased implements AppConfig {

    protected final Logger logger = LoggerFactory.getLogger(AppConfigDefault.class);

    /**
     * Constructor
     */
    @Inject
    public AppConfigDefault(SpincastUtils spincastUtils,
                            @MainArgs @Nullable String[] mainArgs,
                            SpincastConfigPropsFileBasedConfig pluginConfig) {
        super(spincastUtils, mainArgs, pluginConfig);
    }

    @Override
    public String getTodoUrlTemplate() {

        //==========================================
        // We use the URL prefix found in a config file,
        // or fallback to "localhost" if no config file is found.
        //==========================================
        String schemeHostPort = getConfig("app.server.scheme_host_port", "http://localhost:" + getHttpServerPort());

        //==========================================
        // A template for a Todo's URL looks like 
        // "http://example.com/todos/{{id}}".
        //==========================================
        return schemeHostPort + "/{{id}}";
    }

}
