package org.spincast.todobackend.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.spincast.core.exchange.IDefaultRequestContext;
import org.spincast.core.json.JsonArray;
import org.spincast.core.json.JsonManager;
import org.spincast.core.utils.ContentTypeDefaults;
import org.spincast.core.utils.SpincastStatics;
import org.spincast.core.websocket.IDefaultWebsocketContext;
import org.spincast.plugins.httpclient.IHttpResponse;
import org.spincast.shaded.org.apache.http.HttpStatus;
import org.spincast.testing.core.SpincastIntegrationTestBase;
import org.spincast.todobackend.inmemory.App;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Integration test base class specifically made for 
 * our application.
 */
public abstract class AppIntegrationTestBase extends
                                             SpincastIntegrationTestBase<IDefaultRequestContext, IDefaultWebsocketContext> {

    @Inject
    protected JsonManager jsonManager;

    /**
     * Creates the application and returns the Guice
     * injector.
     */
    @Override
    protected Injector createInjector() {
        return App.createApp(getMainArgs(),
                             getDefaultOverridingModule(IDefaultRequestContext.class, IDefaultWebsocketContext.class));
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

        IHttpResponse response = DELETE("/").send();
        assertEquals(HttpStatus.SC_OK, response.getStatus());

        JsonArray jsonArray = getAllTodos();
        assertEquals(0, jsonArray.size());
    }

    protected JsonArray getAllTodos() throws Exception {

        IHttpResponse response = GET("/").send();
        assertEquals(HttpStatus.SC_OK, response.getStatus());

        assertEquals(ContentTypeDefaults.JSON.getMainVariationWithUtf8Charset(), response.getContentType());

        JsonArray jsonArray = getJsonManager().createArray(response.getContentAsString());
        assertNotNull(jsonArray);

        return jsonArray;
    }
}
