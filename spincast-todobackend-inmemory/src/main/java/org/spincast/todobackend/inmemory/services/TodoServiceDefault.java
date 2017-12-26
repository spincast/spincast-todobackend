package org.spincast.todobackend.inmemory.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.spincast.core.exceptions.PublicExceptionDefault;
import org.spincast.core.json.JsonObject;
import org.spincast.core.validation.ValidationSet;
import org.spincast.core.validation.ValidationMessageFormatType;
import org.spincast.todobackend.inmemory.models.Todo;
import org.spincast.todobackend.inmemory.models.validators.TodoValidator;
import org.spincast.todobackend.inmemory.repositories.TodoRepository;

import com.google.inject.Inject;

/**
 * Todo's service implementation.
 */
public class TodoServiceDefault implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoValidator todoValidator;

    /**
     * Constructor
     */
    @Inject
    public TodoServiceDefault(TodoRepository todoRepository,
                              TodoValidator todoValidator) {
        this.todoRepository = todoRepository;
        this.todoValidator = todoValidator;
    }

    protected TodoRepository getTodoRepository() {
        return this.todoRepository;
    }

    protected TodoValidator getTodoValidator() {
        return this.todoValidator;
    }

    @Override
    public List<Todo> getAllTodos() {

        Collection<Todo> todos = getTodoRepository().getAllTodos();

        //==========================================
        // Sort the Todos by their order.
        //==========================================
        List<Todo> todosOrdered = sortTodosByOrder(todos);

        return todosOrdered;
    }

    /**
     * Sorts the Todos by their order.
     */
    protected List<Todo> sortTodosByOrder(Collection<Todo> todos) {

        if (todos == null) {
            return null;
        }

        List<Todo> todosList = new ArrayList<Todo>(todos);
        Collections.sort(todosList, new Comparator<Todo>() {

            @Override
            public int compare(Todo todo1, Todo todo2) {
                return Integer.compare(todo1.getOrder(), todo2.getOrder());
            }
        });

        return todosList;
    }

    @Override
    public Todo addTodo(Todo newTodo) {

        Objects.requireNonNull(newTodo, "The Todo can't be NULL");

        //==========================================
        // Validates the Todo before saving it.
        //==========================================
        validateTodo(newTodo);

        Todo todo = getTodoRepository().addTodo(newTodo);

        //==========================================
        // Return the Todo with its generated id.
        //==========================================
        return todo;
    }

    /**
     * Validates the Todo: throws an exception if something is
     * invalid.
     */
    protected void validateTodo(Todo newTodo) {

        ValidationSet validation = getTodoValidator().validate(newTodo);

        if (!validation.isValid()) {

            StringBuilder messageBuilder = new StringBuilder("The Todo to add is invalid.\nErrors:\n\n");

            messageBuilder.append(validation.getMessagesFormatted(ValidationMessageFormatType.PLAIN_TEXT));

            throw new PublicExceptionDefault(messageBuilder.toString());
        }
    }

    @Override
    public void deleteAllTodos() {
        getTodoRepository().deleteAllTodos();
    }

    @Override
    public Todo getTodo(int todoId) {
        Todo todo = getTodoRepository().getTodo(todoId);
        return todo;
    }

    @Override
    public Todo patchTodo(Todo todo, JsonObject jsonPatch) {

        if (todo == null || jsonPatch == null) {
            return todo;
        }

        if (jsonPatch.isElementExists("title")) {
            todo.setTitle(jsonPatch.getString("title"));
        }

        if (jsonPatch.isElementExists("completed")) {
            todo.setCompleted(jsonPatch.getBoolean("completed"));
        }

        if (jsonPatch.isElementExists("order")) {
            todo.setOrder(jsonPatch.getInteger("order"));
        }

        return todo;
    }

    @Override
    public void deleteTodo(int todoId) {
        getTodoRepository().deleteTodo(todoId);
    }

}
