package org.spincast.todobackend.inmemory.services;

import java.util.List;

import org.spincast.core.json.JsonObject;
import org.spincast.todobackend.inmemory.models.Todo;

/**
 * Todo's service.
 */
public interface TodoService {

    /**
     * Gets all Todos, sorted by their order.
     */
    public List<Todo> getAllTodos();

    /**
     * Adds a Todo.
     */
    public Todo addTodo(Todo newTodo);

    /**
     * Deletes all Todos.
     */
    public void deleteAllTodos();

    /**
     * Gets a Todo.
     * 
     * @return the Todo or <code>null</code> if not found.
     */
    public Todo getTodo(int todoId);

    /**
     * Patches a Todo from the values of a JsonObject.
     */
    public Todo patchTodo(Todo todo, JsonObject jsonPatch);

    /**
     * Deletes a Todo.
     */
    public void deleteTodo(int todoId);

}
