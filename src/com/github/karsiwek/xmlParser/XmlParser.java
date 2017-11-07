package com.github.karsiwek.xmlParser;

import com.github.karsiwek.xmlParser.model.XmlNode;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class XmlParser {
    private static String NODE_PATTERN = "<([\\w-]+)((?: +[\\w-]+=(\'|\")[\\w-]+\\3)*)>(.*?)<\\/\\1>";
    private static String ATTRIBUTES_PATTERN = "([\\w-]+)=(\'|\")([\\w-]+)\\2";

    public Optional<XmlNode> parseNode(String xml) {

        Pattern pattern = Pattern.compile(NODE_PATTERN);

        Matcher matcher = pattern.matcher(xml);
        List<MatchResult> results = matcher.results().collect(Collectors.toList());

        if (results.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(
                    XmlNode.XmlNodeBuilder.xmlNode()
                            .withName(results.get(0).group(1))
                            .withAttributes(parseAttributes(results.get(0).group(2)))
                            .withChildren((pattern.matcher(results.get(0).group(4))).results().flatMap(
                                    result -> parseNode(result.group(0)).stream()
                            ).collect(Collectors.toList()))
                            .build()
            );
        }
    }

    private Map<String, String> parseAttributes(String attributes) {
        return Pattern.compile(ATTRIBUTES_PATTERN).matcher(attributes).results().collect(Collectors.toMap(
                (result) -> result.group(1),
                (result) -> result.group(3)
        ));
    }
}
