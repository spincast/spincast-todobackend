package org.spincast.todobackend.inmemory.models.validators;

import org.spincast.core.validation.IValidationResultBuilderFactory;
import org.spincast.core.validation.IValidationResult;
import org.spincast.core.validation.IValidationResultBuilder;
import org.spincast.core.validation.IValidator;
import org.spincast.todobackend.inmemory.models.ITodo;

import com.google.inject.Inject;

/**
 * Todo validator
 */
public class TodoValidator implements IValidator<ITodo> {

    private final IValidationResultBuilderFactory<ITodo> validationResultBuilderFactory;

    /**
     * Constructor
     */
    @Inject
    public TodoValidator(IValidationResultBuilderFactory<ITodo> validationResultBuilderFactory) {
        this.validationResultBuilderFactory = validationResultBuilderFactory;
    }

    protected IValidationResultBuilderFactory<ITodo> getValidationResultBuilderFactory() {
        return this.validationResultBuilderFactory;
    }

    @Override
    public IValidationResult validate(ITodo objectToValidate) {

        IValidationResultBuilder<ITodo> builder = getValidationResultBuilderFactory().create(objectToValidate);

        //==========================================
        // Maximum 255 characters for the title
        //==========================================
        builder.validateMaxLength("title", objectToValidate.getTitle(), 255);

        return builder.getResult();
    }

}
