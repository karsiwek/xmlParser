package com.github.karsiwek.xmlParser.validator.strategies;

import com.github.karsiwek.xmlParser.model.XmlNode;
import com.github.karsiwek.xmlParser.validator.XmlValidatorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlyAllowedAttributesForNodeValidationStrategy implements ValidationStrategy {

    Map<String, List<String>> rules = new HashMap<>();

    public void addRule(String nodeName, List<String> allowedAttributes) {
        this.rules.put(nodeName, allowedAttributes);
    }

    public void validate(XmlNode node) {
        if (rules.containsKey(node.getName())) {
            if (!rules.get(node.getName()).containsAll(node.getAttributes().keySet())) {
                String illegalAttributes = node.getAttributes().keySet().stream()
                        .filter(key -> !rules.get(node.getName()).contains(key))
                        .reduce(String::concat).get();
                throw new XmlValidatorException("node <" + node.getName() + "> contains illegal attribute: " + illegalAttributes);
            }

        }
    }
}
