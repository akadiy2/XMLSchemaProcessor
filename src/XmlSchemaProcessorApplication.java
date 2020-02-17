public class XmlSchemaProcessorApplication {
    public static void main(String[] args) {
        String fileName;

        if (args.length == 0) {
            fileName = "sample.xml";
        } else {
            fileName = args[0];
        }

        XMLSchemaGenerator generator = new XMLSchemaGenerator(fileName);
        generator.processFile();
        generator.getPaths().forEach(System.out::println);
    }
}
