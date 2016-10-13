package org.spincast.todobackend.inmemory.models.validators;

import org.spincast.core.validation.ValidationSet;
import org.spincast.core.validation.ValidatorBase;
import org.spincast.todobackend.inmemory.models.Todo;

/**
 * Todo validator.
 */
public class TodoValidator extends ValidatorBase {

    public ValidationSet validate(Todo objectToValidate) {

        ValidationSet validation = newValidationBuilder();

        //==========================================
        // Maximum 255 character for the title
        //==========================================
        validation.validationMaxLength(255).key("title").element(objectToValidate.getTitle()).validate();

        return validation;

    }

}
