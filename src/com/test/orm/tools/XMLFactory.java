package com.test.orm.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.test.orm.tableInfo.Id;
import com.test.orm.tableInfo.ManyToOne;
import com.test.orm.tableInfo.OneToMany;
import com.test.orm.tableInfo.Property;
import com.test.orm.tableInfo.TableInfo;

//read xml configuration file
public class XMLFactory {
	public static void main(String[] args)
	{
		
		
	}
	public static Map<String, String> getXMLInfo(String fileName)
	{
		Map<String, String> propertyMap = new HashMap<String, String>();
		File inputFile = new File(fileName);
		SAXReader reader = new SAXReader();
		try {
			Document document =reader.read(inputFile);
			Element rootsElement = document.getRootElement();
			ConstantValue.TABLEMAP = getTableInfoFromXML(readBeanXml(rootsElement));
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
	private static List<String> readBeanXml(Element root)
	{
		Element ormMapping = root.element("orm-mapping");
		Element listElement = ormMapping.element("list");
		List<Element> valueList =listElement.elements();
		List<String> fileList = new ArrayList<String>();
		for(Element value: valueList)
		{
			System.out.println(value.getText());
			fileList.add(value.getText());
		}
		return fileList;
	}
	private static Map<String,TableInfo> getTableInfoFromXML(List<String> fileList)
	{
		Map<String,TableInfo> tableMap = new HashMap<String, TableInfo>();
		for(String file : fileList)
		{
			File beanFile = new File(XMLFactory.class.getResource(file).getPath());
			System.out.println(beanFile);
			SAXReader reader = new SAXReader();
			TableInfo tableInfo = new TableInfo();
			try {
				Document document = reader.read(beanFile);
				Element rootsElement = document.getRootElement(); //class
				
				tableInfo.setClassName(rootsElement.attributeValue("name"));
				tableInfo.setTableName(rootsElement.attributeValue("table"));
				List<Element> elements = rootsElement.elements();
				List<Property> propertyList = new ArrayList<Property>();
				List<OneToMany> oneToManyList = new ArrayList<OneToMany>();
				List<ManyToOne> ManyToOneList = new ArrayList<ManyToOne>();
				for(Element element : elements)
				{
					System.out.println(element.getName());
					switch(element.getName())
					{

					
						case "Id":
							System.out.println(element.attributeValue("name"));
							Id id = new Id();
							id.setIdName(element.attributeValue("name"));
							id.setIdcolumn(element.attributeValue("column"));
							tableInfo.setId(id);
							break;
						case "property":
							Property property = new Property();
							property.setpName(element.attributeValue("name"));
							property.setpColumn(element.attributeValue("column"));
							propertyList.add(property);
							tableInfo.setPlist(propertyList);
							break;
						case "onetomany":
							OneToMany otm = new OneToMany();
							otm.setOneToManyClass(element.attributeValue("class"));
							otm.setOneToManyName(element.attributeValue("name"));
							otm.setOneToManyColumn(element.element("column").attributeValue("name"));
							oneToManyList.add(otm);
							tableInfo.setOnelist(oneToManyList);
						case "manytoone":
							ManyToOne mto = new ManyToOne();
							mto.setManyToOneClass(element.attributeValue("class"));
							mto.setManyToOneName(element.attributeValue("name"));
							mto.setManyToOneColumn(element.element("column").attributeValue("name"));
							ManyToOneList.add(mto);	
							tableInfo.setManylist(ManyToOneList);
						default:
							break;
					}
					
					tableMap.put(tableInfo.getClassName(), tableInfo);
				}		
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return tableMap;
	}
}
