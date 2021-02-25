package algorithms;

import java.util.Set;

/**
 * An Octree implementation. Supports adding, deleting, and querying.
 * @author merlin
 */
public class Octree {
	
	public static int LEAF_SIZE = 4;
	public static int MAX_DEPTH_READING = 24;

	private Octant[] octants;
	private int[] depths;
	
	public Octree() {
		octants = new Octant[8];
		depths = new int[8];
	}
	
	public void addObject(Object object, Vector3i boundsN, Vector3i boundsP) {
		int minX = Math.floorDiv(boundsN.x, LEAF_SIZE) * LEAF_SIZE;
		int minY = Math.floorDiv(boundsN.y, LEAF_SIZE) * LEAF_SIZE;
		int minZ = Math.floorDiv(boundsN.z, LEAF_SIZE) * LEAF_SIZE;
		int maxX = Math.floorDiv(boundsP.x, LEAF_SIZE) * LEAF_SIZE;
		int maxY = Math.floorDiv(boundsP.y, LEAF_SIZE) * LEAF_SIZE;
		int maxZ = Math.floorDiv(boundsP.z, LEAF_SIZE) * LEAF_SIZE;
		for (int x = minX; x < maxX; x += LEAF_SIZE) {
			for (int y = minY; y < maxY; y += LEAF_SIZE) {
				for (int z = minZ; z < maxZ; z += LEAF_SIZE) {
					addObject(object, x, y, z);
				}
			}
		}
	}
	
	public void addObject(Object object, int x, int y, int z) {
		int selector = (x < 0 ? 0 : 1) | (y < 0 ? 0 : 2) | (z < 0 ? 0 : 4);
		
		if (octants[selector] == null) {
			octants[selector] = new Octant();
		}
		
		int maxDist = Math.max(
				x < 0 ? -1-x : x, Math.max(
				y < 0 ? -1-y : y,
				z < 0 ? -1-z : z)
				);
		while (maxDist >= LEAF_SIZE << depths[selector]) {
			Octant newOctant = new Octant();
			newOctant.setOctant(selector ^ 7, octants[selector]);
			octants[selector] = newOctant;
			depths[selector]++;
		}
		
		int offset = LEAF_SIZE << (depths[selector] - 1);
		octants[selector].addObject(
				object,
				x + (x < 0 ? offset : -offset),
				y + (y < 0 ? offset : -offset),
				z + (z < 0 ? offset : -offset),
				depths[selector]
				);
	}
	
	public void delObject(Object object, Vector3i boundsN, Vector3i boundsP) {
		int minX = Math.floorDiv(boundsN.x, LEAF_SIZE) * LEAF_SIZE;
		int minY = Math.floorDiv(boundsN.y, LEAF_SIZE) * LEAF_SIZE;
		int minZ = Math.floorDiv(boundsN.z, LEAF_SIZE) * LEAF_SIZE;
		int maxX = Math.floorDiv(boundsP.x, LEAF_SIZE) * LEAF_SIZE;
		int maxY = Math.floorDiv(boundsP.y, LEAF_SIZE) * LEAF_SIZE;
		int maxZ = Math.floorDiv(boundsP.z, LEAF_SIZE) * LEAF_SIZE;
		for (int x = minX; x < maxX; x += LEAF_SIZE) {
			for (int y = minY; y < maxY; y += LEAF_SIZE) {
				for (int z = minZ; z < maxZ; z += LEAF_SIZE) {
					delObject(object, x, y, z);
				}
			}
		}
	}
	
	public void delObject(Object object, int x, int y, int z) {
		int selector = (x < 0 ? 0 : 1) | (y < 0 ? 0 : 2) | (z < 0 ? 0 : 4);
		
		int offset = LEAF_SIZE << (depths[selector] - 1);
		if (octants[selector].delObject(
				object,
				x + (x < 0 ? offset : -offset),
				y + (y < 0 ? offset : -offset),
				z + (z < 0 ? offset : -offset),
				depths[selector]
				)) {
			octants[selector] = null;
			return;
		}

		int counterSelector = selector ^ 7;
		while (depths[selector] > 0) {
			for (int i = 0; i < 8; i++) {
				if (i != counterSelector && octants[selector].octants[i] != null) {
					return;
				}
			}
			octants[selector] = octants[selector].octants[counterSelector];
			depths[selector]--;
		}
	}
	
	public void query(Set<Object> objectsOutput, Vector3i boundsN, Vector3i boundsP) {
		int minX = Math.floorDiv(boundsN.x, LEAF_SIZE) * LEAF_SIZE;
		int minY = Math.floorDiv(boundsN.y, LEAF_SIZE) * LEAF_SIZE;
		int minZ = Math.floorDiv(boundsN.z, LEAF_SIZE) * LEAF_SIZE;
		int maxX = Math.floorDiv(boundsP.x, LEAF_SIZE) * LEAF_SIZE;
		int maxY = Math.floorDiv(boundsP.y, LEAF_SIZE) * LEAF_SIZE;
		int maxZ = Math.floorDiv(boundsP.z, LEAF_SIZE) * LEAF_SIZE;
		for (int x = minX; x < maxX; x += LEAF_SIZE) {
			for (int y = minY; y < maxY; y += LEAF_SIZE) {
				for (int z = minZ; z < maxZ; z += LEAF_SIZE) {
					query(objectsOutput, x, y, z);
				}
			}
		}
	}
	
	public int query(Set<Object> objectsOutput, int x, int y, int z) {
		int selector = (x < 0 ? 0 : 1) | (y < 0 ? 0 : 2) | (z < 0 ? 0 : 4);
		
		if (octants[selector] == null) {
			return MAX_DEPTH_READING;
		}

		int maxDist = Math.max(
				x < 0 ? -1-x : x, Math.max(
				y < 0 ? -1-y : y,
				z < 0 ? -1-z : z)
				);
		if (maxDist >= LEAF_SIZE << depths[selector]) {
			return depths[selector];
		}
		
		int offset = LEAF_SIZE << (depths[selector] - 1);
		return octants[selector].query(
				objectsOutput,
				x + (x < 0 ? offset : -offset),
				y + (y < 0 ? offset : -offset),
				z + (z < 0 ? offset : -offset),
				depths[selector]
				);
	}

}
