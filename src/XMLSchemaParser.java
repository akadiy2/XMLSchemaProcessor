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
import java.util.ArrayList;
import java.util.Comparator;
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
        List<XMLQueryResult> results = new ArrayList<>();
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

                double fraction = (double) count / total;
                int percentage = (int)(fraction * 100);



                XMLQueryResult result = new XMLQueryResult(path, count, percentage);
                results.add(result);

            }


        }

        results.sort(Comparator.comparing(XMLQueryResult::getHits).reversed());
        results.forEach(System.out::println);


    }
}
