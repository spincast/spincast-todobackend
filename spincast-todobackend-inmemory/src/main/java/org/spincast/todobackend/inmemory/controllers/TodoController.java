package org.spincast.todobackend.inmemory.controllers;

import org.spincast.core.exchange.DefaultRequestContext;

/**
 * Todo controller.
 */
public interface TodoController {

    /**
     * Gets all Todos - route handler
     */
    public void getTodosHandler(DefaultRequestContext context);

    /**
     * Adds a Todo - route handler
     */
    public void addTodoHandler(DefaultRequestContext context);

    /**
     * Deletes all Todos - route handler
     */
    public void deleteTodosHandler(DefaultRequestContext context);

    /**
     * Gets a Todo - route handler
     */
    public void getTodoHandler(DefaultRequestContext context);

    /**
     * Patches a Todo - route handler
     */
    public void patchTodoHandler(DefaultRequestContext context);

    /**
     * Deletes a Todo - route handler
     */
    public void deleteTodoHandler(DefaultRequestContext context);

}
