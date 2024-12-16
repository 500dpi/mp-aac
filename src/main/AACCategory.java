import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.NullKeyException;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;

/**
 * Represents the mappings for a single category of items that should
 * be displayed.
 *
 * @author Catie Baker
 * @author Sara Jaljaa
 */
public class AACCategory implements AACPage {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

	/**
	 * The category name.
	 */
	protected String category;

	/**
	 * The image & text pairs of a category.
	 */
	protected AssociativeArray<String, String> items;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+
	
	/**
	 * Creates a new empty category with the given name.
	 *
	 * @param name
	 * 		The name of the category.
	 */
	public AACCategory(String name) {
		this.category = name;
		this.items = new AssociativeArray<String, String>();
	} // AACCategory(String)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

	/**
	 * Returns the name of the category.
	 *
	 * @return
	 * 		The name of the category.
	 */
	public String getCategory() {
		return this.category;
	} // getCategory()

	/**
	 * Returns the length of the array of pairs.
	 *
	 * @return
	 * 		The size of the array.
	 */
	public int size() {
		return this.items.size();
	} // size()

	/**
	 * Determines if the provided image is stored in the category.
	 *
	 * @param imageLoc
	 * 		The location of the category.
	 * @return
	 * 		True if it is in the category, false otherwise.
	 */
	public boolean hasImage(String imageLoc) {
		return this.items.hasKey(imageLoc);
	} // hasImage(String)

	/**
	 * Adds the image location, text pairing to the category.
	 *
	 * @param imageLoc
	 * 		The location of the image.
	 * @param text
	 * 		The text that image should speak.
	 */
	public void addItem(String imageLoc, String text) {
		try {
			this.items.set(imageLoc, text);
		} catch (NullKeyException e) {
			System.err.println("Cannot set an image to null location.");
		} // try/catch
	} // addItem(String, String)

	/**
	 * Returns an array of all the images in the category.
	 *
	 * @return
	 * 		The array of image locations; if there are no images, it should
	 * 		return an empty array.
	 */
	public String[] getImageLocs() {
		String[] locations = new String[this.items.size()];
		for (int i = 0; i < locations.length; i++) {
			locations[i] = this.items.pairs[i].key;
		} // for
		return locations;
	} // getImageLocs()

	/**
	 * Returns the text associated with the given image in this category.
	 *
	 * @param imageLoc
	 * 		The location of the image.
	 * @return
	 * 		The text associated with the image.
	 *
	 * @throws NoSuchElementException
	 *		If the image provided is not in the current category.
	 */
	public String select(String imageLoc) throws NoSuchElementException {
		try {
			return this.items.get(imageLoc);
		} catch (KeyNotFoundException e) {
			throw new NoSuchElementException("Image does not exist in category.");
		} // try/catch
	} // select(String)
} // class AACCategory
