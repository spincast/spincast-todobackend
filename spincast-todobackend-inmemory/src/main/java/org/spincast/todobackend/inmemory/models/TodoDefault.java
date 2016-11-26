package org.spincast.todobackend.inmemory.models;

import java.util.HashMap;
import java.util.Map;

import org.spincast.core.templating.TemplatingEngine;
import org.spincast.todobackend.inmemory.config.AppConfig;

import com.google.inject.Inject;

/**
 * The Todo model implementation.
 */
public class TodoDefault implements Todo {

    private Integer id;
    private String title;
    private boolean completed = false;
    private int order = 0;
    private String url;

    private AppConfig appConfig;
    private TemplatingEngine templatingEngine;

    /**
     * In Spincast, dependencies are injected in
     * Objects once deserialized using 
     * {@link org.spincast.core.json.JsonManager JsonManager}
     * or {@link org.spincast.core.xml.XmlManager XmlManager}.
     */
    @Inject
    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Inject
    public void setTemplatingEngine(TemplatingEngine templatingEngine) {
        this.templatingEngine = templatingEngine;
    }

    protected AppConfig getAppConfig() {
        return this.appConfig;
    }

    protected TemplatingEngine getTemplatingEngine() {
        return this.templatingEngine;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUrl() {

        if(this.url == null) {

            if(this.id == null) {
                throw new RuntimeException("The Todo has to be saved first to have an id and an unique URL!");
            }

            String urlTemplate = getAppConfig().getTodoUrlTemplate();

            //==========================================
            // We could do a simple "String.replace()" here, but 
            // let's see how the templating engine can be used
            // with inline content!
            //==========================================
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", getId());
            this.url = getTemplatingEngine().evaluate(urlTemplate, params);
        }
        return this.url;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isCompleted() {
        return this.completed;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

}
