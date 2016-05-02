package org.spincast.todobackend.inmemory.controllers;

import org.spincast.core.exchange.IDefaultRequestContext;

/**
 * Todo controller.
 */
public interface ITodoController {

    /**
     * Gets all Todos - route handler
     */
    public void getTodosHandler(IDefaultRequestContext context);

    /**
     * Adds a Todo - route handler
     */
    public void addTodoHandler(IDefaultRequestContext context);

    /**
     * Deletes all Todos - route handler
     */
    public void deleteTodosHandler(IDefaultRequestContext context);

    /**
     * Gets a Todo - route handler
     */
    public void getTodoHandler(IDefaultRequestContext context);

    /**
     * Patches a Todo - route handler
     */
    public void patchTodoHandler(IDefaultRequestContext context);

    /**
     * Deletes a Todo - route handler
     */
    public void deleteTodoHandler(IDefaultRequestContext context);

}
