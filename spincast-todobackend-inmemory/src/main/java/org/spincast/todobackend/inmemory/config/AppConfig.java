package org.spincast.todobackend.inmemory.config;

import org.spincast.core.config.SpincastConfig;

/**
 * Application's custom configurations.
 * 
 * <p>
 * Extends <code>SpincastConfig</code> so we can use this class 
 * as the implementation for this interface too.
 * </p>
 */
public interface AppConfig extends SpincastConfig {

    /**
     * The template for a Todo's unique URL. A placeholder
     * for the Todo's id has to be included.
     * 
     * For example: "http://example.com/todos/{{id}}"
     */
    String getTodoUrlTemplate();
}
