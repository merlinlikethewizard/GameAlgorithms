package algorithms;

/**
 * A single endpoint of the Sweep-And-Prune algorithm.
 * This is designed to contain an integer value, but
 * could be altered to use floats.
 * @author merlin
 */
public class SAPEndpoint {

	public final SAPObject parent;
	public final boolean isMax;
	public SAPEndpoint prev = null;
	public SAPEndpoint next = null;
	public int value;

	public SAPEndpoint(SAPObject parent, boolean isMax) {
		this.parent = parent;
		this.isMax = isMax;
	}

}
