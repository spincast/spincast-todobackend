package org.spincast.todobackend.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.spincast.core.validation.ValidationSet;
import org.spincast.shaded.org.apache.commons.lang3.StringUtils;
import org.spincast.todobackend.inmemory.config.AppConfig;
import org.spincast.todobackend.inmemory.config.AppConstants;
import org.spincast.todobackend.inmemory.models.Todo;
import org.spincast.todobackend.inmemory.models.TodoDefault;
import org.spincast.todobackend.inmemory.models.validators.TodoValidator;
import org.spincast.todobackend.inmemory.repositories.InMemoryTodoRepository;

import com.google.inject.Inject;

/**
 * Various tests.
 */
public class OtherTest extends AppIntegrationTestBase {

    @Inject
    protected AppConfig appConfig;

    @Inject
    protected TodoValidator todoValidator;

    protected AppConfig getAppConfig() {
        return this.appConfig;
    }

    protected TodoValidator getTodoValidator() {
        return this.todoValidator;
    }

    /**
     * Test repository.
     */
    protected InMemoryTodoRepository memoryTodoRepository = new InMemoryTodoRepository();

    @Before
    public void clearRepo() {
        this.memoryTodoRepository.deleteAllTodos();
        assert (this.memoryTodoRepository.getAllTodos().size() == 0);
    }

    @Test
    public void testingConfigAreWorkingProperly() throws Exception {

        int port = getAppConfig().getHttpServerPort();
        assertNotEquals(44419, port);

        port = getSpincastConfig().getHttpServerPort();
        assertNotEquals(44419, port);
    }

    //==========================================
    // Maximum number of Todos.
    //==========================================
    @Test
    public void maxTodoNbr() throws Exception {

        for (int i = 0; i < AppConstants.MAX_TODOS_NBR; i++) {
            this.memoryTodoRepository.addTodo(new TodoDefault());
        }

        try {
            this.memoryTodoRepository.addTodo(new TodoDefault());
            fail();
        } catch (Exception ex) {
        }

        assertEquals(0, this.memoryTodoRepository.getAllTodos().size());

    }

    //==========================================
    // The maximum id sequence
    //==========================================
    @Test
    public void maxIdSequence() throws Exception {

        Field todoIdsSequenceField = this.memoryTodoRepository.getClass().getDeclaredField("todoIdsSequence");
        assertNotNull(todoIdsSequenceField);

        todoIdsSequenceField.setAccessible(true);
        todoIdsSequenceField.set(this.memoryTodoRepository, Integer.MAX_VALUE - 1);

        Todo addTodo = this.memoryTodoRepository.addTodo(new TodoDefault());
        assertNotNull(addTodo);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE - 1), addTodo.getId());

        addTodo = this.memoryTodoRepository.addTodo(new TodoDefault());
        assertNotNull(addTodo);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), addTodo.getId());

        //==========================================
        // Sequence should have been restarted!
        //==========================================
        addTodo = this.memoryTodoRepository.addTodo(new TodoDefault());
        assertNotNull(addTodo);
        assertEquals(Integer.valueOf(1), addTodo.getId());
    }

    //==========================================
    // Title's maximum length (255)
    //==========================================
    @Test
    public void titleMaxLength() throws Exception {

        Todo todo = new TodoDefault();
        todo.setTitle(StringUtils.repeat("x", 255));

        ValidationSet validationResult = getTodoValidator().validate(todo);
        assertTrue(validationResult.isValid());

        todo.setTitle(StringUtils.repeat("x", 256));

        validationResult = getTodoValidator().validate(todo);
        assertFalse(validationResult.isValid());
    }

}
