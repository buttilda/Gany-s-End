package ganymedes01.ganysend.core.utils.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class XMLNode {

	final String name;
	String value;
	final List<XMLNode> nodes = new ArrayList<XMLNode>();
	final List<XMLProperty> properties = new ArrayList<XMLProperty>();

	public XMLNode(String name) {
		this.name = name;
	}

	public boolean addNode(XMLNode node) {
		return nodes.add(node);
	}

	public boolean addNodes(Collection<XMLNode> node) {
		return nodes.addAll(node);
	}

	public XMLNode setValue(String value) {
		this.value = value;
		return this;
	}

	public String getName() {
		return name;
	}

	public XMLNode addProperty(String name, String value) {
		properties.add(new XMLProperty(name, value));
		return this;
	}

	public List<XMLNode> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		String str = "<" + name;
		for (XMLProperty prop : properties)
			str += " " + prop.toString();
		str += ">";

		if (value != null)
			str += value;
		else {
			str += "\n";
			for (XMLNode node : nodes)
				str += "\t" + node.toString() + "\n";
		}

		return str + "</" + name + ">";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof XMLNode))
			return false;
		XMLNode node = (XMLNode) obj;
		if (equalNoProps(node))
			if (!(properties == null ^ node.properties == null))
				return properties == null || properties.equals(node.properties);

		return false;
	}

	public boolean equalNoProps(XMLNode node) {
		if (name.equals(node.name)) // Check if the names are the same
			if (!(value == null ^ node.value == null)) // Check if both have a value or neither has a value
				if (value == null || value.equals(node.value)) // if they don't have a value, check if it's the same
					if (!(hasNodes() ^ node.hasNodes())) // Check if both have sub-nodes or neither has sub-nodes
						return nodes == null || nodes.equals(node.nodes); // if they don't have sub-nodes, check if it's they're the same
		return false;
	}

	public boolean hasValue() {
		return value != null;
	}

	public boolean hasNodes() {
		return nodes != null && !nodes.isEmpty();
	}

	public boolean hasProperties() {
		return properties != null && !properties.isEmpty();
	}

	public String getValue() {
		return value;
	}

	public XMLNode getNode(String key) {
		if (hasNodes())
			for (XMLNode node : nodes)
				if (node.name.equals(key))
					return node;

		return null;
	}

	public String getProperty(String key) {
		if (hasProperties())
			for (XMLProperty prop : properties)
				if (prop.name.equals(key))
					return prop.value;

		return "null";
	}

	public boolean checkPropertyValue(String key, String... values) {
		String v = getProperty(key);
		for (String value : values)
			if (compareValues(v, value))
				return true;

		return false;
	}

	public boolean compareValues(String v1, String v2) {
		return v1.toLowerCase().equals(v2.toLowerCase());
	}
}