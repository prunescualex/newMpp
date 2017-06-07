package ro.ubb.catalog.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import main.java.Domain.BaseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Loads all entities from an xml file containing entities of type T with id of type ID.
 *
 * @author GPR.
 */
public class XmlReader<ID, T extends BaseEntity<ID>> {
    private String fileName;

    public XmlReader(String fileName) {
        this.fileName = fileName;
    }

    public List<T> loadEntities() {
        List<T> entities = new ArrayList<>();
        //TODO implement reader
        return entities;
    }


}
