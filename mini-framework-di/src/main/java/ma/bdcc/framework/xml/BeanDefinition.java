package ma.bdcc.framework.xml;

public class BeanDefinition {
    private String id;
    private String className;
    private String propertyName;
    private String propertyRef;

    public BeanDefinition() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }

    public String getPropertyRef() { return propertyRef; }
    public void setPropertyRef(String propertyRef) { this.propertyRef = propertyRef; }
}
