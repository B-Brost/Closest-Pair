import java.awt.Point
import java.util.ArrayList;
import java.util.List;
"""
	Closest_Pair.java
	Brianna Brost
	2/28/2024
	This Java code is a solution to the closest pair of points problem, using a divide-and-conquer algorithm that splits the problem into smaller subproblems, solves them, and combines the results. It also includes a brute-force method for comparison when the number of points is small.
	""";
public class closest_pair {
	public static List<Point> closestPairRecursive(List<Point> P, List<Point> X, List<Point> Y){
		if (P.size() <= 3) {
			return closestPairBrute(P);
		}

		int mid = X.size() / 2;

		List<Point> XL = X.subList(0, mid);
		List<Point> XR = X.subList(mid, X.size());

		List<Point> YL = new ArrayList<>();
		List<Point> YR = new ArrayList<>();
		List<Point> PL = new ArrayList<>();
		List<Point> PR = new ArrayList<>();

		for (Point point : Y) {
			if (XL.contains(point)) {
				YL.add(point);
			} else {
				YR.add(point);
			}
		}

		for (Point point : P) {
			if (XL.contains(point)) {
				PL.add(point);
			} else {
				PR.add(point);
			}
		}
		//recursive calls
		List<Point> delta_L = closestPairRecursive(PL, XL, YL);
		List<Point> delta_R = closestPairRecursive(PR, XR, YR);

		double delta = Math.min(distance(delta_L.get(0), delta_L.get(1)), 
				distance(delta_R.get(0), delta_R.get(1)));

		// Merge step with y-sorted values
		List<Point> strip = new ArrayList<>();
		double midX = XR.get(0).x;
		for (Point point : Y) {
			if (Math.abs(midX - point.x) < delta) {
				strip.add(point);
			}
		}
		
		List<Point> closestPairStrip = closestPairBrute(strip);

//		// Compare points within the strip
//		List<Point> closestPair = mergeClosest(strip, delta);
		
		List<Point> closestPair = new ArrayList<>();

		
		
		// Compare the closest pairs from left and right halves with the strip
		if (distance(closestPairStrip.get(0), closestPairStrip.get(1)) < delta) {
			delta = distance(closestPairStrip.get(0), closestPairStrip.get(1));
		}

		if (distance(delta_L.get(0), delta_L.get(1)) < delta) {
			delta = distance(delta_L.get(0), delta_L.get(1));
			closestPair = delta_L;
		}
		if (distance(delta_R.get(0), delta_R.get(1)) < delta) {
			delta = distance(delta_R.get(0), delta_R.get(1));
			closestPair = delta_R;
		}

		return closestPair;
	}

//	public static List<Point> mergeClosest(List<Point> strip, double delta) {
//		double minDelta = Double.POSITIVE_INFINITY;
//		List<Point> closestPairStrip = new ArrayList<>(2);
//
//		for (int i = 0; i < strip.size(); i++) {
//			for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < delta; j++) {
//				double dist = distance(strip.get(i), strip.get(j));
//				if (dist < minDelta) {
//					closestPairStrip.clear();
//					closestPairStrip.add(strip.get(i));
//					closestPairStrip.add(strip.get(j));
//					minDelta = dist;
//				}
//			}
//		}
//		return closestPairStrip;
//	}

	public static List<Point> closestPairBrute(List<Point> A){

		List<Point> closestPair = new ArrayList<>(2);
		double minDistance = Double.POSITIVE_INFINITY;

		for (int i = 0; i < A.size(); i++) {
			for (int j = i + 1; j < A.size(); j ++) {
				double distance = distance(A.get(i), A.get(j));

				if (distance < minDistance) {
					minDistance = distance;
					closestPair.clear();
					closestPair.add(A.get(i));
					closestPair.add(A.get(j));
				}
			}
		}
		return closestPair;
	}

	public static double distance(Point a, Point b){
		double xDistance = Math.pow((a.x - b.x), 2);
		double yDistance = Math.pow((a.y - b.y), 2);

		return Math.sqrt(xDistance + yDistance);
	}
}








