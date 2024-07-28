package com.cakemonster.easyapi.parse;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

/**
 * SqlParser
 *
 * @author cakemonster
 * @date 2024/7/26
 */
public class MybatisSqlParser {

    private static final Configuration CONFIGURATION = new Configuration();

    public SqlSource parse(String xml) {

        if (!xml.startsWith("<select>")) {
            xml = "<select>" + xml + "</select>";
        }

        XPathParser parser = new XPathParser(xml);
        XNode node = parser.evalNode("select");

        LanguageDriver langDriver = CONFIGURATION.getDefaultScriptingLanguageInstance();
        return langDriver.createSqlSource(CONFIGURATION, node, Map.class);
    }
}
