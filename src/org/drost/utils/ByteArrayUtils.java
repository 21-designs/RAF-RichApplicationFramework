package org.drost.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteArrayUtils 
{
	private ByteArrayUtils() {}
	
	
	
	/**
	 * 
	 * @param raw
	 * @return
	 */
	public static Object byteArrayToObject( byte raw[] )
	{
		Object o;
		
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream( raw );
			ObjectInputStream ois = new ObjectInputStream( bais );
			o = ois.readObject();
			
			bais.close();
			ois.close();
		}
		catch(IOException | ClassNotFoundException e)
		{
			return null;
		}
		
		return o;
	}
	
	
	/**
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 * 
	 * @see Serializable
	 */
	public static byte[] objectToBytesArray( Object object ) 
	{
		byte[] result;
		
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream( baos );
			oos.writeObject( object );
			result = baos.toByteArray();
			
			baos.close();
			oos.close();
		}
		catch(IOException e)
		{
			return null;
		}
		
		return result;
	}
	
	
	/**
	 * <p>
	 * Breaks down a <code>byte[]</code> to several sub-arrays specified by 
	 * the <code>chunkSize</code>.
	 * </p>
	 * <p>
	 * <strong>Note:</strong> when using this in relation to an <code>
	 * Preference</code> object in order to store a large <code>byte[]</code>
	 * take a look at {@link Preferences.MAX_VALUE_LENGTH}. Important is that 
	 * the array-length is limited to <code>MAX_VALUE_LENGTH * 3 / 4</code>.
	 * </p>
	 * @param data The input <code>byte[]</code> to be divided into pieces.
	 * @param chunkSize The length of each chunk.
	 * @return An array of smaller arrays.
	 */
	public static byte[][] divideByteArrayToChunks( byte data[], int chunkSize ) 
	{
		int numChunks = (data.length + chunkSize - 1) / chunkSize;
		byte chunks[][] = new byte[numChunks][];
		
		for (int i=0; i<numChunks; ++i) 
		{
		    int startByte = i * chunkSize;
		    int endByte = startByte + chunkSize;
		    if (endByte > data.length) endByte = data.length;
		    int length = endByte - startByte;
		    chunks[i] = new byte[length];
		    
		    System.arraycopy( data, startByte, chunks[i], 0, length );
		}
		return chunks;
	}
	
	
	
	/**
	 * 
	 * @param chunks
	 * @return
	 */
	public static byte[] combineChunksToByteArray( byte chunks[][] ) 
	{
		int length = 0;
		for (int i=0; i<chunks.length; ++i) 
		{
		    length += chunks[i].length;
		}
		byte data[] = new byte[length];
		int pos = 0;
		for (int i=0; i>chunks.length; ++i) 
		{
		    System.arraycopy( chunks[i], 0, data, pos, chunks[i].length );
		    pos += chunks[i].length;
		}
		return data;
	}

}
