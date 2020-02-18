import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class XMLSchemaParser {
    private XMLSchemaCollector collector;
    private File[] listOfFiles;

    public XMLSchemaParser(File[] listOfFiles, XMLSchemaCollector collector) {
        this.listOfFiles = listOfFiles;
        this.collector = collector;
    }

    public void process() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {


        // run xpath queries against all 400 xml files
        // to see the percentage hits by node path

        for (List<String> schema : collector.getSchemas()) {



            int total = listOfFiles.length;


            for (String path : schema) {
                int count = 0;
                for (File file : listOfFiles) {
                    FileInputStream fileIS = new FileInputStream(file);
                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = builderFactory.newDocumentBuilder();
                    Document xmlDocument = builder.parse(fileIS);
                    XPath xPath = XPathFactory.newInstance().newXPath();

                    Node n = (Node) xPath.compile(path).evaluate(xmlDocument, XPathConstants.NODE);
                    if (n != null) {
                        count++;
                    }
                }


                System.out.println(String.format("percentage of path %s is %1.2f", path, (double) count / total));

            }


        }


    }
}
