package algorithms;

/**
 * The meat of the Sweep-And-Prune algorithm. Each SAP instance
 * should initialize three of these, one for each axis. The
 * entryTrigger() and exitTrigger() functions will be called when
 * a single endpoint is passed (in any dimension). A call to
 * boundsOverlap() should then pe performed on the parent objects.
 * @author merlin
 */
public abstract class SAPAxis {
	
	public SAPEndpoint n = null;
	public SAPEndpoint p = null;
	
	public void addEndpoint(SAPEndpoint endpoint) {
		if (n != null) {
			n.prev = endpoint;
			endpoint.next = n;
		} else {
			p = endpoint;
		}
		n = endpoint;
		updateEndpoint(n);
	}
	
	public void updateEndpoint(SAPEndpoint endpoint) {
		while (endpoint.prev != null && compareEndpoints(endpoint.prev, endpoint)) {}
		while (endpoint.next != null && compareEndpoints(endpoint, endpoint.next)) {}
	}
	
	public void delEndpoint(SAPEndpoint endpoint) {
		if (endpoint.next != null) {
			endpoint.next.prev = endpoint.prev;
		} else {
			p = endpoint.prev;
		}
		if (endpoint.prev != null) {
			endpoint.prev.next = endpoint.next;
		} else {
			n = endpoint.next;
		}
		endpoint.prev = null;
		endpoint.next = null;
	}
	
	private boolean compareEndpoints(SAPEndpoint endpointN, SAPEndpoint endpointP) {
		if (endpointN.value > endpointP.value || (endpointN.value == endpointP.value && !endpointN.isMax && endpointP.isMax)) {
			endpointN.next = endpointP.next;
			endpointP.prev = endpointN.prev;
			endpointN.prev = endpointP;
			endpointP.next = endpointN;
			if (endpointN.next != null) {
				endpointN.next.prev = endpointN;
			} else {
				p = endpointN;
			}
			if (endpointP.prev != null) {
				endpointP.prev.next = endpointP;
			} else {
				n = endpointP;
			}
			
			if (endpointN.parent != endpointP.parent) {
				if (endpointN.isMax && !endpointP.isMax) {
					entryTrigger(endpointN, endpointP);
				} else if (!endpointN.isMax && endpointP.isMax) {
					exitTrigger(endpointN, endpointP);
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	protected abstract void entryTrigger(SAPEndpoint endpointN, SAPEndpoint endpointP);
	
	protected abstract void exitTrigger(SAPEndpoint endpointN, SAPEndpoint endpointP);

}
