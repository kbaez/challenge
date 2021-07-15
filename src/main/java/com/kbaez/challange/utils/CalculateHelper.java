package com.kbaez.challange.utils;

import com.kbaez.challange.exception.PositionNotDeterminedException;
import com.kbaez.challange.model.Vector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculateHelper {


	  /**
	   * Determine the position of a point using the mathematical method of trilateration to determine the relative <b/>
	   * positions of objects using triangle geometry analogous to triangulation.
	   *
	   * <p>
	   * The system of equations is composed of the equality given by the equation of the distance between two points.
	   * given the points:
	   * <b/>
	   * P1 = (a,b)<b/>
	   * P2 = (c,d)<b/>
	   * P3 = (e,f)<b/>
	   * and the point to find (x,y)<b/>
	   * <b/>
	   * (x - a)^2 + (y - b)^2 = r1^2
	   * (x - a)^2 + (y - b)^2 = r2^2
	   * (x - a)^2 + (y - b)^2 = r3^2
	   * </p>
	   * <p>
	   * if the ordered pair satisfies the entire system of equations, the point is determined,
	   * otherwise null is returned.
	   * </p>
	   *
	   * @param vector1        satellite position1
	   * @param vector2        satellite position2
	   * @param vector3        satellite position3
	   * @param distances      distance between the satellites and the point to find,
	   *                       each position must correspond to the points of the satellites
	   * @param toleranceError error deviation factor in mathematical calculation
	   * @return the vector composed of the ordered pair found, otherwise null
	   */
	  public static Vector determinePosition(final Vector vector1, final Vector vector2, final Vector vector3,
	                                         final float[] distances, final double toleranceError)
	      throws PositionNotDeterminedException {
	    log.debug("vector1: {} - vector2: {} - vector3: {} - distances: {}", vector1, vector2, vector3, distances);
	    // substitutions used in the mathematical model
	    final double sustitutionK = (Math.pow(distances[0], 2) - Math.pow(vector1.getPositionx(), 2)
	        - Math.pow(vector1.getPositiony(), 2) + Math.pow(vector2.getPositionx(), 2)
	        + Math.pow(vector2.getPositiony(), 2) - Math.pow(distances[1], 2))
	        / (2 * (vector2.getPositionx() - vector1.getPositionx()));

	    final double sustitutionI = (vector1.getPositiony() - vector2.getPositiony())
	        / (vector2.getPositionx() - vector1.getPositionx());

	    log.debug("sustitutionK: {} - sustitutionI: {}", sustitutionK, sustitutionI);

	    // the values ​​are determined to calculate the quadratic equation
	    final double a = 1 + sustitutionI * sustitutionI;
	    final double b =
	        2 * (sustitutionI * sustitutionK - vector1.getPositionx() * sustitutionI - vector1.getPositiony());
	    final double c = -1 * (Math.pow(distances[0], 2) - Math.pow(vector1.getPositionx(), 2)
	        - Math.pow(vector1.getPositiony(), 2) - Math.pow(sustitutionK, 2)
	        + 2 * vector1.getPositionx() * sustitutionK);
	    final double[] roots = quadratic(a, b, c);

	    // Once the roots of y have been found, we calculate the roots of x
	    // and it is verified that the system of equations satisfies
	    for (double rootY : roots) {
	      final double rootX = rootY * sustitutionI + sustitutionK;
	      if (checkSystemEquations(vector1, vector2, vector3, distances, rootX, rootY, toleranceError)) {
	        log.debug("root founded: x:{}", rootX);
	        return Vector.builder().positionx(roundOneDecimals(rootX)).positiony(roundOneDecimals(rootY)).build();
	      }
	    }
	    throw new PositionNotDeterminedException(ConsUtil.POSITION_NOT_DETERMINED);
	  }

	  /**
	   * implementation of the quadratic equation.
	   *
	   * @param valueA value valueA
	   * @param valueB value valueB
	   * @param valueC value valueC
	   * @return 1 or two roots according to the determinant of the equation
	   * @throws PositionNotDeterminedException if the determinant is negative, the roots could not be determined
	   */
	  public static double[] quadratic(final double valueA, final double valueB, final double valueC)
	      throws PositionNotDeterminedException {
	    log.debug("valueA={} - valueB={} - valueC={}", valueA, valueB, valueC);
	    double discriminate = Math.pow(valueB, 2) - 4 * valueA * valueC;
	    double y1;
	    double y2;
	    double[] roots;
	    if (discriminate == 0) {
	      y1 = -valueB / (2 * valueA);
	      roots = new double[]{y1};
	      log.debug("root founded: y1 = {}", y1);
	    } else if (discriminate > 0) {
	      y1 = (-valueB + Math.sqrt(discriminate)) / (2 * valueA);
	      y2 = (-valueB - Math.sqrt(discriminate)) / (2 * valueA);
	      roots = new double[]{y1, y2};
	      log.debug("root founded: y1 = {} and y2 = {}", y1, y2);
	    } else {
	      log.warn("negative discriminate: {}", discriminate);
	      throw new PositionNotDeterminedException(ConsUtil.POSITION_NOT_DETERMINED);
	    }
	    return roots;
	  }

	  /**
	   * check the system of equations.
	   *
	   * @param vector1        satellite position1
	   * @param vector2        satellite position2
	   * @param vector3        satellite position3
	   * @param distances      distance between the satellites and the point to find,
	   *                       each position must correspond to the points of the satellites
	   * @param rootX          root of x found
	   * @param rootY          root of y found
	   * @param toleranceError error deviation factor in mathematical calculation
	   * @return true if it satisfies the system of equations, otherwise false
	   */
	  public static boolean checkSystemEquations(final Vector vector1, final Vector vector2, final Vector vector3,
	                                             final float[] distances, final double rootX, final double rootY,
	                                             final double toleranceError) {
	    return checkEqualities(vector1, distances[0], rootX, rootY, toleranceError)
	        && checkEqualities(vector2, distances[1], rootX, rootY, toleranceError)
	        && checkEqualities(vector3, distances[2], rootX, rootY, toleranceError);
	  }

	  /**
	   * check the equality of an equation.
	   *
	   * @param vector         point on the plane
	   * @param distance       distance between the given vector and the ordered pair composed of x and y
	   * @param rootX          root of x found
	   * @param rootY          root of y found
	   * @param toleranceError error deviation factor in mathematical calculation
	   * @return true if it satisfies equality, otherwise false
	   */
	  public static boolean checkEqualities(final Vector vector, final float distance, final double rootX,
	                                        final double rootY, final double toleranceError) {
	    log.debug("P1: ({},{}) - P2: ({}, {}) = {}", vector.getPositionx(), vector.getPositiony(),
	        rootX, rootY, distance);
	    final double leftCalculate = Math.sqrt(Math.pow(rootX - vector.getPositionx(), 2)
	        + Math.pow(rootY - vector.getPositiony(), 2));
	    log.debug("equation {} - {} = {}", leftCalculate, distance, Math.abs(leftCalculate - distance));
	    return Math.abs(leftCalculate - distance) < toleranceError;
	  }

	  /**
	   * round to two decimal places.
	   *
	   * @param value value
	   * @return round to two decimal places
	   */
	  public static double roundOneDecimals(final double value) {
	    return (double) Math.round(value * 10) / 10;
	  }

}
