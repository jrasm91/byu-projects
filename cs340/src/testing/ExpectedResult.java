package testing;

import hypeerweb.SimplifiedNodeDomain;
import hypeerweb.node.NodeState;

import java.util.HashSet;
import java.util.Iterator;




/**
 * Given integer values for the hyPeerWebSize and the webId we can compute integer values
 * representing the height, neighbors, upPointers, downPointers, fold, surrogateFold,
 * inverseSurrogateFold, and state of a node.  The constructor for this class does just that
 * representing the result as a SimplifiedNodeDomain.
 * 
 * <pre>
 * Domain:
 *     Inherited from SimplifiedNodeDomain
 * </pre>
 * 
 * @author Scott
 */
public class ExpectedResult extends SimplifiedNodeDomain {
    // Domain Implementation
    // Inherited from SimplifiedNodeDomain

    // Constructors
    /**
     * Constructs an SimplifiedNodeDomain representation of the expected webId, neighbors, upPointers,
     * downPointers, fold, surrogateFold, inverseSurrogateFold, and state of a node given integer values
     * for the hyPeerWebSize and webId value.
     * 
     * @param hyPeerWebSize the number of nodes in the hypothetical hyPeerWeb
     * @param webId the value of the webId for a hypothetical node in the hyPeerWeb
     * @pre hyPeerWebSize &ge; 1 AND 0 &le; webId &lt; hyPeerWebSize
     * @post this.webId = webId AND <br>
     * height = the expected height of this hypothetical node AND <br>
     * neighbors = a hash set of integers representing all the neighbors of the hypothetical node AND <br>
     * upPointers = a hash set of integers representing all the upPointers of the hypothetical node AND <br>
     * downPointers = a hash set of integers representing all the downPointers of the hypothetical node AND <br>
     * fold = expected integer value of the webId for the fold AND <br>
     * surrogateFold = expected integer value of the webId for the surrogateFold AND <br>
     * inverseSurrogateFold = expected integer value of the webId of the surrogateFold AND <br>
     * state = expected state's STATE_ID
     */
    public ExpectedResult(int hyPeerWebSize, int webId) {
        super();
        assert hyPeerWebSize >= 1;
        assert webId >= 0 && webId < hyPeerWebSize;

        cubeHeight = (int) Math.ceil((Math.log(hyPeerWebSize) / log2));
        ExpectedResult.hyPeerWebSize = hyPeerWebSize;
        
        this.webId = webId;
        height = computeExpectedHeight();
        ExpectedResult.lastIdOfEmbeddedHyperCube = ((int) Math.pow(2.0d, Math.floor(Math.log(hyPeerWebSize) / log2))) - 1;
        ExpectedResult.idMask = cubeHeight == 0 ? 1 : -1 >>> (32 - cubeHeight);
        neighbors = expectedNeighbors();
        upPointers = expectedUpPointers();
        downPointers = expectedDownPointers();
        fold = expectedFold();
        surrogateFold = expectedSurrogateFold();
        inverseSurrogateFold = expectedInverseSurrogateFold(webId);
        state = expectedState();
    }

    // Queries

    // Commands

    // Local Information
    /**
     * The number of nodes in the hypothetical HyPeerWeb.
     */
    private static int hyPeerWebSize;
    
    /**
     * The height of the smallest perfect hypercube that would contain the hyperweb of size <i>hyPeerWebSize</i>.
     */
    private static int cubeHeight;
    
    /**
     * The webId of the cap node of the largest hypercube embedded in the hypothetical HyPeerWeb with <i>hyPeerWebSize<i> nodes.
     */
    private static int lastIdOfEmbeddedHyperCube;
    
    /**
     * An integer where the rightmost <i>cubeHight</i> bits are ones.  The other bits are zero.  If the <i>cubeHeight</i> is zero
     * then the idMask is 1.
     */
    private static int idMask;
    
    /**
     * Computes the expected height of this hypothetical node given the HyPeerWeb size and webId.
     * 
     * @pre <i>None</i>
     * @post If the hyPeerWebSize = cubeHeight then result = cubeHeight
     *       else if the webId is greater than the webId of the cap node of the largest embedded hypercube then result = cubeHeight
     *       else if the webId has a child webId less than the hyPeerWebSize then result = cubeHeight
     *       else result = cubeHeight - 1
     */
    private int computeExpectedHeight(){
        int result = cubeHeight;
        if((hyPeerWebSize - 1) > lastIdOfEmbeddedHyperCube && webId <= lastIdOfEmbeddedHyperCube && childOf(webId) == -1){
            result--;
        }
        return result;
    }

    /**
     * Returns a hash set of integers representing all the neighbors of the hypothetical node.
     * 
     * @pre <i>None</i>
     * @post &forall; 0 &le; i &lt; hyPeerWebSize (i &isin;  result &hArr; i is a neighboring webId of webId)
     */
    private HashSet<Integer> expectedNeighbors() {
        HashSet<Integer> result = new HashSet<Integer>();

        for (int digit = 0; digit < cubeHeight; digit++) {
            int neighbor = flipBit(digit);
            if (exists(neighbor)) {
                result.add(neighbor);
            }
        }
        return result;
    }

    /**
     * Returns a hash set of integers representing all the neighbors of the hypothetical node in the enclosing perfect hypercube.
     * 
     * @pre <i>None</i>
     * @post &forall; 0 &le; i &lt; cubeSize (i &isin;  result &hArr; i is a neighboring webId of webId)
     */
    private HashSet<Integer> allPossibleNeighbors() {
        HashSet<Integer> result = new HashSet<Integer>();

        for (int digit = 0; digit < cubeHeight; digit++) {
            int neighbor = flipBit(digit);
            result.add(neighbor);
        }
        return result;
    }


    /**
     * Returns a hash set of integers representing all the upPointers of the hypothetical node.
     * 
     * @pre <i>None</i>
     * @post &forall; largestEmbeddedHypercube.capNode.webId  &lt; i &lt; hyPeerWebSize (i &isin;  result &hArr; parent(i) is a neighboring webId of webId)
     */
    private HashSet<Integer> expectedUpPointers() {
        HashSet<Integer> result = new HashSet<Integer>();
        if (webId <= lastIdOfEmbeddedHyperCube && childOf(webId) == -1) {
            HashSet<Integer> neighbors = expectedNeighbors();
            for (int neighbor : neighbors) {
                int child = childOf(neighbor);
                if (child > -1 && child != webId) {
                    result.add(child);
                }
            }
        }
        return result;
    }

    /**
     * Returns a hash set of integers representing all the downPointers of the hypothetical node.
     * 
     * @pre <i>None</i>
     * @post &forall; hyPeerWebSize &le; i &lt; cubeSize (parent(i) &isin;  result &hArr; i is a neighboring webId of webId)
     */
    private HashSet<Integer> expectedDownPointers() {
        HashSet<Integer> result = new HashSet<Integer>();
        if (webId > lastIdOfEmbeddedHyperCube) {
            HashSet<Integer> neighbors = allPossibleNeighbors();
            for (int neighbor : neighbors) {
                if (!exists(neighbor)) {
                    result.add(parentOf(neighbor));
                }
            }
        }
        return result;
    }

    /**
     * Returns the expected fold of the given webId.
     * 
     * @pre <i>None</i>
     * @post ~webId & idMask < hyPeerWebSize &rArr; result = ~webId & idMask AND
     *  ~webId & idMask &ge; hyPeerWebSize &rArr; result = -1
     */
    private int expectedFold() {
        int result =  ~webId & idMask;
        if(height == 0) {
            result = 0;
        } else {
            result =  ~webId & idMask;
            if(height == cubeHeight) {
                if(!exists(result)) {
                    result = -1;
                }
            } else { //height < cubeHeight
                if(!exists(result)) {
                    result = ~webId & (idMask >> 1);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Returns whether or not the webId has an existing parent.
     * 
     * @param webId the webId we are going to test.
     * @pre 0 &lt; webId &lt; hyPeerWebSize
     * @post result = &exists 0 &le; i &lt; hyPeerWebSize (i = webId | 1 << cubeHeight -1)
     */
    private boolean hasParent(int webId) {
        boolean result = exists(webId | (1 << cubeHeight-1));
        return result;
    }

    /**
     * Computes the expectedSurrogateFold for this webId.
     * 
     * @pre <i>None</i>
     * @post expectedFold() > -1 &rArr; result = -1 AND<br>
     *       expectedFold() = -1 &rArr; result = ~webId AND ~(-1 << cubeHeight - 1)
     */
    private int expectedSurrogateFold() {
        int expectedFold = expectedFold();
        int result = -1;
        if(expectedFold == -1) {
            result = ~webId & ~(-1 << cubeHeight - 1);
        }
        return result;
    }


    /**
     * Computes the expectedInverseSurrogateFold for this webId.
     * 
     * @pre <i>None</i>
     * @post (height < cubeHeight OR height = 0) AND 0 &le; (~webId & idMask) &lt; hyPeerWebSize AND NOT hasParent(webId) &rArr; result = parentOf(~webId & idMask) <br>
     * ELSE result = -1
     */
    private int expectedInverseSurrogateFold(int webId) {
        int result = -1;
        if(height < cubeHeight || height == 0) {
            int fold = ~webId & idMask;
            if(exists(fold) && !hasParent(webId)) {
                result = parentOf(fold);
            }
        }
        return result;
    }

    /**
     * Computes the expected State.
     * 
     * @pre <i>None</i>
     * @post webId = 0 AND height = 0 &rArr; result = HypercubeCapState.STATE_ID AND<br>
     * webId > lastIdOfEmbeddedHyperCube && everyNeighborIsSmallerThan(webId) &rArr; result = TerminalNodeState.STATE_ID AND<br>
     * |downPointers| > 0 &rArr; result = DownPointingNodeState.STATE_ID AND<br>
     * |upPointers| > 0 &rArr; result = UpPointingNodeState.STATE_ID AND<br>
     * webId = hyPeerWebSize - 1 AND hyPeerWebSize = cubeSize &rArr; result = HypercubeCapState.STATE_ID
     */
    private NodeState expectedState() {
        NodeState result = NodeState.STANDARD;
        
        if(webId == 0 && height == 0) {
            result = NodeState.HYPEERCUBECAP;
        } else if (webId > lastIdOfEmbeddedHyperCube && everyNeighborIsSmallerThan(webId)) {
            result = NodeState.TERMINAL;
        } else if(downPointers.size() > 0) {
            result = NodeState.DOWNPOINTING;
        } else if(upPointers.size() > 0) {
            result = NodeState.UPPOINTING;
        } else if (webId == hyPeerWebSize-1) {
            result = NodeState.HYPEERCUBECAP;
        }
        return result;
    }
    
    /**
     * Determines whether all of the neighbors are smaller the the given webId.
     * 
     * @param webId the webId we are comparing against.
     * 
     * @pre <i>None</i>
     * @post result = &forall; i &isin; neighbors (i < webId)
     */
    private boolean everyNeighborIsSmallerThan(int webId) {
        boolean result = true;
        Iterator<Integer> iterator = neighbors.iterator();
        while(result && iterator.hasNext()) {
            result = iterator.next() < webId;
        }
        return result;
    }

    /**
     * Flips the bit at location "digit" in the webId
     * 
     * @param digit the digit we are going to flip
     * 
     * @pre 0 &le; digit &lt; 32
     * @post result.binaryRepresentation = ~webId.binaryRepresentation[digit]
     */
    private int flipBit(int digit) {
        int bitMask = 1 << digit;
        int bit = webId & bitMask;
        int result = 0;
        if (bit == 0) {
            result = webId | bitMask;
        } else {
            result = webId & ~bitMask;
        }
        return result;
    }

    /**
     * Returns the parent id of the provided id.
     * @param id the id we 
     * 
     * @pre <i>None</i>
     * @post id &le; 0 &rArr; result = -1 AND<br>
     *  id &gt; 0 &rArr; result = id & (1 << locationOfHighestOneBit(id))
     */
    private int parentOf(int id) {
        int result = -1;
        if (id > 0) {
            int mask = 1 << locationOfHighestOneBit(id);
            result = id & ~mask;
        }
        return result;
    }

    /**
     * Computes the child of the given id.
     * 
     * @param id the webId we are trying to compute the child id for.
     *
     * @pre id &ge; 0
     * @post (cubeHeight &le; 0) OR ((id | (1 << (cubeHeight - 1))) = id OR 0 &le; (id | (1 << (cubeHeight - 1))) &lt; hyPeerWebSize) &rArr; result = -1 ELSE<br>
     * result = id | (cubeHeight -1)
     */
    private int childOf(int id) {
        int result = -1;
        if (cubeHeight > 0) {
            result = id | (1 << (cubeHeight - 1));
            if (result == id || !exists(result)) {
                result = -1;
            }
        }
        return result;
    }

    /**
     * Returns the location of the most significant one bit in the given id.
     * 
     * @param id the id we are going to examine.
     * 
     * @pre id &ge; 0
     * @post id.binaryRepresentation[result] = 1 AND &forall; result &le; i &lt; 32 (id.binaryRepresentation[i] = 0)
     */
    public int locationOfHighestOneBit(int id) {
        assert id >= 0;

        int result = -1;
        int tempId = id;
        while (tempId > 0) {
            result++;
            tempId >>>= 1;
        }
        return result;
    }

    /**
     * Determines whether the node is a node in the hyPeerWeb.
     * 
     * @param node the node we wish to examine.
     * 
     * @pre <i>None</i>
     * @post result = 0 &le; node &lt; hyPeerWebSize
     */
    private boolean exists(int node) {
        return node >= 0 && node < hyPeerWebSize;
    }

    /**
     * The log of 2.
     */
    private double log2 = Math.log(2.0d);
}
