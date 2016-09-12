package org.drost.application;

/**
 * <p>
 * This example is the simplest form of the application usage and is the base
 * structure for the most other examples. This demonstration initializes all
 * fields when calling the constructor, therefore this class forces all
 * subclasses to invoke the super constructor {@link #AbstractExample(String)}
 * which allows to define a custom UID for the application instantiation.
 * </p>
 * The following examples shows a short snippet on how to extends this basic
 * example:
 * 
 * <pre>
 * public class ExtendedExample extends AbstractExample
 * {
 * 	public ExtendedExample()
 * 	{
 * 		super("ExampleApplication");
 * 	}
 * 
 * 	... other example code ...
 * }
 * </pre>
 * <p>
 * A basic example class that defines methods for each example that is a
 * subclass of this.
 * </p>
 * 
 * @author Yannick Drost
 * @since 1.0
 */
public abstract class AbstractExample
{
	protected static String ID = "ApplicationExample";
	
	protected static Application APPLICATION;
		
	public AbstractExample(String id)
	{
		ID = id;
		instantiate(ID);
	}
	
	/**
	 * Initializes the application instance specified by the {@code id}.
	 * 
	 * @param id
	 *            The name of the application.
	 */
	protected void instantiate(final String id)
	{
		APPLICATION = Application.launch(id);
	}
	
	
	/**
	 * Returns the examples name or identifier.
	 * 
	 * @return The examples name.
	 */
	public abstract String getName();
	
	
	/**
	 * Returns a short comment or description for this example.
	 * 
	 * @return A comment or description.
	 */
	public abstract String getInformation();
}
