import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class XMLSchemaCollector {
    public static XMLSchemaCollector instance;

    private Set<List<String>> schemas;

    private XMLSchemaCollector() {
        schemas = new LinkedHashSet<>();
    }

    public static XMLSchemaCollector getInstance() {
        if (instance == null) {
            instance = new XMLSchemaCollector();
        }

        return instance;

    }

    public void addSchema(List<String> schema) {
        this.schemas.add(schema);
    }

    public Set<List<String>> getSchemas() {
        return this.schemas;
    }
}
