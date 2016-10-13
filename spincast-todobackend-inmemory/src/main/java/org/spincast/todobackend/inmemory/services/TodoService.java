package org.spincast.todobackend.inmemory.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.spincast.core.exceptions.PublicExceptionDefault;
import org.spincast.core.json.JsonObject;
import org.spincast.core.validation.IValidationSet;
import org.spincast.core.validation.ValidationMessageFormatType;
import org.spincast.todobackend.inmemory.models.ITodo;
import org.spincast.todobackend.inmemory.models.validators.TodoValidator;
import org.spincast.todobackend.inmemory.repositories.ITodoRepository;

import com.google.inject.Inject;

/**
 * Todo's service implementation.
 */
public class TodoService implements ITodoService {

    private final ITodoRepository todoRepository;
    private final TodoValidator todoValidator;

    /**
     * Constructor
     */
    @Inject
    public TodoService(ITodoRepository todoRepository,
                       TodoValidator todoValidator) {
        this.todoRepository = todoRepository;
        this.todoValidator = todoValidator;
    }

    protected ITodoRepository getTodoRepository() {
        return this.todoRepository;
    }

    protected TodoValidator getTodoValidator() {
        return this.todoValidator;
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

        //==========================================
        // Validates the Todo before saving it.
        //==========================================
        validateTodo(newTodo);

        ITodo todo = getTodoRepository().addTodo(newTodo);

        //==========================================
        // Return the Todo with its generated id.
        //==========================================
        return todo;
    }

    /**
     * Validates the Todo: throws an exception if something is
     * invalid.
     */
    protected void validateTodo(ITodo newTodo) {

        IValidationSet validationResult = getTodoValidator().validate(newTodo);

        if(!validationResult.isValid()) {

            StringBuilder messageBuilder = new StringBuilder("The Todo to add is invalid.\nErrors:\n\n");

            messageBuilder.append(validationResult.getMessagesFormatted(ValidationMessageFormatType.PLAIN_TEXT));

            throw new PublicExceptionDefault(messageBuilder.toString());
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
    public ITodo patchTodo(ITodo todo, JsonObject jsonPatch) {

        if(todo == null || jsonPatch == null) {
            return todo;
        }

        if(jsonPatch.isElementExists("title")) {
            todo.setTitle(jsonPatch.getString("title"));
        }

        if(jsonPatch.isElementExists("completed")) {
            todo.setCompleted(jsonPatch.getBoolean("completed"));
        }

        if(jsonPatch.isElementExists("order")) {
            todo.setOrder(jsonPatch.getInteger("order"));
        }

        return todo;
    }

    @Override
    public void deleteTodo(int todoId) {
        getTodoRepository().deleteTodo(todoId);
    }

}
