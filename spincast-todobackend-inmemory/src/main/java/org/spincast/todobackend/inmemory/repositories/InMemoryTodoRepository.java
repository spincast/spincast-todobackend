package org.spincast.todobackend.inmemory.repositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spincast.core.exceptions.PublicExceptionDefault;
import org.spincast.todobackend.inmemory.config.AppConstants;
import org.spincast.todobackend.inmemory.models.ITodo;

/**
 * In-memory Todo's repository implementation.
 */
public class InMemoryTodoRepository implements ITodoRepository {

    protected final Logger logger = LoggerFactory.getLogger(InMemoryTodoRepository.class);

    /**
     * The in-memory Todos.
     */
    private Map<Integer, ITodo> inMemoryTodos;

    /**
     * Dummy sequence for the Todos' ids.
     */
    private int todoIdsSequence = 1;

    protected Map<Integer, ITodo> getTodosMap() {
        if(this.inMemoryTodos == null) {
            this.inMemoryTodos = new HashMap<Integer, ITodo>();
        }
        return this.inMemoryTodos;
    }

    /**
     * Generates a dummy Todo id.
     */
    protected int getNextTodoId() {

        int id = this.todoIdsSequence;

        if(this.todoIdsSequence < Integer.MAX_VALUE) {
            this.todoIdsSequence++;
        } else {
            this.logger.warn("Integer.MAX_VALUE reached for Todos' ids. Will restart at 1!");
            this.todoIdsSequence = 1;
        }
        return id;
    }

    @Override
    public Collection<ITodo> getAllTodos() {
        return getTodosMap().values();
    }

    @Override
    public ITodo addTodo(ITodo newTodo) {

        if(getTodosMap().size() >= AppConstants.MAX_TODOS_NBR) {
            deleteAllTodos();
            throw new PublicExceptionDefault("Too many Todos, please start again...");
        }

        int id = getNextTodoId();
        newTodo.setId(id);

        getTodosMap().put(id, newTodo);

        return newTodo;
    }

    @Override
    public void deleteAllTodos() {
        getTodosMap().clear();
        this.todoIdsSequence = 1;
    }

    @Override
    public ITodo getTodo(int id) {
        return getTodosMap().get(id);
    }

    @Override
    public void deleteTodo(int todoId) {
        getTodosMap().remove(todoId);
    }

}
