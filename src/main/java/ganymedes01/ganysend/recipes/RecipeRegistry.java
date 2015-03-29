package ganymedes01.ganysend.recipes;

import ganymedes01.ganysend.core.utils.xml.XMLHelper;
import ganymedes01.ganysend.core.utils.xml.XMLNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public abstract class RecipeRegistry<T> {

	private static final List<String> TAG_ORDER_LIST = Arrays.asList("custom", "default", "ignored");
	public static File baseFile;

	private final List<T> registry = new ArrayList<T>();
	protected final File recipesFile;
	protected final String name;
	// @formatter:off
	protected String comment = 	"\nRecipes marked as \"default\" will be reset every time the game launches.\n" +
								"If you wish to disable a recipe change its status to \"ignored\"\n" +
								"If you want to add a custom recipe, mark it as \"custom\". If you mark it as default, it will be deleted!!\n" +
								"If you alter a default recipe don't forget to also mark it as \"custom\"\n" +
								"\n" +
								"The syntax for an item stack is the same as you'd use in the /give command. NBT tags are supported! (e.g. minecraft:skull 1 3 {SkullOwner:\"ganymedes01\"})\n" +
								"The syntax for a fluid stack is simiar to the item stack, it should be the fluid name followed by the amount. NBT tags are also supported! (e.g. water 1000)\n" +
								"The syntax for ore dictionary values is the value between double-quotes. (e.g. \"ingotIron\")\n";
	// @formatter:on
	
	protected RecipeRegistry(String name) {
		this.name = name;
		recipesFile = new File(baseFile, this.name + ".xml");
	}

	public final void init() {
		try {
			// Add the default recipes in
			addDefaultRecipes();

			// Convert default recipes to XML nodes
			List<XMLNode> defaultNodes = new ArrayList<XMLNode>();
			for (T recipe : registry)
				defaultNodes.add(toXML(recipe).addProperty("status", "default"));

			if (!recipesFile.exists()) { // If file doesn't exist, write the default recipes to it
				BufferedWriter bw = XMLHelper.getWriter(recipesFile, name.replace(" ", "").toUpperCase(), comment);
				for (XMLNode node : defaultNodes)
					XMLHelper.writeNode(bw, node.toString());
				bw.close();
			} else { // If file exists, read the recipes from it
				// Clear the recipes added by default
				registry.clear();

				// Read from file
				String file = XMLHelper.readFile(recipesFile);
				List<XMLNode> nodes = new ArrayList<XMLNode>();
				XMLHelper.getNodes(file, nodes); // Will fill the list with 1 master node containing all of the other nodes.
				nodes = nodes.get(0).getNodes(); // Use the nodes held by the master nodes

				// Remove any nodes tagged as "default"
				removeDefaults(nodes);

				// Remove from the defaults list any nodes tagged as ignored in the file then add the defaults list into the ones read from the file
				removeIgnored(nodes, defaultNodes);
				nodes.addAll(defaultNodes);

				// Sort
				Collections.sort(nodes, new Comparator<XMLNode>() {

					@Override
					public int compare(XMLNode node1, XMLNode node2) {
						String stat1 = node1.getProperty("status");
						String stat2 = node2.getProperty("status");
						if (stat1.equals(stat2))
							return 0;

						return Integer.compare(TAG_ORDER_LIST.indexOf(stat1), TAG_ORDER_LIST.indexOf(stat2));
					}
				});

				// Add nodes tagged as custom, skip the ones tagged as ignored
				BufferedWriter bw = XMLHelper.getWriter(recipesFile, name.replace(" ", "").toUpperCase(), comment);
				for (XMLNode node : nodes) {
					String prop = node.getProperty("status");
					if (node.compareValues(prop, "custom") || node.compareValues(prop, "default"))
						try {
							registry.add(makeRecipe(node));
							XMLHelper.writeNode(bw, node.toString());
						} catch (Exception e) {
							commentOutNode(bw, node.toString(), "it threw an error when it was being read: " + e.getCause());
							throw new RuntimeException(e);
						}
					else if (node.compareValues(prop, "ignored"))
						XMLHelper.writeNode(bw, node.toString());
					else
						commentOutNode(bw, node.toString(), "it has an invalid status");
				}
				bw.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("Error whilst registering" + name + "recipes!" + e);
		}
	}

	private void commentOutNode(BufferedWriter bw, String str, String reason) throws IOException {
		XMLHelper.writeNode(bw, "<!--This recipe has been commented out because " + reason + "\n" + str + "-->");
	}

	private void removeIgnored(List<XMLNode> file, List<XMLNode> defaults) {
		List<XMLNode> ignored = new ArrayList<XMLNode>();
		for (XMLNode node : defaults)
			for (XMLNode n : file)
				if (n.equalNoProps(node) && n.checkPropertyValue("status", "ignored"))
					ignored.add(node);
		for (XMLNode node : ignored)
			defaults.remove(node);
	}

	private void removeDefaults(List<XMLNode> nodes) {
		List<XMLNode> defaults = new ArrayList<XMLNode>();
		for (XMLNode node : nodes)
			if (node.checkPropertyValue("status", "default"))
				defaults.add(node);
		for (XMLNode node : defaults)
			nodes.remove(node);
	}

	public List<T> getRecipes() {
		return Collections.unmodifiableList(registry);
	}

	protected final boolean addRecipe(T recipe) {
		return registry.add(recipe);
	}

	protected abstract XMLNode toXML(T recipe);

	protected abstract T makeRecipe(XMLNode node);

	protected abstract void addDefaultRecipes();
}