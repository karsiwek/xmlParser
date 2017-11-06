package com.github.karsiwek.xmlParser.validator;

import com.github.karsiwek.xmlParser.model.XmlNode;
import com.github.karsiwek.xmlParser.validator.strategies.ValidationStrategy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class XmlValidator {

    List<ValidationStrategy> strategies = new ArrayList<>();

    public void validate(XmlNode xmlNode) throws XmlValidatorException {
        for (ValidationStrategy strategy : strategies) {
            strategy.validate(xmlNode);
        }

        for (XmlNode child : xmlNode.getChildren()) {
            validate(child);
        }
    }

    public void registerStrategy(Class<? extends ValidationStrategy> strategy) {
        try {
            strategies.add(strategy.getConstructor().newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void registerStrategy(ValidationStrategy strategy) {
        strategies.add(strategy);
    }
}
