package org.spincast.todobackend.inmemory.models.validators;

import org.spincast.core.validation.IValidationSet;
import org.spincast.core.validation.IValidationResult;
import org.spincast.core.validation.ValidatorBase;
import org.spincast.todobackend.inmemory.models.ITodo;

/**
 * Todo validator.
 */
public class TodoValidator extends ValidatorBase {

    public IValidationResult validate(ITodo objectToValidate) {

        IValidationSet validation = newValidationBuilder();

        //==========================================
        // Maximum 255 character for the title
        //==========================================
        validation.validateMaxLength("title", objectToValidate.getTitle(), 255);

        return validation.getResult();

    }

}
