package com.tuniu.gt.complaint.util;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

import javax.xml.stream.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 封装开源项目staxon, 用于json和xml之间的转换
 * Created by rongzhiming on 2015/12/18.
 */
public class StaxonUtils {
    /**
     * json string convert to xml string
     */
    public static String json2xml(String json) throws XMLStreamException {
        StringReader input = new StringReader(json);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);
            writer.add(reader);
            reader.close();
            writer.close();
        }  finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String rst = output.toString();
        //去除例如<?xml version="1.0" encoding="UTF-8"?>这一段
        int idx = output.toString().indexOf("?>");
        if(idx > 0){
            rst = rst.substring(idx + "?>".length());
        }
        return rst;
    }

    /**
     * xml string convert to json string
     */
    public static String xml2json(String xml) throws XMLStreamException {
        return xml2json(xml, true);
    }

    /**
     * xml string convert to json string
     */
    public static String xml2json(String xml, boolean autoPrimitive) throws XMLStreamException {
        StringReader input = new StringReader(xml);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(autoPrimitive).prettyPrint(true).build();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }

}
