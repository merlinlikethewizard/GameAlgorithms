package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Recursivly structured class for the octree implementation.
 * @author merlin
 */
public class Octant {
	
	public List<Object> objects;
	public Octant[] octants;
	
	public void addObject(Object object, int x, int y, int z, int depth) {
		if (depth == 0) {
			if (objects == null) {
				objects = new ArrayList<Object>();
			}
			objects.add(object);
			return;
		}
		
		int selector = (x < 0 ? 0 : 1) | (y < 0 ? 0 : 2) | (z < 0 ? 0 : 4);

		if (octants == null) {
			octants = new Octant[8];
		}
		if (octants[selector] == null) {
			octants[selector] = new Octant();
		}

		int offset = Octree.LEAF_SIZE << (depth - 2);
		octants[selector].addObject(
				object,
				x + (x < 0 ? offset : -offset),
				y + (y < 0 ? offset : -offset),
				z + (z < 0 ? offset : -offset),
				depth - 1
				);
	}
	
	public boolean delObject(Object object, int x, int y, int z, int depth) {
		if (depth == 0) {
			objects.remove(object);
			return objects.isEmpty();
		}
		
		int selector = (x < 0 ? 0 : 1) | (y < 0 ? 0 : 2) | (z < 0 ? 0 : 4);

		int offset = Octree.LEAF_SIZE << (depth - 2);
		if (octants[selector].delObject(
				object,
				x + (x < 0 ? offset : -offset),
				y + (y < 0 ? offset : -offset),
				z + (z < 0 ? offset : -offset),
				depth - 1
				)) {
			octants[selector] = null;
			for (Octant octant: octants) {
				if (octant != null) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public int query(Set<Object> objectsOutput, int x, int y, int z, int depth) {
		if (depth == 0) {
			if (objects != null) {
				objectsOutput.addAll(objects);
			}
			return 0;
		}
		
		int selector = (x < 0 ? 0 : 1) | (y < 0 ? 0 : 2) | (z < 0 ? 0 : 4);
		
		if (octants == null) {
			return depth;
		}
		if (octants[selector] == null) {
			return depth - 1;
		}

		int offset = Octree.LEAF_SIZE << (depth - 2);
		return octants[selector].query(
				objectsOutput,
				x + (x < 0 ? offset : -offset),
				y + (y < 0 ? offset : -offset),
				z + (z < 0 ? offset : -offset),
				depth - 1
				);
	}

	
	public void setOctant(int selector, Octant octant) {
		if (octants == null) {
			octants = new Octant[8];
		}
		octants[selector] = octant;
	}

}
