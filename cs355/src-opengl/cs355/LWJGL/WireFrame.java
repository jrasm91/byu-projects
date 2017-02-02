package cs355.LWJGL;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Brennan Smith
 */
public class WireFrame 
{
    public Iterator<Line3D> getIterator()
    {
        return new ArrayList<Line3D>().iterator();
    }
}
