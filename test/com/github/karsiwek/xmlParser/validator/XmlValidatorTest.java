package com.github.karsiwek.xmlParser.validator;

import com.github.karsiwek.xmlParser.model.XmlNode;
import com.github.karsiwek.xmlParser.validator.strategies.OnlyAllowedAttributesForNodeValidationStrategy;
import com.github.karsiwek.xmlParser.validator.strategies.OnlyOneChildrenValidationStrategy;
import com.github.karsiwek.xmlParser.validator.strategies.ValidationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlValidatorTest {

    private XmlValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new XmlValidator();
    }

    @Test
    void WhenValidateWithoutRules_ShouldPassValidation() {
        XmlNode testNode = XmlNode.XmlNodeBuilder.xmlNode().build();

        validator.validate(testNode);
    }

    @Test
    void WhenValidateWithOneChildrenValidationStrategy_ShouldFailWithNodeWithTwoChildren() {
        XmlNode testNode = XmlNode.XmlNodeBuilder.xmlNode().withChildren(Arrays.asList(null, null)).build();

        validator.registerStrategy(OnlyOneChildrenValidationStrategy.class);

        assertThrows(XmlValidatorException.class, () -> validator.validate(testNode));
    }


    @Test
    void WhenValidateWithOnlyAllowedAttributesValidationStrategy_ShouldFailWithIllegalAttributes() {
        XmlNode testNode = XmlNode.XmlNodeBuilder.xmlNode()
                .withName("node")
                .withAttributes(Map.of("legal1", "val", "illegal1", "val"))
                .build();

        OnlyAllowedAttributesForNodeValidationStrategy strategy = new OnlyAllowedAttributesForNodeValidationStrategy();
        strategy.addRule("node", Arrays.asList("legal1"));

        validator.registerStrategy(strategy);

        assertThrows(XmlValidatorException.class, () -> validator.validate(testNode));
    }

    @Test
    void WhenValidateWithOnlySomeCustomValidator_ShouldFailIfDoesNotMeetCriteria() {
        XmlNode testNode = XmlNode.XmlNodeBuilder.xmlNode()
                .withName("node")
                .withAttributes(Map.of("legal1", "val", "illegal1", "val"))
                .build();

        ValidationStrategy strategy = new ValidationStrategy() {
            @Override
            public void validate(XmlNode xmlNode) throws XmlValidatorException {
                throw new XmlValidatorException("custom exception");
            }
        };

        validator.registerStrategy(strategy);

        assertThrows(XmlValidatorException.class, () -> validator.validate(testNode));
    }


}