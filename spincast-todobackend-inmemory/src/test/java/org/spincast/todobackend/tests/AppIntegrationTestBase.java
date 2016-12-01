package org.spincast.todobackend.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.spincast.core.json.JsonArray;
import org.spincast.core.json.JsonManager;
import org.spincast.core.utils.ContentTypeDefaults;
import org.spincast.core.utils.SpincastStatics;
import org.spincast.defaults.tests.AppDefaultContextesIntegrationTestBase;
import org.spincast.plugins.httpclient.HttpResponse;
import org.spincast.shaded.org.apache.http.HttpStatus;
import org.spincast.todobackend.inmemory.App;

import com.google.inject.Inject;

/**
 * Integration test base class specifically made for 
 * our application.
 */
public abstract class AppIntegrationTestBase extends AppDefaultContextesIntegrationTestBase {

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
