package demo.xml;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class XMLSerializationTest
{

     static final String XALAN_INDENT_AMOUNT = "{http://xml.apache.org/xslt}" + "indent-amount";

     public static void main(String[] args) throws Exception
     {
         File resultFile = new File(XMLSerializationTest.class.getResource("./").getPath() + "web.xml");

         SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();
         TransformerHandler handler = stf.newTransformerHandler();
         Transformer transformer = handler.getTransformer();
         transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty(XALAN_INDENT_AMOUNT, "2");
         handler.setResult(new StreamResult(new FileOutputStream(resultFile)));

         Attributes noAtts = new AttributesImpl();
         String text = "foo bar";
         handler.startDocument();
         handler.startElement("", "", "doc", noAtts);
         handler.startElement("", "", "para", noAtts);
         handler.characters(text.toCharArray(), 0, text.length());
         handler.endElement("", "", "para");
         handler.endElement("", "", "doc");
         handler.endDocument();

         System.out.println("Done.");
     }

}