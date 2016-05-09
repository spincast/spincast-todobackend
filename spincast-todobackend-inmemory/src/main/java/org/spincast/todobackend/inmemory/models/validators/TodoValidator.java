package org.spincast.todobackend.inmemory.models.validators;

import org.spincast.core.json.IJsonManager;
import org.spincast.core.xml.IXmlManager;
import org.spincast.plugins.validation.ISpincastValidationConfig;
import org.spincast.plugins.validation.IValidationErrorFactory;
import org.spincast.plugins.validation.SpincastValidatorBase;
import org.spincast.todobackend.inmemory.models.ITodo;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Todo validator.
 */
public class TodoValidator extends SpincastValidatorBase<ITodo> {

    @AssistedInject
    public TodoValidator(@Assisted ITodo objToValidate,
                         IValidationErrorFactory validationErrorFactory,
                         ISpincastValidationConfig spincastValidationConfig,
                         IJsonManager jsonManager,
                         IXmlManager xmlManager) {
        super(objToValidate, validationErrorFactory, spincastValidationConfig, jsonManager, xmlManager);
    }

    @Override
    protected void validate() {

        //==========================================
        // Maximum 255 character for the title
        //==========================================
        validateMaxLength("title", getObjToValidate().getTitle(), 255);

    }

}
