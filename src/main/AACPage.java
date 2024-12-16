import java.util.NoSuchElementException;

/**
 * This interface represents a set of information that would
 * be displayed together on an AAC.
 *
 * @author Catie Baker
 */
public interface AACPage {

	/**
	 * Adds the image location, text pairing to the page as
	 * either a pairing of items to be spoken or
	 * a pairing of image to a category or similar.
	 *
	 * @param imageLoc
	 * 		The location of the image.
	 * @param text
	 * 		The text that image should speak.
	 */
	public void addItem(String imageLoc, String text);
	
	/**
	 * Returns an array of all the images to be displayed on
	 * the page.
	 *
	 * @return
	 * 		The array of image locations; if there are no images,
	 * 		it should return an empty array.
	 */
	public String[] getImageLocs();
	
	/**
	 * Returns the name of the current category.
	 *
	 * @return
	 * 		The name of the current category.
	 */
	public String getCategory();

	/**
	 * Given the image location selected, it determines the action to be
	 * taken. This can be updating the information that should be displayed
	 * or return text to be spoken.
	 *
	 * @param imageLoc
	 * 		The location where the image is stored.
	 * @return
	 * 		If there is text to be spoken, it returns that information, otherwise
	 * 		it returns the empty string.
	 *
	 * @throws NoSuchElementException
	 * 		If the image provided is not in the current
	 * 		category.
	 */
	public String select(String imageLoc);

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
	public boolean hasImage(String imageLoc);
} // class AACPage
