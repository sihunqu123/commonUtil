package demo.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import util.commonUtil.ComFileUtil;

/**
 *
 */
public class XPathParserDemo {

  public static void main(String[] args) {
    readPlugin();

    // System.out.println(new
    // File(XPathParserDemo.class.getResource("./").getFile() +
    // "web.xml").exists());
    // System.out.println(new File(new
    // File(XPathParserDemo.class.getResource("./").getFile() +
    // "web.xml").toString()));
    // System.out.println(new File(new
    // File(XPathParserDemo.class.getResource("./").getFile() +
    // "web.xml").toString()).exists());
    // System.out.println(XPathParserDemo.class.getResource("./").getPath());
    // System.out.println(XPathParserDemo.class.getResource("./").getPath() +
    // "web.xml");
    System.out.println("done");
  }

  public static void readPlugin() {
    // ComFileUtil.readStream2String(is)

    DocumentBuilderFactory dbFactory = null;
    DocumentBuilder dBuilder = null;
    Document newDoc = null;
    Document doc = null;

    try {
      dbFactory = DocumentBuilderFactory.newInstance();
      dBuilder = dbFactory.newDocumentBuilder();

      // xml header: <?xml version="1.0" encoding="UTF-8"?>
      newDoc = dBuilder.newDocument();
      newDoc.setXmlVersion("1.0");
      newDoc.setXmlStandalone(true);
      // create root element.
      Element webApp = newDoc.createElement("web-app");
      webApp.setAttribute("id", "WebApp_ID");
      webApp.setAttribute("version", "2.4");
      // webApp.setPrefix("");
      webApp.setAttribute("xmlns", "http://java.sun.com/xml/ns/j2ee");
      webApp.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      webApp.setAttribute("xsi:schemaLocation",
          "http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
      // append root element to doc to make it works.
      newDoc.appendChild(webApp);

      doc = dBuilder.parse(new File(XPathParserDemo.class.getResource("plugin.xml").getPath()));
      doc.getDocumentElement().normalize();
      // XPath to locate elements.
      XPath xPath = XPathFactory.newInstance().newXPath();
      // find all servlet elements
      NodeList nodeList = (NodeList) xPath.compile("//servlet").evaluate(doc, XPathConstants.NODESET);
      int length = nodeList.getLength();
      System.out.println("length :" + length);

      Map<String, Integer> duplicateServletNameMap = new HashMap<String, Integer>();

      for (int i = 0; i < length; i++) {
        Node nNode = nodeList.item(i);

        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          String attrAlias = eElement.getAttribute("alias");
          String attrclass = eElement.getAttribute("class");

          String classSimpleName = attrclass.substring(attrclass.lastIndexOf(".") + 1, attrclass.length());
          Integer duplicatteIndex = duplicateServletNameMap.get(classSimpleName);
          if (duplicatteIndex == null) { // deal with duplicate servletClass
            duplicateServletNameMap.put(classSimpleName, 0);
          } else {
            duplicateServletNameMap.put(classSimpleName, ++duplicatteIndex);
            classSimpleName += duplicatteIndex;
          }

          Element servlet = newDoc.createElement("servlet");
          Element servletName = newDoc.createElement("servlet-name");
          servletName.setTextContent(classSimpleName);
          Element servletClass = newDoc.createElement("servlet-class");
          servletClass.setTextContent(attrclass);
          // a same node can be append for once. For reuse case, need to cloneNode.
          servlet.appendChild(servletName.cloneNode(true));

          System.out.println("Servlet alias: " + attrAlias);
          System.out.println("Servlet attrclass: " + attrclass);
          NodeList childNodes = eElement.getChildNodes();
          int childLength = childNodes.getLength();
          for (int j = 0; j < childLength; j++) {
            Node childNote = childNodes.item(j);
            if (childNote.getNodeType() == Node.ELEMENT_NODE) {
              Element childElement = (Element) childNote;
              String initParamName = childElement.getAttribute("name");
              String initParamVal = childElement.getAttribute("value");
              Element initParam = newDoc.createElement("init-param");
              Element paramName = newDoc.createElement("param-name");
              paramName.setTextContent(initParamName);
              Element paramValue = newDoc.createElement("param-value");
              paramValue.setTextContent(initParamVal);
              initParam.appendChild(paramName);
              initParam.appendChild(paramValue);
              servlet.appendChild(initParam);
              System.out.println("Servlet init-param: " + initParamName + " value:" + initParamVal);
            }
          }
          servlet.appendChild(servletClass);

          Element servletMapping = newDoc.createElement("servlet-mapping");
          Element urlPattern = newDoc.createElement("url-pattern");
          urlPattern.setTextContent(attrAlias);

          servletMapping.appendChild(servletName);
          servletMapping.appendChild(urlPattern);

          webApp.appendChild(servlet);
          webApp.appendChild(servletMapping);
        }
      }

      
      // doc.removeChild(doc.getFirstChild());
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      // make created xml formated
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

      // since the default xml-declaration will be 
      // <?xml version="1.0" encoding="UTF-8" isStandAlone="false"?>
      // while no isStandAlone attr:
      // <?xml version="1.0" encoding="UTF-8"?>
      // is expected, set OMIT_XML_DECLARATION=yes. Then we add
      // the expected xml-declaration manually.
      // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

      newDoc.normalize();
      newDoc.normalizeDocument();
      DOMSource source = new DOMSource(newDoc);
      System.out.println("-----------Modified File-----------");
      
      // comment out this to print result in console.
//      OutputStream out = System.out; 
      OutputStream out = new FileOutputStream(XPathParserDemo.class.getResource("").getPath() + "web.xml");
      // we add the expected xml-declaration manually.
      out.write(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + ComFileUtil.EOL).getBytes("UTF-8"));
      transformer.transform(source, new StreamResult(out));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void createXML() {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();
      // root element
      Element rootElement = doc.createElement("cars");
      doc.appendChild(rootElement);

      // supercars element
      Element supercar = doc.createElement("supercars");
      rootElement.appendChild(supercar);

      // setting attribute to element
      Attr attr = doc.createAttribute("company");
      attr.setValue("Ferrari");
      supercar.setAttributeNode(attr);

      // carname element
      Element carname = doc.createElement("carname");
      Attr attrType = doc.createAttribute("type");
      attrType.setValue("formula one");
      carname.setAttributeNode(attrType);
      carname.appendChild(doc.createTextNode("Ferrari 101"));
      supercar.appendChild(carname);

      Element carname1 = doc.createElement("carname");
      Attr attrType1 = doc.createAttribute("type");
      attrType1.setValue("sports");
      carname1.setAttributeNode(attrType1);
      carname1.appendChild(doc.createTextNode("Ferrari 202"));
      supercar.appendChild(carname1);

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("C:\\cars.xml"));
      transformer.transform(source, result);
      // Output to console for testing
      StreamResult consoleResult = new StreamResult(System.out);
      transformer.transform(source, consoleResult);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void readDemo() {
    /**
     *  * <?xml version="1.0"?>
     * <class> <student rollno="393"> <firstname>dinkar</firstname>
     * <lastname>kad</lastname> <nickname>dinkar</nickname>
     * <marks>85</marks> </student>
     * <student rollno="493"> <firstname>Vaneet</firstname>
     * <lastname>Gupta</lastname> <nickname>vinni</nickname>
     * <marks>95</marks> </student>
     * <student rollno="593"> <firstname>jasvir</firstname>
     * <lastname>singn</lastname> <nickname>jazz</nickname>
     * <marks>90</marks> </student> </class>
     */
    DocumentBuilderFactory dbFactory = null;
    DocumentBuilder dBuilder = null;
    try {
      File inputFile = new File("input.txt");
      dbFactory = DocumentBuilderFactory.newInstance();
      dBuilder = dbFactory.newDocumentBuilder();

      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      XPath xPath = XPathFactory.newInstance().newXPath();

      String expression = "/class/student[@rollno='493']";
      NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node nNode = nodeList.item(i);
        // nNode.appendChild(newChild)
        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          System.out.println("Student roll no : " + eElement.getAttribute("rollno"));
          System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
          System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
          System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
          System.out.println("Marks : " + eElement.getElementsByTagName("marks").item(0).getTextContent());
        }
      }
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
  }
}