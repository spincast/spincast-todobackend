package org.spincast.todobackend.inmemory.models.validators;

import org.spincast.core.validation.ValidationSet;
import org.spincast.core.validation.ValidationFactory;
import org.spincast.todobackend.inmemory.models.Todo;

import com.google.inject.Inject;

/**
 * Todo validator.
 */
public class TodoValidator {

    private final ValidationFactory validationFactory;

    @Inject
    public TodoValidator(ValidationFactory validationFactory) {
        this.validationFactory = validationFactory;
    }

    protected ValidationFactory getValidationFactory() {
        return this.validationFactory;
    }

    public ValidationSet validate(Todo objectToValidate) {

        ValidationSet validation = getValidationFactory().createValidationSet();

        //==========================================
        // Maximum 255 character for the title
        //==========================================
        String title = objectToValidate.getTitle();
        if (title != null && title.length() > 255) {
            validation.addError("title",
                                "title_maxLength",
                                "The maximum length is 255");
        }

        return validation;

    }

}
