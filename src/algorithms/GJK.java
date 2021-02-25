package algorithms;

/** 
 * Implementation of the Gilbert Johnson Keerthi algorithm descirbed here:
 * https://caseymuratori.com/blog_0003
 * 
 * I altered the algorithm to be integer based and to exclude non-penatrating (surface contact) collisions.
 * @author merlin
 */
public class GJK {
	
	private static final Vector3i START_QUERY = Vector3i.UNITY;
	
	/**
	 * Given two objects, returns true iff their geometries overlap. Coordinates are in World space.
	 */
	public static boolean testIntersection(GJKVertexFinder objectA, GJKVertexFinder objectB) {
		Vector3i newVertex, ao, ab, ac, ad, abcNormal, acdNormal, adbNormal, temp;
		
		int simplexSize = 1;
		Vector3i[] simplex = {support(objectA, objectB, START_QUERY), null, null, null};
		Vector3i direction = simplex[0].inverse();
		while (true) {
			if (simplex[simplexSize - 1].equals(Vector3i.ZEROS)) {
				return false;
			}
			newVertex = support(objectA, objectB, direction);
			if (newVertex.dot(direction) <= 0) {
				return false;
			}
			simplex[simplexSize] = newVertex;
			simplexSize++;
			
			switch (simplexSize) {
			
			case 1:
				// Simplex is a point
				direction = simplex[0].inverse();
				break;
				
			case 2:
				// Simplex is a line
				ao = simplex[1].inverse();
				ab = simplex[0].sub(simplex[1]);
				direction = ab.cross(ao).cross(ab);
				if (direction.equals(Vector3i.ZEROS)) {
					direction = ab.cross(ab.equals(Vector3i.X_ONE) ? Vector3i.Y_ONE : Vector3i.X_ONE);
				}
				break;
				
			case 4:
				// Simplex is a tetrahedron
				ao = simplex[3].inverse();
				ab = simplex[2].sub(simplex[3]);
				ac = simplex[1].sub(simplex[3]);
				ad = simplex[0].sub(simplex[3]);
				abcNormal = ab.cross(ac);
				acdNormal = ac.cross(ad);
				adbNormal = ad.cross(ab);
				
				if (abcNormal.dot(ao) > 0) {
					simplexSize = 3;
					simplex[0] = simplex[1];
					simplex[1] = simplex[2];
					simplex[2] = simplex[3];
					simplex[3] = null;
					direction = abcNormal;
				} else if (acdNormal.dot(ao) > 0) {
					simplexSize = 3;
					simplex[2] = simplex[3];
					simplex[3] = null;
					direction = abcNormal;
				} else if (adbNormal.dot(ao) > 0) {
					simplexSize = 3;
					simplex[1] = simplex[0];
					simplex[0] = simplex[2];
					simplex[2] = simplex[3];
					simplex[3] = null;
					direction = abcNormal;
				} else {
					
					// Point is somewhere in 4-simplex, now check if on edge of minkowski sum

					Vector3i constraint = null;
					Vector3i edgePoint = null;
					Vector3i bo = simplex[2].inverse();
					Vector3i dcbNormal = simplex[0].sub(simplex[2]).cross(simplex[1].sub(simplex[2])); // bd.cross(bc)

					int faces = (abcNormal.dot(ao) == 0 ? 1 : 0) |
							    (acdNormal.dot(ao) == 0 ? 2 : 0) |
							    (adbNormal.dot(ao) == 0 ? 4 : 0) |
							    (dcbNormal.dot(bo) == 0 ? 8 : 0);
					
					switch (faces) {
					case 0: // no faces (inside)
						return true;
					case 1: // face abc
						direction = abcNormal;
						break;
					case 2: // face acd
						direction = acdNormal;
						break;
					case 3: // face abc and acd (edge ac)
						direction = abcNormal;
						constraint = acdNormal;
						edgePoint = simplex[3];
						break;
					case 4: // face adb
						direction = adbNormal;
						break;
					case 5: // face abc and adb (edge ba)
						direction = abcNormal;
						constraint = adbNormal;
						edgePoint = simplex[2];
						break;
					case 6: // face acd and adb (edge ad)
						direction = acdNormal;
						constraint = adbNormal;
						edgePoint = simplex[3];
						break;
					case 7: // three faces (point)
						return false;
					case 8: // face dcb
						direction = dcbNormal;
						break;
					case 9: // face abc and dcb (edge cb)
						direction = abcNormal;
						constraint = dcbNormal;
						edgePoint = simplex[1];
						break;
					case 10: // face acd and dcb (edge dc)
						direction = acdNormal;
						constraint = dcbNormal;
						edgePoint = simplex[0];
						break;
					case 11: // three faces (point)
						return false;
					case 12: // face adb and dcb (edge bd)
						direction = adbNormal;
						constraint = dcbNormal;
						edgePoint = simplex[2];
						break;
					case 13: // three faces (point)
						return false;
					case 14: // three faces (point)
						return false;
					case 15: // four faces (wtf)
						throw new RuntimeException("All faces in 4-simplex contained the origin. How did this happen?");
					}
					
					if (constraint == null) {
						return support(objectA, objectB, direction).dot(direction) != 0;
					}

					while (true) {
						newVertex = support(objectA, objectB, direction);
						if (newVertex.dot(direction) == 0) {
							return false;
						}
						if (newVertex.dot(constraint) > 0) {
							return true;
						}
						direction = edgePoint.cross(newVertex);
					}
					
				}
				// break purposely omitted
				
			default: // (a.k.a. case 3)
				// Simplex is a triangle
				ao = simplex[2].inverse();
				ab = simplex[1].sub(simplex[2]);
				ac = simplex[0].sub(simplex[2]);
				abcNormal = ab.cross(ac);
				
				if (abcNormal.cross(ac).dot(ao) > 0) {
					if (ac.dot(ao) > 0) {
						simplexSize = 2;
						simplex[1] = simplex[2];
						simplex[2] = null;
						direction = ac.cross(ao).cross(ac);
					} else {
						if (ab.dot(ao) > 0) {
							simplexSize = 2;
							simplex[0] = simplex[1];
							simplex[1] = simplex[2];
							simplex[2] = null;
							direction = ab.cross(ao).cross(ab);
						} else {
							simplexSize = 1;
							simplex[0] = simplex[2];
							simplex[1] = null;
							simplex[2] = null;
							direction = ao;
						}
					}
				} else {
					if (ab.cross(abcNormal).dot(ao) > 0) {
						if (ab.dot(ao) > 0) {
							simplexSize = 2;
							simplex[0] = simplex[1];
							simplex[1] = simplex[2];
							simplex[2] = null;
							direction = ab.cross(ao).cross(ab);
						} else {
							simplexSize = 1;
							simplex[0] = simplex[2];
							simplex[1] = null;
							simplex[2] = null;
							direction = ao;
						}
					} else {
						if (abcNormal.dot(ao) >= 0) {
							direction = abcNormal;
						} else {
							temp = simplex[0];
							simplex[0] = simplex[1];
							simplex[1] = temp;
							direction = abcNormal.inverse();
						}
					}
				}
				break;
				
			}
		}
	}
	
	private static Vector3i support(GJKVertexFinder objectA, GJKVertexFinder objectB, Vector3i direction) {
		return objectA.furthestVertex(direction).sub(objectB.furthestVertex(direction.inverse()));
	}

}
