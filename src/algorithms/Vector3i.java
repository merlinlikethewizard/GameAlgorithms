package algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple 3 integer element vector with some supporting functions
 * @author merlin
 */
public class Vector3i implements Comparable<Vector3i> {

	public final int x;
	public final int y;
	public final int z;

	public static Vector3i ZEROS = new Vector3i(0, 0, 0);
	public static Vector3i X_ONE = new Vector3i(1, 0, 0);
	public static Vector3i Y_ONE = new Vector3i(0, 1, 0);
	public static Vector3i Z_ONE = new Vector3i(0, 0, 1);
	public static Vector3i UNITY = new Vector3i(1, 1, 1);
	
	public static List<Vector3i> inflate(int[] flattened) {
		List<Vector3i> vectors = new ArrayList<Vector3i>();
		for (int i = 0; i < flattened.length / 3; i++) {
			vectors.add(new Vector3i(
					flattened[i*3    ],
					flattened[i*3 + 1],
					flattened[i*3 + 2]
					));
		}
		return vectors;
	}
		
	public static int[] flatten(List<Vector3i> vectors) {
		int[] flattened = new int[vectors.size() * 3];
		for (int i = 0; i < vectors.size(); i++) {
			flattened[i*3    ] = vectors.get(i).x;
			flattened[i*3 + 1] = vectors.get(i).y;
			flattened[i*3 + 2] = vectors.get(i).z;
		}
		return flattened;
	}
	
	public Vector3i() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean equals(Object object) {
        if (object instanceof Vector3i) {
        	Vector3i that = (Vector3i) object;
            return this.x == that.x && this.y == that.y && this.z == that.z;
        }
        return false;
    }
	
	@Override
	public String toString() {
		return String.format("Vector3i( %d, %d, %d )", x, y, z);
	}
	
	public Vector3i add(Vector3i that) {
		return new Vector3i(
				this.x + that.x,
				this.y + that.y,
				this.z + that.z
		);
	}
	
	public Vector3i sub(Vector3i that) {
		return new Vector3i(
				this.x - that.x,
				this.y - that.y,
				this.z - that.z
		);
	}
	
	public Vector3i scale(int value) {
		return new Vector3i(
				this.x * value,
				this.y * value,
				this.z * value
		);
	}
	
	public Vector3i inverse() {
		return new Vector3i(-x, -y, -z);
	}
	
	public int dot(Vector3i that) {
		return (this.x * that.x) + (this.y * that.y) + (this.z * that.z);
	}
	
	public Vector3i cross(Vector3i that) {
		return new Vector3i(
				(this.y * that.z) - (this.z * that.y),
				(this.z * that.x) - (this.x * that.z),
				(this.x * that.y) - (this.y * that.x)
		);
	}
	
	public Vector3i positive() {
		return new Vector3i(
				(x > 0 ? x : 0),
				(y > 0 ? y : 0),
				(z > 0 ? z : 0)
				);
	}
	
	public Vector3i negative() {
		return new Vector3i(
				(x < 0 ? x : 0),
				(y < 0 ? y : 0),
				(z < 0 ? z : 0)
				);
	}
	
	public Vector3i abs() {
		return new Vector3i(
				Math.abs(x),
				Math.abs(y),
				Math.abs(z)
				);
	}

	@Override
	public int compareTo(Vector3i that) {
		int cmp;
		cmp = Integer.compare(this.z, that.z);
		if (cmp != 0) {
			return cmp;
		}
		cmp = Integer.compare(this.y, that.y);
		if (cmp != 0) {
			return cmp;
		}
		cmp = Integer.compare(this.x, that.x);
		if (cmp != 0) {
			return cmp;
		}
		return 0;
	}

}