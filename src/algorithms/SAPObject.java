package algorithms;

/**
 * A class which an object must implement in order to be used in the Sweep-And-Prune
 * @author merlin
 */
public class SAPObject {
	
	public SAPEndpoint endpointNX;
	public SAPEndpoint endpointNY;
	public SAPEndpoint endpointNZ;
	public SAPEndpoint endpointPX;
	public SAPEndpoint endpointPY;
	public SAPEndpoint endpointPZ;
	
	public boolean boundsOverlap(SAPObject that) {
		return (
				getWorldNX() < that.getWorldPX() && that.getWorldNX() < getWorldPX() &&
				getWorldNY() < that.getWorldPY() && that.getWorldNY() < getWorldPY() &&
				getWorldNZ() < that.getWorldPZ() && that.getWorldNZ() < getWorldPZ()
				);
	}
	
	public boolean boundsOverlapYZ(SAPObject that) {
		return (
				getWorldNY() < that.getWorldPY() && that.getWorldNY() < getWorldPY() &&
				getWorldNZ() < that.getWorldPZ() && that.getWorldNZ() < getWorldPZ()
				);
	}
	
	public int getWorldNX() {return endpointNX.value;}
	public int getWorldNY() {return endpointNY.value;}
	public int getWorldNZ() {return endpointNZ.value;}
	public int getWorldPX() {return endpointPX.value;}
	public int getWorldPY() {return endpointPY.value;}
	public int getWorldPZ() {return endpointPZ.value;}
	
	public void genEndpoints() {
		endpointNX = new SAPEndpoint(this, false);
		endpointNY = new SAPEndpoint(this, false);
		endpointNZ = new SAPEndpoint(this, false);
		endpointPX = new SAPEndpoint(this, true);
		endpointPY = new SAPEndpoint(this, true);
		endpointPZ = new SAPEndpoint(this, true);
	}
	
}