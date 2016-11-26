package org.spincast.todobackend.inmemory.repositories;

import java.util.Collection;

import org.spincast.todobackend.inmemory.models.Todo;

/**
 * Todo's repository.
 */
public interface TodoRepository {

    /**
     * Gets all Todos.
     */
    public Collection<Todo> getAllTodos();

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
     * Deletes a Todos.
     */
    public void deleteTodo(int todoId);

}
