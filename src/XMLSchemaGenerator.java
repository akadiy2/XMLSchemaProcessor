import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class XMLSchemaGenerator extends Thread {
    private String fileName;
    private List<String> paths;
    private XMLSchemaCollector collector;

    public XMLSchemaGenerator(String fileName, XMLSchemaCollector collector) {
        this.fileName = fileName;
        this.paths = new ArrayList<>();
        this.collector = collector;
    }


    private void visitChildNodes(NodeList nList, StringBuilder sb) {

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node n = nList.item(temp);

            if (n.getNodeName().equals("#text")) {

                if (!sb.toString().isEmpty()) {
                    this.paths.add(sb.toString());
                }
                sb = new StringBuilder();
                continue;
            } else {
                Node current = n;
                List<String> path = new ArrayList<>();
                while (current != null) {
                    String nodeName = current.getNodeName();
                    if (!nodeName.equalsIgnoreCase("#document")) {
                        path.add(current.getNodeName());
                    }

                    current = current.getParentNode();
                }

                Collections.reverse(path);

                sb.append(String.join("/", path));

            }


            if (n.hasChildNodes()) {
                visitChildNodes(n.getChildNodes(), sb);
            }

            sb = new StringBuilder();
        }

    }

    @Override
    public void run() {
        processFile();
        collector.addSchema(this.paths);
    }


    private void processFile() {

        try {

            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();


            NodeList nList = doc.getFirstChild().getChildNodes();

            StringBuilder sb = new StringBuilder();


            visitChildNodes(nList, sb);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
