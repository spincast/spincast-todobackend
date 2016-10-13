package org.spincast.todobackend.inmemory;

import org.spincast.core.config.SpincastConfig;
import org.spincast.defaults.guice.SpincastDefaultGuiceModule;
import org.spincast.todobackend.inmemory.config.AppConfigDefault;
import org.spincast.todobackend.inmemory.config.AppConfig;
import org.spincast.todobackend.inmemory.controllers.TodoController;
import org.spincast.todobackend.inmemory.controllers.TodoControllerDefault;
import org.spincast.todobackend.inmemory.repositories.TodoRepository;
import org.spincast.todobackend.inmemory.repositories.InMemoryTodoRepository;
import org.spincast.todobackend.inmemory.services.TodoService;
import org.spincast.todobackend.inmemory.services.TodoServiceDefault;

import com.google.inject.Scopes;

/**
 * The application's custom Guice module.
 * 
 * It extends "SpincastDefaultGuiceModule" so we start with
 * the default bindings.
 */
public class AppModule extends SpincastDefaultGuiceModule {

    @Override
    protected void configure() {
        super.configure();

        //==========================================
        // One instance only of our configuration class.
        //==========================================
        bind(AppConfigDefault.class).in(Scopes.SINGLETON);

        //==========================================
        // Binds our configuration implementation class to our
        // "IAppConfig" interface.
        //==========================================
        bind(AppConfig.class).to(AppConfigDefault.class).in(Scopes.SINGLETON);

        //==========================================
        // Binds our controller / service / repository.
        //==========================================
        bind(TodoController.class).to(TodoControllerDefault.class).in(Scopes.SINGLETON);
        bind(TodoService.class).to(TodoServiceDefault.class).in(Scopes.SINGLETON);
        bind(TodoRepository.class).to(InMemoryTodoRepository.class).in(Scopes.SINGLETON);

        //==========================================
        // Binds a Todo validator factory
        //==========================================
        //        install(new FactoryModuleBuilder().implement(IValidationResult.class, TodoValidator.class)
        //                                          .build(new TypeLiteral<IValidatorFactory<ITodo>>() {}));

        //==========================================
        // Binds the App itself.
        //==========================================
        bind(App.class).in(Scopes.SINGLETON);
    }

    /**
     * We tell Spincast to use our custom configuration class
     * as the implementation for its "ISpincastConfig" component.
     */
    @Override
    protected void bindConfigPlugin() {
        bind(SpincastConfig.class).to(AppConfigDefault.class).in(Scopes.SINGLETON);
    }

}
