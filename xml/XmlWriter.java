package ro.ubb.catalog.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.Domain.BaseEntity;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Appends an entity of type T with id of type ID to an existing xml file.
 * The xml structure is assumed to be like the one from data/students.xml.
 *
 * @author GPR.
 */
public class XmlWriter<ID, T extends BaseEntity<ID>> {

    private String fileName;

    private String firstName = null;
    private String lastName = null;
    private String group = null;
    private String nrMatricol = null;
    private ArrayList<String> student;

    public XmlWriter(String fileName) {
        this.fileName = fileName;
    }

    public void save(T entity) {
        Document dom;
        Element e = null;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("roles");

            // create data elements and place them under root
            e = dom.createElement("firstName");
            e.appendChild(dom.createTextNode(firstName));
            rootEle.appendChild(e);

            e = dom.createElement("secondName");
            e.appendChild(dom.createTextNode(secondName));
            rootEle.appendChild(e);

            e = dom.createElement("grout");
            e.appendChild(dom.createTextNode(group));
            rootEle.appendChild(e);

            e = dom.createElement("nrMatricol");
            e.appendChild(dom.createTextNode(nrMatricol));
            rootEle.appendChild(e);

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(xml)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }

    }


}
