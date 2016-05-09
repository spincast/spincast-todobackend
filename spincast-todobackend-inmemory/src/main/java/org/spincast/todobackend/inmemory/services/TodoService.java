package org.spincast.todobackend.inmemory.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.spincast.core.exceptions.PublicException;
import org.spincast.core.json.IJsonObject;
import org.spincast.plugins.validation.FormatType;
import org.spincast.plugins.validation.IValidator;
import org.spincast.plugins.validation.IValidatorFactory;
import org.spincast.todobackend.inmemory.models.ITodo;
import org.spincast.todobackend.inmemory.repositories.ITodoRepository;

import com.google.inject.Inject;

/**
 * Todo's service implementation.
 */
public class TodoService implements ITodoService {

    private final ITodoRepository todoRepository;
    private final IValidatorFactory<ITodo> todoValidatorFactory;

    /**
     * Constructor
     */
    @Inject
    public TodoService(ITodoRepository todoRepository,
                       IValidatorFactory<ITodo> todoValidatorFactory) {
        this.todoRepository = todoRepository;
        this.todoValidatorFactory = todoValidatorFactory;
    }

    protected ITodoRepository getTodoRepository() {
        return this.todoRepository;
    }

    protected IValidatorFactory<ITodo> getTodoValidatorFactory() {
        return this.todoValidatorFactory;
    }

    @Override
    public List<ITodo> getAllTodos() {

        Collection<ITodo> todos = getTodoRepository().getAllTodos();

        //==========================================
        // Sort the Todos by their order.
        //==========================================
        List<ITodo> todosOrdered = sortTodosByOrder(todos);

        return todosOrdered;
    }

    /**
     * Sorts the Todos by their order.
     */
    protected List<ITodo> sortTodosByOrder(Collection<ITodo> todos) {
        if(todos == null) {
            return null;
        }

        List<ITodo> todosList = new ArrayList<ITodo>(todos);
        Collections.sort(todosList, new Comparator<ITodo>() {

            @Override
            public int compare(ITodo todo1, ITodo todo2) {
                return Integer.compare(todo1.getOrder(), todo2.getOrder());
            }
        });

        return todosList;
    }

    @Override
    public ITodo addTodo(ITodo newTodo) {

        Objects.requireNonNull(newTodo, "The Todo can't be NULL");

        validateTodo(newTodo);

        ITodo todo = getTodoRepository().addTodo(newTodo);

        //==========================================
        // Return the Todo with its generated id.
        //==========================================
        return todo;
    }

    /**
     * Validates the Todo and throws an exception if something is
     * invalid.
     */
    protected void validateTodo(ITodo newTodo) {

        IValidator todoValidator = getTodoValidatorFactory().create(newTodo);
        if(!todoValidator.isValid()) {

            StringBuilder messageBuilder = new StringBuilder("The Todo to add is invalid.\nErrors:\n\n");

            messageBuilder.append(todoValidator.getErrorsFormatted(FormatType.PLAIN_TEXT));

            throw new PublicException(messageBuilder.toString());
        }
    }

    @Override
    public void deleteAllTodos() {
        getTodoRepository().deleteAllTodos();
    }

    @Override
    public ITodo getTodo(int todoId) {
        ITodo todo = getTodoRepository().getTodo(todoId);
        return todo;
    }

    @Override
    public ITodo patchTodo(ITodo todo, IJsonObject jsonPatch) {

        if(todo == null || jsonPatch == null) {
            return todo;
        }

        if(jsonPatch.isKeyExists("title")) {
            todo.setTitle(jsonPatch.getString("title"));
        }

        if(jsonPatch.isKeyExists("completed")) {
            todo.setCompleted(jsonPatch.getBoolean("completed"));
        }

        if(jsonPatch.isKeyExists("order")) {
            todo.setOrder(jsonPatch.getInteger("order"));
        }

        return todo;
    }

    @Override
    public void deleteTodo(int todoId) {
        getTodoRepository().deleteTodo(todoId);
    }

}
