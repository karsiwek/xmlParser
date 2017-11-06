package com.github.karsiwek.xmlParser.integration;

import com.github.karsiwek.xmlParser.XmlParser;
import com.github.karsiwek.xmlParser.model.XmlNode;
import com.github.karsiwek.xmlParser.validator.XmlValidator;
import com.github.karsiwek.xmlParser.validator.XmlValidatorException;
import com.github.karsiwek.xmlParser.validator.strategies.OnlyAllowedAttributesForNodeValidationStrategy;
import com.github.karsiwek.xmlParser.validator.strategies.OnlyOneChildrenValidationStrategy;
import com.github.karsiwek.xmlParser.validator.strategies.ValidationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IntegrationTests {
    private XmlParser parser;
    private XmlValidator validator;

    @BeforeEach
    void setUp() {
        this.parser = new XmlParser();
        this.validator = new XmlValidator();
    }


    @Test
    void WhenValidatingParsedNodeWithManyChildren_ShouldThrowException() {
        Optional<XmlNode> result = parser.parseNode("<test attr1='123' attr2=\"321\"><a></a><b></b></test>");

        validator.registerStrategy(OnlyOneChildrenValidationStrategy.class);
        assertThrows(XmlValidatorException.class, () -> validator.validate(result.get()));
    }

    @Test
    void WhenValidatingParsedValidNodeWithAllStrategies_ShouldNotThrowException() {
        Optional<XmlNode> result = parser.parseNode("<test attr1='123' attr2=\"321\"><a><b></b></a></test>");

        OnlyAllowedAttributesForNodeValidationStrategy attributesStrategy = new OnlyAllowedAttributesForNodeValidationStrategy();
        attributesStrategy.addRule("test", Arrays.asList("attr1", "attr2"));
        attributesStrategy.addRule("other", Arrays.asList("foo", "bar"));

        ValidationStrategy customStrategy = new ValidationStrategy() {
            @Override
            public void validate(XmlNode xmlNode) throws XmlValidatorException {
                if (xmlNode.getName().equals("illegalNode")) {
                    throw new XmlValidatorException("should never happen");
                }
            }
        };

        validator.registerStrategy(customStrategy);
        validator.registerStrategy(attributesStrategy);
        validator.registerStrategy(OnlyOneChildrenValidationStrategy.class);


        validator.validate(result.get());
    }

}