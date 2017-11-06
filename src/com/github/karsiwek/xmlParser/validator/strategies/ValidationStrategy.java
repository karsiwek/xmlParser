package com.github.karsiwek.xmlParser.validator.strategies;

import com.github.karsiwek.xmlParser.model.XmlNode;
import com.github.karsiwek.xmlParser.validator.XmlValidatorException;

public interface ValidationStrategy {
    void validate(XmlNode xmlNode) throws XmlValidatorException;
}
