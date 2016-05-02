package org.spincast.todobackend.inmemory.repositories;

import java.util.Collection;

import org.spincast.todobackend.inmemory.models.ITodo;

/**
 * Todo's repository.
 */
public interface ITodoRepository {

    /**
     * Gets all Todos.
     */
    public Collection<ITodo> getAllTodos();

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
     * Deletes a Todos.
     */
    public void deleteTodo(int todoId);

}
