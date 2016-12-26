package weighingproblem;

import java.util.*;

import theory.Ensemble;

public class Scales {
	
	public Integer heavy = new Integer(2);
	public Integer light = new Integer(1);
	
	
	public List<Integer> leftscale = new ArrayList<Integer>();
	public List<Integer> rightscale = new ArrayList<Integer>();
	public List<Integer> table = new ArrayList<Integer>();
	
	/**
	 * 
	 * balls start out on the table, you can them move them around to the left or right scale,
	 * by using the leftscale and rightscale fields
	 * one thats done, call weigh, and it will return and enum that gives the result
	 * 
	 */
	public Scales() {
		for (int i = 1; i <= 12; i++){table.add(heavy);}
		int i = new Random().nextInt(11);
		table.set(i, light);
	}	
	
	public Scales(int totalBalls) {
		for (int i = 1; i <= totalBalls; i++){table.add(heavy);}
		int i = new Random().nextInt(totalBalls-1);
		table.set(i, light);
	}	

	public int totalBalls() {
		return leftscale.size() + rightscale.size() + table.size();
	}
	
	private int scaleWeight(List<Integer> scaleSide) {
		int weight = 0;
		for (Integer ball : scaleSide) {
			weight += ball;
		}
		return weight;
	}
	
	public WeighResult weigh()   {
		int leftscaleWeight = scaleWeight(this.leftscale);
		int rightscaleWeight = scaleWeight(this.rightscale);
		if (leftscaleWeight == rightscaleWeight) {return WeighResult.EQUAL;}
		if (leftscaleWeight < rightscaleWeight) {return WeighResult.LEFT_LIGHT;}
		else {return WeighResult.RIGHT_LIGHT;}
	}
		
}
