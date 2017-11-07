package com.github.karsiwek.xmlParser.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlNode {
    private String name;
    private Map<String, String> attributes;
    private List<XmlNode> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<XmlNode> getChildren() {
        return children;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    private void setChildren(List<XmlNode> children) {
        this.children = children;
    }

    public static class XmlNodeBuilder {
        private final XmlNode xmlNode;

        private XmlNodeBuilder() {
            this.xmlNode = new XmlNode();
        }

        public static XmlNodeBuilder xmlNode() {
            return new XmlNodeBuilder();
        }

        public XmlNodeBuilder withName(String name) {
            xmlNode.setName(name);
            return this;
        }

        public XmlNodeBuilder withAttributes(Map<String, String> attributes) {
            xmlNode.setAttributes(attributes);
            return this;
        }

        public XmlNodeBuilder withChildren(List<XmlNode> children) {
            xmlNode.setChildren(children);
            return this;
        }

        public XmlNode build() {
            return xmlNode;
        }
    }
}
