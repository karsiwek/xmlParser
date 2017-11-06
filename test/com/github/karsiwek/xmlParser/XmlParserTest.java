package com.github.karsiwek.xmlParser;

import com.github.karsiwek.xmlParser.model.XmlNode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class XmlParserTest {
    private XmlParser parser = new XmlParser();

    @Test
    void WhenParseEmptyString_ShouldReturnEmptyOptional() {
        Optional<XmlNode> result = parser.parseNode("");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void WhenParseSingleNode_ShouldReturnObjectWithSingleNode() {
        Optional<XmlNode> result = parser.parseNode("<test></test>");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getName(), is("test"));
    }

    @Test
    void WhenParseSingleNodeWithAttribute_ShouldReturnObjectWithSingleNodeWithAttributes() {
        Optional<XmlNode> result = parser.parseNode("<test attr1='123' attr2=\"321\"></test>");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getName(), is("test"));
        assertThat(result.get().getAttributes().size(), is(2));
        assertThat(result.get().getAttributes().get("attr1"), is("123"));
        assertThat(result.get().getAttributes().get("attr2"), is("321"));
    }

    @Test
    void WhenParseSingleNodeWithSubNode_ShouldReturnObjectWithNodeWithOneChild() {
        Optional<XmlNode> result = parser.parseNode("<test><test2></test2></test>");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getName(), is("test"));
        assertThat(result.get().getChildren().size(), is(1));
        assertThat(result.get().getChildren().get(0).getName(), is("test2"));
    }

    @Test
    void WhenParseSingleNodeWithSubNoded_ShouldReturnObjectWithNodeWithTwoChildren() {
        Optional<XmlNode> result = parser.parseNode("<test><test2 attr='somthing'></test2><test3></test3></test>");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getName(), is("test"));
        assertThat(result.get().getChildren().size(), is(2));
        assertThat(result.get().getChildren().get(0).getName(), is("test2"));
        assertThat(result.get().getChildren().get(0).getAttributes().get("attr"), is("somthing"));
        assertThat(result.get().getChildren().get(1).getName(), is("test3"));
    }
}