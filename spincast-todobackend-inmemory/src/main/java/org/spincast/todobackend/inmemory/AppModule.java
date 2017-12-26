package org.spincast.todobackend.inmemory;

import org.spincast.core.config.SpincastConfig;
import org.spincast.core.guice.SpincastGuiceModuleBase;
import org.spincast.todobackend.inmemory.config.AppConfig;
import org.spincast.todobackend.inmemory.config.AppConfigDefault;
import org.spincast.todobackend.inmemory.controllers.TodoController;
import org.spincast.todobackend.inmemory.controllers.TodoControllerDefault;
import org.spincast.todobackend.inmemory.models.validators.TodoValidator;
import org.spincast.todobackend.inmemory.repositories.InMemoryTodoRepository;
import org.spincast.todobackend.inmemory.repositories.TodoRepository;
import org.spincast.todobackend.inmemory.services.TodoService;
import org.spincast.todobackend.inmemory.services.TodoServiceDefault;

import com.google.inject.Scopes;

/**
 * The application's custom Guice module.
 */
public class AppModule extends SpincastGuiceModuleBase {

    @Override
    protected void configure() {

        //==========================================
        // One instance only of our configuration class.
        //==========================================
        bind(AppConfigDefault.class).in(Scopes.SINGLETON);

        //==========================================
        // Binds our configuration implementation class to our
        // "AppConfig" interface.
        //==========================================
        bind(AppConfig.class).to(AppConfigDefault.class).in(Scopes.SINGLETON);

        //==========================================
        // Override the "SpincastConfig" binding too so
        // it uses our configuration implementation.
        //==========================================
        bind(SpincastConfig.class).to(AppConfigDefault.class).in(Scopes.SINGLETON);

        //==========================================
        // Binds our controller / service / repository.
        //==========================================
        bind(TodoController.class).to(TodoControllerDefault.class).in(Scopes.SINGLETON);
        bind(TodoService.class).to(TodoServiceDefault.class).in(Scopes.SINGLETON);
        bind(TodoRepository.class).to(InMemoryTodoRepository.class).in(Scopes.SINGLETON);
        bind(TodoValidator.class).in(Scopes.SINGLETON);

        //==========================================
        // Binds the App itself.
        //==========================================
        bind(App.class).in(Scopes.SINGLETON);
    }

}
