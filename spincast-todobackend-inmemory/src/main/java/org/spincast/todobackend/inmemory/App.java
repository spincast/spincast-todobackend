package org.spincast.todobackend.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spincast.core.server.IServer;
import org.spincast.plugins.routing.IDefaultRouter;
import org.spincast.todobackend.inmemory.controllers.ITodoController;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * The main class of the application. Everything starts with the
 * classic <code>main(...)</code> method.
 */
public class App {

    protected final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * The entry point for the application.
     */
    public static void main(String[] args) {
        createApp(args);
    }

    /**
     * Creates an <code>App</code> instance and returns the Guice injector. 
     * The injector can be useful for integration tests.
     */
    public static Injector createApp() {
        return createApp(null);
    }

    /**
     * Creates an <code>App</code> instance using the given
     * parameters and returns the Guice injector.
     * The injector can be useful for integration tests.
     */
    public static Injector createApp(String[] args) {

        if(args == null) {
            args = new String[]{};
        }

        Injector guice = Guice.createInjector(new AppModule());

        App app = guice.getInstance(App.class);
        app.start();

        return guice;
    }

    private final IServer server;
    private final IDefaultRouter router;
    private final ITodoController todoController;

    /**
     * The application constructor which Guice will call
     * with the required dependencies.
     */
    @Inject
    public App(IServer server,
               IDefaultRouter router,
               ITodoController todoController) {
        this.server = server;
        this.router = router;
        this.todoController = todoController;
    }

    protected IServer getServer() {
        return this.server;
    }

    protected IDefaultRouter getRouter() {
        return this.router;
    }

    protected ITodoController getTodoController() {
        return this.todoController;
    }

    /**
     * Adds the routes and starts the server.
     */
    protected void start() {
        addRoutes();
        getServer().start();

        this.logger.info("Application started!");
    }

    /**
     * Application routes.
     */
    protected void addRoutes() {

        //==========================================
        // We enable Cors on all routes
        //==========================================
        getRouter().cors();

        getRouter().GET("/").save(getTodoController()::getTodosHandler);
        getRouter().POST("/").save(getTodoController()::addTodoHandler);
        getRouter().DELETE("/").save(getTodoController()::deleteTodosHandler);

        getRouter().GET("/${todoId:<N>}").save(getTodoController()::getTodoHandler);
        getRouter().PATCH("/${todoId:<N>}").save(getTodoController()::patchTodoHandler);
        getRouter().DELETE("/${todoId:<N>}").save(getTodoController()::deleteTodoHandler);
    }

}
