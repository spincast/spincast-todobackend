package org.spincast.todobackend.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.spincast.core.config.SpincastConfig;
import org.spincast.core.json.JsonArray;
import org.spincast.core.json.JsonManager;
import org.spincast.core.utils.ContentTypeDefaults;
import org.spincast.core.utils.SpincastStatics;
import org.spincast.defaults.testing.AppBasedDefaultContextTypesTestingBase;
import org.spincast.plugins.config.SpincastConfigPluginConfig;
import org.spincast.plugins.httpclient.HttpResponse;
import org.spincast.shaded.org.apache.http.HttpStatus;
import org.spincast.testing.core.AppTestingConfigs;
import org.spincast.testing.core.utils.SpincastTestUtils;
import org.spincast.todobackend.inmemory.App;
import org.spincast.todobackend.inmemory.config.AppConfig;
import org.spincast.todobackend.inmemory.config.AppConfigDefault;

import com.google.inject.Inject;

/**
 * Integration test base class specifically made for 
 * our application.
 */
public abstract class AppIntegrationTestBase extends AppBasedDefaultContextTypesTestingBase {

    @Inject
    protected JsonManager jsonManager;

    @Override
    protected void startApp() {
        App.main(getMainArgs());
    }

    protected String[] getMainArgs() {
        return null;
    }

    /**
     * We specify our testing configurations informations.
     */
    @Override
    protected AppTestingConfigs getAppTestingConfigs() {

        return new AppTestingConfigs() {

            @Override
            public boolean isBindAppClass() {
                return true;
            }

            @Override
            public Class<? extends SpincastConfig> getSpincastConfigTestingImplementationClass() {
                return AppTestingConfig.class;
            }

            @Override
            public Class<?> getAppConfigInterface() {
                return AppConfig.class;
            }

            @Override
            public Class<?> getAppConfigTestingImplementationClass() {
                return AppTestingConfig.class;
            }
        };
    }

    /**
     * Testing config
     */
    public static class AppTestingConfig extends AppConfigDefault {

        private int serverPort = -1;

        @Inject
        public AppTestingConfig(SpincastConfigPluginConfig spincastConfigPluginConfig) {
            super(spincastConfigPluginConfig);
        }

        /**
         * We do not run in "debug" mode.
         */
        @Override
        public boolean isDebugEnabled() {
            return false;
        }

        @Override
        public String getServerHost() {
            return "localhost";
        }

        /**
         * We use a free port.
         */
        @Override
        public int getHttpServerPort() {

            if (this.serverPort == -1) {

                //==========================================
                // We reserve 44419 for the default configuration.
                //==========================================
                do {
                    this.serverPort = SpincastTestUtils.findFreePort();
                } while (this.serverPort == 44419);
            }
            return this.serverPort;
        }

        @Override
        public String getPublicUrlBase() {
            return "http://" + getServerHost() + ":" + getHttpServerPort();
        }

        @Override
        public boolean isValidateLocalhostHost() {
            return false;
        }

        @Override
        public boolean isEnableCookiesValidator() {
            return false;
        }
    }


    @Override
    public void beforeClass() {
        super.beforeClass();

        try {
            deleteAllTodos();
        } catch (Exception ex) {
            throw SpincastStatics.runtimize(ex);
        }
    }

    protected JsonManager getJsonManager() {
        return this.jsonManager;
    }

    protected void deleteAllTodos() throws Exception {

        HttpResponse response = DELETE("/").send();
        assertEquals(HttpStatus.SC_OK, response.getStatus());

        JsonArray jsonArray = getAllTodos();
        assertEquals(0, jsonArray.size());
    }

    protected JsonArray getAllTodos() throws Exception {

        HttpResponse response = GET("/").send();
        assertEquals(HttpStatus.SC_OK, response.getStatus());

        assertEquals(ContentTypeDefaults.JSON.getMainVariationWithUtf8Charset(), response.getContentType());

        JsonArray jsonArray = getJsonManager().fromStringArray(response.getContentAsString());
        assertNotNull(jsonArray);

        return jsonArray;
    }
}
