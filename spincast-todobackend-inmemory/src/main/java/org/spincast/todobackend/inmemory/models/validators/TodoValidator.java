package org.spincast.todobackend.inmemory.models.validators;

import org.spincast.plugins.validation.SpincastValidatorBase;
import org.spincast.plugins.validation.SpincastValidatorBaseDeps;
import org.spincast.todobackend.inmemory.models.ITodo;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Todo validator.
 */
public class TodoValidator extends SpincastValidatorBase<ITodo> {

    @AssistedInject
    public TodoValidator(@Assisted ITodo todo,
                         SpincastValidatorBaseDeps spincastValidatorBaseDeps) {
        super(todo, spincastValidatorBaseDeps);
    }

    @Override
    protected void validate() {

        //==========================================
        // Maximum 255 character for the title
        //==========================================
        validateMaxLength("title", getObjToValidate().getTitle(), 255);

    }

}
