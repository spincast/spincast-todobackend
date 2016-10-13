package org.spincast.todobackend.inmemory.services;

import java.util.List;

import org.spincast.core.json.JsonObject;
import org.spincast.todobackend.inmemory.models.ITodo;

/**
 * Todo's service.
 */
public interface ITodoService {

    /**
     * Gets all Todos, sorted by their order.
     */
    public List<ITodo> getAllTodos();

    /**
     * Adds a Todo.
     */
    public ITodo addTodo(ITodo newTodo);

    /**
     * Deletes all Todos.
     */
    public void deleteAllTodos();

    /**
     * Gets a Todo.
     * 
     * @return the Todo or <code>null</code> if not found.
     */
    public ITodo getTodo(int todoId);

    /**
     * Patches a Todo from the values of a JsonObject.
     */
    public ITodo patchTodo(ITodo todo, JsonObject jsonPatch);

    /**
     * Deletes a Todo.
     */
    public void deleteTodo(int todoId);

}
