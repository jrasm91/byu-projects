

package cs235.rsg;

public class Factory {

    public static RSG createRSG() {
    	return new RSGImpl();
    }
}
