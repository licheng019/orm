package com.test.orm.tools;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

//read xml configuration file
public class XMLFactory {
	public static void main(String[] args)
	{
		
		getXMLInfo(XMLFactory.class.getResource("datasource.xml").getPath());
	}
	public static Map<String, String> getXMLInfo(String fileName)
	{
		Map<String, String> propertyMap = new HashMap<String, String>();
		File inputFile = new File(fileName);
		SAXReader reader = new SAXReader();
		try {
			Document document =reader.read(inputFile);
			Element rootsElement = document.getRootElement();
			Element rootElement = rootsElement.element("datasource");
			Element jdbcElement = rootElement.element("jdbc");
			List<Element> propertys = jdbcElement.elements();
			for(Element property : propertys)
			{
				Attribute nameAttribute = property.attribute("name");
				Attribute valueAttribute = property.attribute("value");
				propertyMap.put(nameAttribute.getValue(), valueAttribute.getValue());
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyMap;
	}
}
