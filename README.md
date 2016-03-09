Crafty
======

A library for easily setting up your own Java applications. All done in a few lines of code. Beside providing classes to handle resources or even the applications data storage this library comes along with other several suppliers to reinstate the application state or even to control newer versions by updating the application packages.

Features
--------
- Support for command-line and GUI applicatins.
- A Single application instance feature to prevent a second execution.
- Checks if an appropriate Java version is installed, otherwise it will open a support website (http://java.com/download).
- Handles local file and resource storage.
- Ability to restore the graphical user interface on relaunch.
- Option to change current directory to the executable location.
- Provides a Mac OS application adapter to adapt the UI appearance.
- Restart the application.
- Gives you the main class name.
- Option to get the executable jar file name.
- Option to get the path of the executable jar file.

Getting started
---------------
To initialize an application simply call the `Application.launch(String)` method.
```java
public class ApplicationExample
{
    public static void main( String[] args )
    {
        // Initialize the application by using a proper class name
        Application.launch( ApplicationExample.getClass().getSimpleName() );
        
        // Another way to initialize the application is to use an identifier:
        // Application.launch( "MyApplication" );
        
        // Your application logic here ...
        
        // Closes the application and shuts down the JVM
        Application.get().exit();
    }
}
```
Get the application instance by `Application.get()` to gain access to all of the application features.
