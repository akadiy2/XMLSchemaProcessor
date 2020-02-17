import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class XMLSchemaGenerator {
    private String fileName;
    private List<String> paths;

    public XMLSchemaGenerator(String fileName) {
        this.fileName = fileName;
        this.paths = new ArrayList<>();
    }

    public List<String> getPaths() {
        return this.paths;
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
                Stack<String> st = new Stack<>();
                while (current != null) {
                    st.push(current.getNodeName());
                    st.push("/");

                    current = current.getParentNode();
                }

                while (!st.isEmpty()) {
                    sb.append(st.pop());
                }


            }




            if (n.hasChildNodes()) {
                visitChildNodes(n.getChildNodes(), sb);
            }

            sb = new StringBuilder();
        }

    }



    public void processFile() {

        try {

            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();


            NodeList nList = doc.getFirstChild().getChildNodes();
            String rootName = doc.getFirstChild().getNodeName();

            System.out.println(rootName);


            System.out.println("----------------------------");
            StringBuilder sb = new StringBuilder();


            visitChildNodes(nList, sb);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
