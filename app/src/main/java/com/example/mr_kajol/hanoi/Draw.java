package com.example.mr_kajol.hanoi;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

public class Draw extends View {

	private Stack<DiskShape> leftRod, middleRod, rightRod;
	private Stack<DiskShape> rodWithDiskSelected = null;
	Context context;
	
	int no_of_disks, moves = 0;
	float x, y;
	float xRatio, yRatio;
	boolean isValidTouch = true;
	float bottomLimit, topLimit, leftLimitMiddleRod, rightLimitMiddleRod;

	@SuppressWarnings("deprecation")
	public Draw(Context context, float width, float height, int _no_of_disks) {
		super(context);
		
		this.context = context;
		
		setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
				R.drawable.hanoi_background)));

		leftRod = new Stack<DiskShape>();
		middleRod = new Stack<DiskShape>();
		rightRod = new Stack<DiskShape>();

		xRatio = width / 480;
		yRatio = height / 320;
		no_of_disks = _no_of_disks;

		bottomLimit = 20 * yRatio;
		topLimit = 250 * yRatio;
		leftLimitMiddleRod = 165 * xRatio;
		rightLimitMiddleRod = 315 * xRatio;
		
		for (int i = _no_of_disks; i >= 1; i--) {
			leftRod.push(new DiskShape(i, xRatio, yRatio));
		}
	}

	public void onDraw(Canvas canvas) {

		// left rod should be at (90, 226) on 480*320 screen
		canvas.translate(xRatio * 90, yRatio * 226);
		canvas.save();
		drawDisks(canvas, leftRod);
		canvas.restore();
		
		// middle rod will be at (240, 226)
		canvas.translate(150 * xRatio, 0);
		canvas.save();
		drawDisks(canvas, middleRod);
		canvas.restore();
		
		// right rod will be at (390, 226)
		canvas.translate(150 * xRatio, 0);
		canvas.save();
		drawDisks(canvas, rightRod);
		canvas.restore();
	}

	private void drawDisks(Canvas canvas, Stack<DiskShape> rod) {
		for (DiskShape disk : rod) {
			disk.draw(canvas);
			canvas.translate(0, -25 * yRatio);
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			x = event.getX();
			y = event.getY();

			if (y > bottomLimit && y < topLimit) {
				isValidTouch = true;
				if (x < leftLimitMiddleRod)
					rodWithDiskSelected = leftRod;
				else if (x >= leftLimitMiddleRod && x <= rightLimitMiddleRod)
					rodWithDiskSelected = middleRod;
				else
					rodWithDiskSelected = rightRod;

				if (rodWithDiskSelected.size() != 0)
					rodWithDiskSelected.lastElement().select();

			} else
				isValidTouch = false;

			invalidate();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (isValidTouch == true) {
				if (rodWithDiskSelected.size() != 0) {

					int mX = (int) (90 * xRatio);
					int mY = (int) (250 * yRatio);

					if (rodWithDiskSelected == middleRod) {
						// mX=240;
						mX += 150 * xRatio;

					} else if (rodWithDiskSelected == rightRod) {
						// mX=390;
						mX += 300 * xRatio;
						// mY=191;
					}
					int mm = (int) (rodWithDiskSelected.size() * 25 * yRatio);

					// int x = (int) event.getX()-mX;
					x = event.getX() - mX;
					y = event.getY() - mY + mm;

					rodWithDiskSelected.lastElement().setBound();
					rodWithDiskSelected.lastElement().getBounds()
							.inset((int) x, (int) y);
					invalidate();
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (isValidTouch == true)
				if (rodWithDiskSelected.size() != 0) {
					x = event.getX();
					y = event.getY();
					rodWithDiskSelected.lastElement().setBound();
					if (y > bottomLimit && y < topLimit) {
						rodWithDiskSelected.lastElement().getBounds()
								.inset((int) x, (int) y);
						if (x < leftLimitMiddleRod) {
							actionOnTouch(leftRod);
						} else if (x >= leftLimitMiddleRod
								&& x <= rightLimitMiddleRod) {
							actionOnTouch(middleRod);
						} else
							actionOnTouch(rightRod);
					} else
						rodWithDiskSelected.lastElement().unSelect();
					invalidate();
				}

		}
		return true;
	}

	private void actionOnTouch(Stack<DiskShape> touchedRod) {
		rodWithDiskSelected.lastElement().unSelect();
		rodWithDiskSelected.lastElement().setBound();
		
		if (isValidMove(touchedRod)) {
			touchedRod.push(rodWithDiskSelected.pop());
			moves++;
		}
		rodWithDiskSelected = null;
		invalidate();

		if (rightRod.size() == no_of_disks || middleRod.size() == no_of_disks) {
			((Play) getContext()).gameOver(moves);
		}
	}

	private boolean isValidMove(Stack<DiskShape> touchedRod) {
		return touchedRod.size() == 0
				|| rodWithDiskSelected.lastElement().size < touchedRod
						.lastElement().size;
	}
}