package com.github.karsiwek.xmlParser.validator.strategies;

import com.github.karsiwek.xmlParser.model.XmlNode;
import com.github.karsiwek.xmlParser.validator.XmlValidatorException;

public class OnlyOneChildrenValidationStrategy implements ValidationStrategy {
    public void validate(XmlNode node) {
        if (node.getChildren().size() > 1) {
            throw new XmlValidatorException("cannot have more than 1 children");
        }
    }
}
