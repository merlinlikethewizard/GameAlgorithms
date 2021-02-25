package algorithms;

/**
 * A Sweep-And-Prune implementation. Most of the code is in
 * the SAPAxis class. The SAPEndpoint class is set up to use
 * integer values, but could be altered.
 * @author merlin
 */
public abstract class SAP {
	
	public SAPAxis axisX;
	public SAPAxis axisY;
	public SAPAxis axisZ;
	
	public void addObject(SAPObject object) {
		axisX.addEndpoint(object.endpointNX);
		axisY.addEndpoint(object.endpointNY);
		axisZ.addEndpoint(object.endpointNZ);
		axisX.addEndpoint(object.endpointPX);
		axisY.addEndpoint(object.endpointPY);
		axisZ.addEndpoint(object.endpointPZ);
	}
	
	public void updateObject(SAPObject object) {
		axisX.updateEndpoint(object.endpointNX);
		axisY.updateEndpoint(object.endpointNY);
		axisZ.updateEndpoint(object.endpointNZ);
		axisX.updateEndpoint(object.endpointPX);
		axisY.updateEndpoint(object.endpointPY);
		axisZ.updateEndpoint(object.endpointPZ);
	}
	
	public void delObject(SAPObject object) {
		axisX.delEndpoint(object.endpointNX);
		axisY.delEndpoint(object.endpointNY);
		axisZ.delEndpoint(object.endpointNZ);
		axisX.delEndpoint(object.endpointPX);
		axisY.delEndpoint(object.endpointPY);
		axisZ.delEndpoint(object.endpointPZ);
	}

}
