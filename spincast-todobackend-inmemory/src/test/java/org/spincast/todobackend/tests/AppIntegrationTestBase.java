package org.spincast.todobackend.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.spincast.core.config.SpincastConfig;
import org.spincast.core.guice.SpincastAppBuilderGuiceTweaker;
import org.spincast.core.guice.SpincastGuiceModuleBase;
import org.spincast.core.json.JsonArray;
import org.spincast.core.json.JsonManager;
import org.spincast.core.utils.ContentTypeDefaults;
import org.spincast.core.utils.SpincastStatics;
import org.spincast.defaults.tests.IntegrationTestAppDefaultContextsBase;
import org.spincast.plugins.httpclient.HttpResponse;
import org.spincast.shaded.org.apache.http.HttpStatus;
import org.spincast.testing.core.utils.SpincastTestConfig;
import org.spincast.todobackend.inmemory.App;
import org.spincast.todobackend.inmemory.config.AppConfig;

import com.google.inject.Inject;
import com.google.inject.Scopes;

/**
 * Integration test base class specifically made for 
 * our application.
 */
public abstract class AppIntegrationTestBase extends IntegrationTestAppDefaultContextsBase {

    @Override
    protected boolean isAddGuiceTweakerTestingConfigModule() {
        return false;
    }

    protected static class AppConfigTesting extends SpincastTestConfig implements AppConfig {

        @Override
        public String getTodoUrlTemplate() {
            //==========================================
            // A template for a Todo's URL looks like 
            // "http://example.com/todos/{{id}}".
            //==========================================
            return "http://" + getServerHost() + ":" + getHttpServerPort() + "/{{id}}";
        }
    }

    @Override
    protected SpincastAppBuilderGuiceTweaker createSpincastAppBuilderGuiceTweaker() {
        SpincastAppBuilderGuiceTweaker tweaker = super.createSpincastAppBuilderGuiceTweaker();
        tweaker.moduleOverride(new SpincastGuiceModuleBase() {

            @Override
            protected void configure() {
                bind(AppConfigTesting.class).in(Scopes.SINGLETON);
                bind(AppConfig.class).to(AppConfigTesting.class).in(Scopes.SINGLETON);
                bind(SpincastConfig.class).to(AppConfigTesting.class).in(Scopes.SINGLETON);
            }
        });
        return tweaker;
    }

    @Inject
    protected JsonManager jsonManager;

    @Override
    protected void startApp() {
        App.main(getMainArgs());
    }

    protected String[] getMainArgs() {
        return null;
    }

    @Override
    public void beforeClass() {
        super.beforeClass();

        try {
            deleteAllTodos();
        } catch(Exception ex) {
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
