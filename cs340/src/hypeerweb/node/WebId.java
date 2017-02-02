package hypeerweb.node;

import java.io.Serializable;

/**
 * The id of a node in the HyPeerWeb.<br>
 * <br>
 * 
 * <pre>
 * <b>Domain:</b>
 *      id     : int
 *      height : int
 * 
 * <b>Invariant:</b>
 *      id &ge; -1 AND
 *      height &ge; -1 AND
 *      height &le; MAX_HEIGHT AND
 *      (id = -1 OR height = -1) &rArr; this = NULL_WEB_ID) AND
 *      id &ge; 0 &rArr; height &ge; &lceil;log<sub>2</sub>(id + 1)&rceil;
 *      
 * -- In other words:
 * -- The id must be greater than or equal to 0 and the only webId that can have the id = -1 or height = -1 is the NULL_WEB_ID.
 * </pre>
 * 
 * @author Scott Woodfield
 */

public class WebId implements Serializable {

	
	private static final long serialVersionUID = -2011048356258455777L;

	// Domain Implementation
    /**
     * The value for this webId.
     */
    private int id;
    
    /**
     * The height of the webId.
     */
    private int height;
    
    /**
     * The one and only NULL webId.
     */
    public static final WebId NULL_WEB_ID = new WebId();

    // Constructors
    /**
     * The default constructor.  It is private and should only be used to construct the NULL_WEB_ID;
     * 
     * @pre <i>None</i>
     * @post id = -1 AND height = -1
     */
    private WebId() {
        id = -1;
        height = -1;
        mask = 0;
        highOrderBitMask = 1;
        
        //The invariant is checked in the static block below.
    }
    
    /**
     * The standard webId constructor that creates a new web id from the given id.
     * 
     * @param id  The value of the new webId.
     * @pre id &ge; 0
     * @post this.id = id AND height = locationOfMostSignificantOneBit(id) + 1
     */
    public WebId(int id) {
        assert id >= 0;
        this.id = id;
        height = locationOfMostSignificantOneBit(id) + 1;
        mask = ~(-1 << height);
        highOrderBitMask = 1 << height - 1;  
        
        assert invariant(this);
    }
    
    /**
     * The standard webId constructor that creates a new web id from the given id, and height.
     * 
     * @param id  The value of the new webId.
     * @param height The height of the new webId.
     * @pre id &ge; 0 AND height >= locationOfMostSignificantOneBit(id) + 1)
     * @post this.id = id AND this.height = height
     */
    public WebId(int id, int height) {
        assert id >= 0 && height >= locationOfMostSignificantOneBit(id) + 1 && height <= 31;
        this.id = id;
        this.height = height;
        mask = ~(-1 << height);
        highOrderBitMask = 1 << height - 1;
    }

    // Queries
    /**
     * Returns the hash code of this webId.  Used for the key in hashMaps.
     * 
     * @pre <i>None</i>
     * @post result = id
     */
    public int hashCode() {
        return id;
    }
    /**
     * Returns a binary string representation of this webId.
     * 
     * @pre <i>None</i>
     * @post this = NULL_WEB_ID &rArr; result = "NULL WEB ID" AND <br>
     *       this &ne; NULL_WEB_ID &rArr; |result| = height AND id > 0 &rArr; id = Integer.valueOf(result, 2)
     */
    public String toString() {
        String result = null;
        if(id == -1) {
            result = "NULL WEB ID";
        } else if(height == 0) {
            result = "";
        } else {
            StringBuffer buffer = new StringBuffer();
            String binaryString = Integer.toBinaryString(id);
            int binaryStringLength = binaryString.length();
            for(int i = 0; i < height - binaryStringLength; i++) {
                buffer.append('0');
            }
            buffer.append(binaryString);
            result = buffer.toString();
        }
        return result;
    }

    /**
     * Returns whether this webId is equal to the object passed in.
     * 
     * @pre <i>None</i>
     * @post result = object &ne; null AND <br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                object instanceof WebId AND <br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                id = object.id AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                height = object.height
     */
    public boolean equals(Object object) {
        boolean result = (object != null) && (object instanceof WebId);
        if (result) {
            WebId otherWebId = (WebId)object;
            result = id == otherWebId.id && height == otherWebId.height;
        }
        return result;
    }

    /**
     * The id getter.
     * 
     * @pre <i>None</i>
     * @post result = id
     */
    public int getValue() {
        return id;
    }

    /**
     * The height getter.
     * 
     * @pre <i>None</i>
     * @post result = height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Tests whether or not this is a neighbor or the webId.
     * 
     * @param webId the possible neighbor we are comparing against.
     * 
     * @pre webId &ne; null AND <br>
     *      webId &ne; NULL_WEB_ID AND <br>
     *      this &ne; NULL_WEB_ID AND
     * @post result = this.distanceTo(webId) = 1 AND <br>
     *                height > webId.height => id > webId.id AND<br>
     *                height < webId.height => id < webId.id
     */
    public boolean isNeighborOf(WebId webId) {
        assert webId != null && webId != NULL_WEB_ID && id >= 0;
        
        boolean result = distanceTo(webId) == 1;
        if (result) {
            if(height + 1 == webId.height) {
                result = id > webId.id;
            } else if(height == webId.height + 1) {
                result = id < webId.id;
            } else {
                result = height == webId.height;
            }
        }
        return result;
    }
    
    /**
     * Tests whether or not this is a surrogate neighbor of webId.<br>             
     * <br>
     * A webId x is the surrogate neighbor of a node y iff<br>
     *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *    x.height + 1 = y.height                   AND<br>
     *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *    y.toString().charAt(y.toString()-1) = '1' AND -- the high order bit of y is 1. Because the height of x is shorter its corresponding bit is 0.<br>
     *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *    x.distanceTo(y) = 2                           -- That is, the bits at location webId.height of x and y differ plus one and only one other bit.
     * 
     * @param webId The other webId we are going to compare against.
     * 
     * @pre webId &ne; null AND <br>
     *      webId &ne; NULL_WEB_ID AND <br>
     *      this &ne; NULL_WEB_ID
     *      
     * @post result = height + 1 = webId.height AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                webId.id &amp; webId.highOrderBit &ne; 0 AND <br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                (~(-1 << height) &amp; webId.Id) < id AND <br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                distanceTo(webId) = 2<br>
     *  <br>
     */
    public boolean isSurrogateNeighborOf(WebId webId) {

        assert webId != null && webId != NULL_WEB_ID && height >  0;
        
        boolean result = height + 1 == webId.height &&
                         ((webId.id & webId.highOrderBitMask) != 0) &&
                         (mask & webId.id) < id &&
                         distanceTo(webId) == 2;
                         
        return result;
    }
    
    /**
     * Tests whether or not this is the fold of webId.<br>
     * 
     * @param webId The webId we are going to test against.
     * 
     * @pre webId &ne; null AND <br>
     *      webId &ne; NULL_WEB_ID AND <br>
     *      this &ne; NULL_WEB_ID
     * @post result = height = webId.height &rArr; result = (~(-1 << height) &amp; id) = (~(-1 << webId.height) &amp; ~webId.id) AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                height + 1 = webId.height + 1 &rArr; result = (~(-1 << height) &amp; id) = (~(-1 << height) &amp; ~webId) AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                height = webId.height + 1 &rArr; result = (~(-1 << webId.height) &amp; id) = (~(-1 << webId.height) &amp; ~webId.webId) AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                height &ne; webId.height && height + 1 &ne; webId.height && height &ne webId.id + 1 &rArr; result = false
     *               )
     */
    public boolean isFoldOf(WebId webId) {
        assert webId != null && webId != NULL_WEB_ID && id >= 0;
        
        boolean result = false;
        if((id == -1 && webId.id != -1) || (id != -1 && webId.id == -1)) {
            result = true;
        } else if(id != -1 && webId.id != -1) {
            if (height == webId.height) {
                result = (mask & id) == (webId.mask & ~webId.id);
            } else if(height + 1 == webId.height && (webId.id & ~webId.highOrderBitMask) < id) {
                result = (mask & id) == (mask & ~webId.id);
            } else if(height == webId.height + 1 && (id & ~highOrderBitMask) < webId.id) {
                result = (webId.mask & id) == (webId.mask & ~webId.id);
            }
        }

        return result;
    }
    
    /**
     * Tests whether or not this is the surrogate fold to webId.
     * 
     * @param webId The webId we are going to test against.
     * 
     * @pre webId &ne; null AND <br>
     *      webId &ne; NULL_WEB_ID AND <br>
     *      this &ne; NULL_WEB_ID
     * @post result = height + 1 = webId.height AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                webId.id < id             AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                (~(-1 << height) &amp; webId.id = ~(~(-1 << height) &amp; id)
     */
    public boolean isSurrogateFoldOf(WebId webId) {
        assert webId != null && webId != NULL_WEB_ID && id >= 0;

        boolean result = (height + 1 == webId.height) &&
                         (webId.id < id)              &&
                         (mask & webId.id) == (mask & ~id);

        
        return result;
    }
    
    /**
     * Tests whether or not this is the inverse surrogate fold to webId.
     * 
     * @param webId The webId we are going to test against.
     * 
     * @pre webId &ne; null AND <br>
     *      webId &ne; NULL_WEB_ID AND <br>
     *      this &ne; NULL_WEB_ID
     * @post result = height = webId.height + 1AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                id < webId.id                  AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                (~(-1 << webId.height) &amp; webId.id = ~(~(-1 << webId.height) &amp; id)
     */
    public boolean isInverseSurrogateFoldOf(WebId webId) {
        assert webId != null && webId != NULL_WEB_ID && id >= 0;

        boolean result = (height == webId.height + 1) &&
                         (id < webId.id)              &&
                         (webId.mask & webId.id) == (webId.mask & ~id);

        
        return result;
    }
    
    /**
     * Creates a child's webId.
     * 
     * @pre <i>None</i>
     * @post result.height = height' + 1 AND result.id = id + 1 << height
     */
    public WebId createChildsWebId() {
        WebId result = new WebId();
        
        result.height = height + 1;
        result.id = id | 1 << height;
        mask = mask << 1 | 1;
        
        return result;
    }
    
    /**
     * Computes the value for the parent's webId.
     *
     * @pre id > 0
     * @post result = id & ~(1 << height)
     */
    public int getParentsValue() {
        assert id > 0 && height > 0;
        
        int result = id & ~(1 << (height - 1));
        return result;
    }
    
    /**
     * Determines whether this is the webId of a cap node.
     * 
     * @pre id &ge; 0
     * @post height = 0 &rArr; result = true AND
     *       height > 0 &rArr; result = ((1 << height) - 1) == id
     */
    public boolean isCapNode() {
        assert id >= 0;
        
        boolean result = false;
        if(height == 0) {
            result = true;
        } else if(height > 0) {
            result = ((1 << height) - 1) == id;
        }
        
        return result;
    }

    // Commands
    
    /**
     * Increments the height of the webId by one.
     * 
     * @pre <i>None</i>
     * @post height = height' + 1
     */
    public void incrementHeight() {
        height = height + 1;
        mask = ~(-1 << height);
        highOrderBitMask = 1 << height - 1;  
    }
    
    /**
     * Decrements the height of the webId by one.
     * 
     * @pre <i>None</i>
     * @post height = height' - 1
     */
    public void decrementHeight() {
        height = height - 1;
        mask = ~(-1 << height);
        highOrderBitMask = 1 << height - 1;  
    }
    

    //Auxiliary Constants, Variables, Methods, and Classes
    /**
     * The mask used to set the first 32 - height bits of an id to 0.
     */
    private int mask = 0;
    
    /**
     * The mask that allows us to select the bit at location 'height'.
     */
    private int highOrderBitMask = 1;
    
    /**
     * The maximum height of a WebId.
     */
    public static final int MAX_HEIGHT = 31;
    
    /**
     * Implements the invariant.
     * 
     * @param webId  The webId we are going to test to see if it satisfies the invariant.
     * 
     * @pre webId &ne; null
     * @post result = webId.id &ge; -1 AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                webId.height &ge; -1 AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                webId.height &le; MAX_HEIGHT AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                (webId.id = -1 OR webId.height = -1) &rArr; webId = NULL_WEB_ID AND<br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                webId.id &ge; 0 &rArr; webId.height &ge; &lceil;log<sub>2</sub>(webId.id + 1)&rceil;
     */
    private static boolean invariant(WebId webId) {
        boolean result = webId.id >= -1 &&
                         webId.height >= -1 &&
                         webId.height <= MAX_HEIGHT &&
                         (!(webId.id == -1 || webId.height == -1) || webId == NULL_WEB_ID) &&
                         (webId.id < 0 || webId.height >= locationOfMostSignificantOneBit(webId.id) + 1);
        return result;
    }

    /**
     * Returns the location of the highest one bit in the id.
     * 
     * @param id We will find the location of the highest one bit of this id.
     * @pre id &ge; 0
     * @post id = 0 &rArr; result = -1 AND<br>
     *       id > 0 &rArr; Integer.toBinaryString(id).charAt(result) = '1' AND <br>
     *                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                     &forall; result &lt; i < 32 (Integer.toBinaryString(id).charAt(i) = '0')
     */
    public static int locationOfMostSignificantOneBit(int id) {
        assert id >= 0;

        int result = -1;
        while (id > 0) {
            result++;
            id >>>= 1;
        }
        return result;
    }

    /**
     * Returns the number of locations in which the bits of this id differ from the bits in the other webId's id.
     * 
     * @param webId the other webId whose id we are going to compare this id against.
     * 
     * @pre webId &ne; null AND <br>
     *      id &ge; 0 AND <br>
     *      webId.id &ge; 0
     * @post result = NumberOf 0 &le; i &lt; min(height, webId.height) (id.toString().charAt(i) &ne; webId.toString().charAt(i)) + <br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                NumberOf min(height, webId.height) &le; i &lt; height (toString().charAt(i) = '1') + <br>
     *                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     *                NumberOf min(height, webId.height) &le; i &lt; webId.height (webId.toString().charAt(i) = '1')
     */
    public int distanceTo(WebId webId) {
        assert id >= 0 && webId.id >= 0;

        int result = 0;
        if(id > mask) {
        	//Something is wrong..
        	mask = ~(-1 << height);
            highOrderBitMask = 1 << height - 1;  
        }
        int thisId = id & mask;
        int otherId = webId.id & webId.mask;
        while (thisId != 0 || otherId != 0) {
            int digit1 = thisId & 1;
            int digit2 = otherId & 1;
            if (digit1 != digit2) {
                result++;
            }
            thisId >>>= 1;
            otherId >>>= 1;
        }
        return result;
    }
    
    //This checks the invariant of the default constructor which is only used to create the NULL_WEB_ID
    static {
        assert invariant(NULL_WEB_ID);
        assert (NULL_WEB_ID.mask == 0 && NULL_WEB_ID.highOrderBitMask == 1);
    }

}
