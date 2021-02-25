package algorithms;

/**
 * Helper class for the GJK implementation.
 * @author merlin
 */
public interface GJKVertexFinder {
	
	public Vector3i furthestVertex(Vector3i direction);
	
	/**
	 * Returns the furtest point along the given vector.
	 * All coordinates are in World space.
	 */
	public static Vector3i furthestVertex(Vector3i direction, Vector3i offset, Vector3i sweep, Vector3i[] vertices) {

		Vector3i maxVertex = null;
		int maxDot = Integer.MIN_VALUE;
		int dot;

		if (sweep != null) {
			for (Vector3i vertex: vertices) {
				dot = direction.dot(vertex);
				if (dot > maxDot) {
					maxDot = dot;
					maxVertex = vertex;
				}
				vertex = vertex.add(sweep);
				dot = direction.dot(vertex);
				if (dot > maxDot) {
					maxDot = dot;
					maxVertex = vertex;
				}
			}
		} else {
			for (Vector3i vertex: vertices) {
				dot = direction.dot(vertex);
				if (dot > maxDot) {
					maxDot = dot;
					maxVertex = vertex;
				}
			}
		}

		return maxVertex.add(offset);
	}

}
