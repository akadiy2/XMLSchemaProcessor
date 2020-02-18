import java.io.File;

public class XmlSchemaProcessorApplication {
    public static void main(String[] args) {
        String fileName;

        if (args.length == 0) {
            fileName = "input";
        } else {
            fileName = args[0];
        }
        XMLSchemaCollector collector = XMLSchemaCollector.getInstance();

        File folder = new File(fileName);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            try {

                for (File f : listOfFiles) {

                    XMLSchemaGenerator generator = new XMLSchemaGenerator(f.getAbsolutePath(), collector);
                    generator.start();
                    generator.join();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(collector.getSchemas().size());

            try {
                new XMLSchemaParser(listOfFiles, collector).process();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }


    }
}
