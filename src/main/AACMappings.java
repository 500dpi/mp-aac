import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;

/**
 * Creates a set of mappings of an AAC that has two levels, one for
 * categories and then within each category, it has images that have
 * associated text to be spoken. This class provides the methods for
 * interacting with the categories and updating the set of images that
 * would be shown and handling an interactions.
 *
 * @author Catie Baker
 * @author Sara Jaljaa
 */
public class AACMappings implements AACPage {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

	/**
	 * Stores an AA that maps filenames to categories.
	 */
	private AssociativeArray<String, AACCategory> categories;

	/**
	 * The current category.
	 */
	private AACCategory current;

	/**
	 * The default category.
	 */
	private AACCategory root;
	
  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

	/**
	 * Creates a set of mappings for the AAC based on the provided file. The
	 * file is read in to create categories and fill each of the categories
	 * with initial items. The file is formatted as the text location of the
	 * category followed by the text name of the category and then one line
	 * per item in the category that starts with > and then has the file name
	 * and text of that image.
	 *
	 * For instance,
	 *
	 * img/food/plate.png food
	 * img/clothing/hanger.png clothing
	 *
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * >img/clothing/collaredshirt.png collared shirt
	 *
	 * represents the file with two categories, food and clothing and food has
	 * french fries and watermelon and clothing has a collared shirt.
	 *
	 * @param filename
	 * 		The name of the file that stores the mapping information.
	 */
	public AACMappings(String filename) {
		this.categories = new AssociativeArray<String, AACCategory>();
		this.root = new AACCategory("");
		this.current = this.root;
		String line = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			/* Until EOF, read a line and store the file name + text. */
			while ((line = br.readLine()) != null) {
				String[] metadata = line.split(" ", 2);
				if (metadata.length > 1) {
					/* If line is a category, add it to the rest of the categories & update
					the current category to match. */
					if (line.charAt(0) != '>') {
						this.reset();
						this.addItem(metadata[0], metadata[1]);
						this.current = this.categories.get(metadata[0]);
					/* If line is not a category & instead an item, add it to the current
					category.*/
					} else {
						this.addItem(metadata[0].substring(1), metadata[1]);
					} // elif
				} // if
			} // while
			this.current = this.root;
			br.close();
		} catch (IOException | KeyNotFoundException e) {
			/* Doesn't matter (IO) & shouldn't ever happen (KNF). */
			return;
		} // try/catch
	} // AACMappings(String)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

	/**
	 * Check if the current category is the default category.
	 *
	 * @return
	 * 		True if the current categories are equal.
	 */
	private boolean atRoot() {
		return this.current.equals(this.root);
	} // atRoot()

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

	/**
	 * Resets the current category of the AAC back to the default category.
	 */
	public void reset() {
		this.current = this.root;
	} // reset()

	/**
	 * Gets the name of the current category.
	 *
	 * @return
	 * 		The current category or the empty string (if on the default
	 * 		category).
	 */
	public String getCategory() {
		return this.current.getCategory();
	} // getCategory()
	
	/**
	 * Determines if the provided image is in the set of images that can be
	 * displayed and false otherwise.
	 *
	 * @param imageLoc
	 * 		The location of the category.
	 * @return
	 * 		True if it is in the set of images that can be displayed, false
	 * 		otherwise.
	 */
	public boolean hasImage(String imageLoc) {
		return this.current.hasImage(imageLoc);
	} // hasImage(String)

	/**
	 * Provides an array of all the images in the current category.
	 *
	 * @return
	 * 		The array of images in the current category; if there are no images,
	 * 		it should return an empty array.
	 */
	public String[] getImageLocs() {
		return this.current.getImageLocs();
	} // getImageLocs()
	
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
	 *
	 * @throws NoSuchElementException
	 * 		If the image provided is not in the current category.
	 */
	public String select(String imageLoc) throws NoSuchElementException {
		try {
			if (atRoot()) {
				this.current = this.categories.get(imageLoc);
				return "";
			} // if
			return this.current.select(imageLoc);
		} catch (KeyNotFoundException e) {
			throw new NoSuchElementException();
		} // try/catch
	} // select(String)

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
		try {
			if (atRoot()) {
				this.current.items.set(imageLoc, text);
				this.categories.set(imageLoc, new AACCategory(text));
			} else {
				this.current.addItem(imageLoc, text);
			} // elif
		} catch (NullKeyException e) {
			return;
		} // try/catch
	} // addItem(String, String)

	/**
	 * Writes the ACC mappings stored to a file. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image.
	 *
	 * For instance,
	 *
	 * img/food/plate.png food
	 * img/clothing/hanger.png clothing
	 *
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * >img/clothing/collaredshirt.png collared shirt
	 *
	 * represents the file with two categories, food and clothing and food has
	 * french fries and watermelon and clothing has a collared shirt.
	 *
	 * @param filename
	 * 		The name of the file to write the AAC mapping to.
	 */
	public void writeToFile(String filename) {
		try {
			/* To write to the file directly. */
			PrintWriter pen = new PrintWriter(new File(filename));
			AACCategory category;

			/* The category & image paths. */
			String[] paths = this.categories.keys();
			String[] imgs;

			/* Format a category, then format all of the images contained within
			the same category. */
			for (String path : paths) {
				category = this.categories.get(path);
				imgs = category.getImageLocs();
				pen.printf("%s %s\n", path, category.getCategory());
				for (String img : imgs) {
					pen.printf(">%s %s\n", img, category.select(img));
				} // for (images)
			} // for (locations)
			pen.close();
		} catch (IOException | KeyNotFoundException e) {
		/* Doesn't matter (IO) & shouldn't ever happen (KNF). */
			return;
		} // try/catch
	} // writeToFile(String)
} // class AACMappings
