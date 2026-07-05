package ma.bdcc.framework.xml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class XmlParser {
    public static Map<String, BeanDefinition> parse(String xmlFile) throws Exception {
        Map<String, BeanDefinition> beans = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFile));

        NodeList beanList = document.getElementsByTagName("bean");

        for (int i = 0; i < beanList.getLength(); i++) {
            Element beanElement = (Element) beanList.item(i);
            BeanDefinition beanDef = new BeanDefinition();
            String id = beanElement.getAttribute("id");
            beanDef.setId(id);
            beanDef.setClassName(beanElement.getAttribute("class"));

            NodeList properties = beanElement.getElementsByTagName("property");
            if (properties.getLength() > 0) {
                Element prop = (Element) properties.item(0);
                beanDef.setPropertyName(prop.getAttribute("name"));
                beanDef.setPropertyRef(prop.getAttribute("ref"));
            }

            beans.put(id, beanDef);
        }

        return beans;
    }
}
