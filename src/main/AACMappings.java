import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;
// import java.io.*;

/**
 * Creates a set of mappings of an AAC that has two levels,
 * one for categories and then within each category, it has
 * images that have associated text to be spoken. This class
 * provides the methods for interacting with the categories
 * and updating the set of images that would be shown and handling
 * an interactions.
 *
 * @author Catie Baker
 * @author Sara Jaljaa
 *
 * @course CSC-207-01
 *
 */
public class AACMappings implements AACPage {

	// ╔════════════╗
	// ║   Fields   ║
	// ╚════════════╝

	/**
	 * Holds an AA of categories that is used to map categories to filenames.
	 */
	protected AssociativeArray<String, AACCategory> categories;

	/**
	 * Holds the the current category's objects.
	 */
	protected AACCategory current;
	
	// ╔══════════════════╗
	// ║   Constructors   ║
	// ╚══════════════════╝

	/**
	 * Creates a set of mappings for the AAC based on the provided
	 * file. The file is read in to create categories and fill each
	 * of the categories with initial items. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image
	 *
	 * for instance:
	 * img/food/plate.png food
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 *
	 * represents the file with two categories, food and clothing
	 * and food has french fries and watermelon and clothing has a
	 * collared shirt.
	 *
	 * @param filename
	 * 		The name of the file that stores the mapping information.
	 */
	public AACMappings(String filename) {
		this.current = new AACCategory(null);
		this.categories = new AssociativeArray<>();
		// String input = null;

		// try {
		// 	while (writer.readLine() != null) {
		// 		String[] args = input.split(" ");
		// 	}
		// } catch (Exception e) {
		// 	;
		// }
	} // AACMappings(String)

	// ╔════════════════════╗
	// ║   Public Methods   ║
	// ╚════════════════════╝
	
	/**
	 * Given the image location selected, it determines the action to be
	 * taken. This can be updating the information that should be displayed
	 * or returning text to be spoken. If the image provided is a category,
	 * it updates the AAC's current category to be the category associated
	 * with that image and returns the empty string. If the AAC is currently
	 * in a category and the image provided is in that category, it returns
	 * the text to be spoken.
	 *
	 * @param imageLoc
	 * 		The location where the image is stored.
	 * @return
	 * 		If there is text to be spoken, it returns that information, otherwise
	 * 		it returns the empty string.
	 * @throws NoSuchElementException
	 * 		If the image provided is not in the current category.
	 */
	public String select(String imageLoc) throws NoSuchElementException {
		if (!this.categories.hasKey(imageLoc)) {
			return this.current.select(imageLoc);
		} else {
			try {
				this.current = this.categories.get(imageLoc);
			} catch (KeyNotFoundException e) {
				System.err.println("Error: image does not exist in category.");
			} // try
			return "";
		} // if
	} // select(String)
	
	/**
	 * Provides an array of all the images in the current category.
	 *
	 * @return
	 * 		The array of images in the current category; if there are no images,
	 * 		it should return an empty array.
	 */
	public String[] getImageLocs() {
		if (this.current != null) {
			return this.current.getImageLocs();
		} else {
			return new String[0];
		} // if
	} // getImageLocs()
	
	/**
	 * Resets the current category of the AAC back to the default
	 * category.
	 */
	public void reset() {
		this.current = null;
	} // reset()
	
	/**
	 * Writes the ACC mappings stored to a file. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image
	 *
	 * for instance:
	 * img/food/plate.png food
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 *
	 * represents the file with two categories, food and clothing
	 * and food has french fries and watermelon and clothing has a
	 * collared shirt.
	 *
	 * @param filename
	 * 		The name of the file to write the AAC mapping to.
	 */
	public void writeToFile(String filename) {
		// FileWriter writer = new FileWriter(filename);
		// String[] names = new String[this.categories.size()], images;
		// AACCategory category;
		
		// try {
		// 	if (this.categories != null) {
		// 		for (int i = 0; i < categories.size(); i++) {
		// 			category = this.categories.pairs[i].val;

		// 			writer.write(this.categories.pairs[i].key + " "
		// 					+ category.getCategory() + "\n");
		// 		} // for
		// 	} else {
		// 		;
		// 	}
		// } catch (IOException e) {
		// 	;
		// }
	} // writeToFile(String)
	
	/**
	 * Adds the mapping to the current category (or the default category if
	 * that is the current category).
	 *
	 * @param imageLoc
	 * 		The location of the image.
	 * @param text
	 * 		The text associated with the image.
	 */
	public void addItem(String imageLoc, String text) {
		if (this.current != null) {
			current.addItem(imageLoc, text);
		} else {
			try {
				this.categories.set(imageLoc, new AACCategory(text));
			} catch (NullKeyException e) {
				System.err.println("Error: Cannot add null item.");;
			} // try
		} // if
	} // addItem(String, String)
	
	/**
	 * Gets the name of the current category.
	 *
	 * @return
	 * 		The current category or the empty string if
	 * 		on the default category.
	 */
	public String getCategory() {
		return this.current.getCategory();
	} // getCategory()
	
	/**
	 * Determines if the provided image is in the set of images that
	 * can be displayed and false otherwise.
	 *
	 * @param imageLoc
	 * 		The location of the category.
	 * @return
	 * 		True if it is in the set of images that
	 * 		can be displayed, false otherwise.
	 */
	public boolean hasImage(String imageLoc) {
		return this.categories.hasKey(imageLoc);
	} // hasImage(String)
} // AACMappings class
