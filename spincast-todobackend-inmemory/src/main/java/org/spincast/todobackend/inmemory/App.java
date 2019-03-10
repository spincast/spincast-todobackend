package org.spincast.todobackend.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spincast.core.server.Server;
import org.spincast.defaults.bootstrapping.Spincast;
import org.spincast.plugins.routing.DefaultRouter;
import org.spincast.todobackend.inmemory.controllers.TodoController;

import com.google.inject.Inject;

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
        Spincast.configure()
                .module(new AppModule())
                .init(args);
    }

    //==========================================
    // The application
    //==========================================
    private final Server server;
    private final DefaultRouter router;
    private final TodoController todoController;

    /**
     * The application constructor which Guice will call
     * with the required dependencies.
     */
    @Inject
    public App(Server server,
               DefaultRouter router,
               TodoController todoController) {
        this.server = server;
        this.router = router;
        this.todoController = todoController;
    }

    protected Server getServer() {
        return this.server;
    }

    protected DefaultRouter getRouter() {
        return this.router;
    }

    protected TodoController getTodoController() {
        return this.todoController;
    }

    /**
     * Adds the routes and starts the server.
     */
    @Inject
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

        getRouter().GET("/").handle(getTodoController()::getTodosHandler);
        getRouter().POST("/").handle(getTodoController()::addTodoHandler);
        getRouter().DELETE("/").handle(getTodoController()::deleteTodosHandler);

        getRouter().GET("/${todoId:<N>}").handle(getTodoController()::getTodoHandler);
        getRouter().PATCH("/${todoId:<N>}").handle(getTodoController()::patchTodoHandler);
        getRouter().DELETE("/${todoId:<N>}").handle(getTodoController()::deleteTodoHandler);
    }

}
