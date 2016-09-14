package org.spincast.todobackend.inmemory.controllers;

import java.util.List;

import org.spincast.core.exceptions.NotFoundException;
import org.spincast.core.exchange.IDefaultRequestContext;
import org.spincast.core.json.IJsonObjectMutable;
import org.spincast.todobackend.inmemory.models.ITodo;
import org.spincast.todobackend.inmemory.models.Todo;
import org.spincast.todobackend.inmemory.services.ITodoService;

import com.google.inject.Inject;

/**
 * Todo's controller implementation.2
 */
public class TodoController implements ITodoController {

    private final ITodoService todoService;

    /**
     * Constructor
     */
    @Inject
    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    protected ITodoService getTodoService() {
        return this.todoService;
    }

    /**
     * Get the "todoId" id of a Todo, parsed from the URL.
     */
    protected int getTodoIdFromPathParam(IDefaultRequestContext context) {

        String todoIdStr = context.request().getPathParam("todoId");
        int todoId = Integer.parseInt(todoIdStr);

        return todoId;
    }

    @Override
    public void getTodosHandler(IDefaultRequestContext context) {

        List<ITodo> todos = getTodoService().getAllTodos();
        context.response().sendJsonObj(todos);
    }

    @Override
    public void addTodoHandler(IDefaultRequestContext context) {

        ITodo newTodo = context.request().getJsonBody(Todo.class);
        if(newTodo != null) {
            newTodo = getTodoService().addTodo(newTodo);
        }
        context.response().sendJsonObj(newTodo);
    }

    @Override
    public void deleteTodosHandler(IDefaultRequestContext context) {
        getTodoService().deleteAllTodos();
    }

    @Override
    public void getTodoHandler(IDefaultRequestContext context) {

        int todoId = getTodoIdFromPathParam(context);

        ITodo todo = getTodoService().getTodo(todoId);
        if(todo == null) {
            throw new NotFoundException();
        }

        context.response().sendJsonObj(todo);
    }

    @Override
    public void patchTodoHandler(IDefaultRequestContext context) {

        int todoId = getTodoIdFromPathParam(context);

        ITodo todo = getTodoService().getTodo(todoId);
        if(todo == null) {
            throw new NotFoundException();
        }

        IJsonObjectMutable jsonPatch = context.request().getJsonBodyAsJsonObject();

        todo = getTodoService().patchTodo(todo, jsonPatch);

        context.response().sendJsonObj(todo);
    }

    @Override
    public void deleteTodoHandler(IDefaultRequestContext context) {

        int todoId = getTodoIdFromPathParam(context);
        getTodoService().deleteTodo(todoId);
    }

}
