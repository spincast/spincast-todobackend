package org.spincast.todobackend.inmemory.controllers;

import java.util.List;

import org.spincast.core.exceptions.NotFoundException;
import org.spincast.core.exchange.DefaultRequestContext;
import org.spincast.core.json.JsonObject;
import org.spincast.todobackend.inmemory.models.Todo;
import org.spincast.todobackend.inmemory.models.TodoDefault;
import org.spincast.todobackend.inmemory.services.TodoService;

import com.google.inject.Inject;

/**
 * Todo's controller implementation.
 */
public class TodoControllerDefault implements TodoController {

    private final TodoService todoService;

    /**
     * Constructor
     */
    @Inject
    public TodoControllerDefault(TodoService todoService) {
        this.todoService = todoService;
    }

    protected TodoService getTodoService() {
        return this.todoService;
    }

    /**
     * Get the "todoId" id of a Todo, parsed from the URL.
     */
    protected int getTodoIdFromPathParam(DefaultRequestContext context) {

        String todoIdStr = context.request().getPathParam("todoId");
        int todoId = Integer.parseInt(todoIdStr);

        return todoId;
    }

    @Override
    public void getTodosHandler(DefaultRequestContext context) {

        List<Todo> todos = getTodoService().getAllTodos();
        context.response().sendJson(todos);
    }

    @Override
    public void addTodoHandler(DefaultRequestContext context) {

        Todo newTodo = context.request().getJsonBody(TodoDefault.class);
        if(newTodo != null) {
            newTodo = getTodoService().addTodo(newTodo);
        }
        context.response().sendJson(newTodo);
    }

    @Override
    public void deleteTodosHandler(DefaultRequestContext context) {
        getTodoService().deleteAllTodos();
    }

    @Override
    public void getTodoHandler(DefaultRequestContext context) {

        int todoId = getTodoIdFromPathParam(context);

        Todo todo = getTodoService().getTodo(todoId);
        if(todo == null) {
            throw new NotFoundException();
        }

        context.response().sendJson(todo);
    }

    @Override
    public void patchTodoHandler(DefaultRequestContext context) {

        int todoId = getTodoIdFromPathParam(context);

        Todo todo = getTodoService().getTodo(todoId);
        if(todo == null) {
            throw new NotFoundException();
        }

        JsonObject jsonPatch = context.request().getJsonBody();

        todo = getTodoService().patchTodo(todo, jsonPatch);

        context.response().sendJson(todo);
    }

    @Override
    public void deleteTodoHandler(DefaultRequestContext context) {

        int todoId = getTodoIdFromPathParam(context);
        getTodoService().deleteTodo(todoId);
    }

}
