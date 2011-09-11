/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.androsz.electricsleepbeta.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.androsz.electricsleepbeta.achartengine.util.MathHelper;
import com.androsz.electricsleepbeta.util.PointD;

/**
 * An XY series encapsulates values for XY charts like line, time, area,
 * scatter... charts.
 */
public class XYSeries implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -224488951294506127L;
	/** The maximum value for the X axis. */
	private double mMaxX = -MathHelper.NULL_VALUE;
	/** The maximum value for the Y axis. */
	private double mMaxY = -MathHelper.NULL_VALUE;
	/** The minimum value for the X axis. */
	private double mMinX = MathHelper.NULL_VALUE;
	/** The minimum value for the Y axis. */
	private double mMinY = MathHelper.NULL_VALUE;
	/** The series title. */
	private String mTitle;
	/** A list to contain the values for the X axis. */
	public List<PointD> xyList = new ArrayList<PointD>();

	/**
	 * Builds a new XY series.
	 * 
	 * @param title
	 *            the series title.
	 */
	public XYSeries(final String title) {
		mTitle = title;
		initRange();
	}

	/**
	 * Adds a new value to the series.
	 * 
	 * @param x
	 *            the value for the X axis
	 * @param y
	 *            the value for the Y axis
	 */
	public synchronized void add(final double x, final double y) {
		xyList.add(new PointD(x, y));
		updateRange(x, y);
	}

	/**
	 * Removes all the existing values from the series.
	 */
	public synchronized void clear() {
		xyList.clear();
		initRange();
	}

	/**
	 * Returns the series item count.
	 * 
	 * @return the series item count
	 */
	public synchronized int getItemCount() {
		return xyList.size();
	}

	/**
	 * Returns the maximum value on the X axis.
	 * 
	 * @return the X axis maximum value
	 */
	public double getMaxX() {
		return mMaxX;
	}

	/**
	 * Returns the maximum value on the Y axis.
	 * 
	 * @return the Y axis maximum value
	 */
	public double getMaxY() {
		return mMaxY;
	}

	/**
	 * Returns the minimum value on the X axis.
	 * 
	 * @return the X axis minimum value
	 */
	public double getMinX() {
		return mMinX;
	}

	/**
	 * Returns the minimum value on the Y axis.
	 * 
	 * @return the Y axis minimum value
	 */
	public double getMinY() {
		return mMinY;
	}

	/**
	 * Returns the series title.
	 * 
	 * @return the series title
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * Returns the X axis value at the specified index.
	 * 
	 * @param index
	 *            the index
	 * @return the X value
	 */
	public synchronized double getX(final int index) {
		return xyList.get(index).x;
	}

	/**
	 * Returns the Y axis value at the specified index.
	 * 
	 * @param index
	 *            the index
	 * @return the Y value
	 */
	public synchronized double getY(final int index) {
		return xyList.get(index).y;
	}

	/**
	 * Initializes the range for both axes.
	 */
	public void initRange() {
		mMinX = MathHelper.NULL_VALUE;
		mMaxX = -MathHelper.NULL_VALUE;
		mMinY = MathHelper.NULL_VALUE;
		mMaxY = -MathHelper.NULL_VALUE;
		final int length = getItemCount();
		for (int k = 0; k < length; k++) {
			final double x = getX(k);
			final double y = getY(k);
			updateRange(x, y);
		}
	}

	/**
	 * Removes an existing value from the series.
	 * 
	 * @param index
	 *            the index in the series of the value to remove
	 */
	public synchronized void remove(final int index) {
		final PointD removedPoint = xyList.remove(index);
		if (removedPoint.x == mMinX || removedPoint.x == mMaxX
				|| removedPoint.y == mMinY || removedPoint.y == mMaxY) {
			initRange();
		}
	}

	public void setMaxX(final double maxX) {
		mMaxX = maxX;
	}

	public void setMaxY(final double maxY) {
		mMaxY = maxY;
	}

	public void setMinX(final double minX) {
		mMinX = minX;
	}

	public void setMinY(final double minY) {
		mMinY = minY;
	}

	/**
	 * Sets the series title.
	 * 
	 * @param title
	 *            the series title
	 */
	public void setTitle(final String title) {
		mTitle = title;
	}

	/**
	 * Updates the range on both axes.
	 * 
	 * @param x
	 *            the new x value
	 * @param y
	 *            the new y value
	 */
	private void updateRange(final double x, final double y) {
		mMinX = Math.min(mMinX, x);
		mMaxX = Math.max(mMaxX, x);
		mMinY = Math.min(mMinY, y);
		mMaxY = Math.max(mMaxY, y);
	}
}
