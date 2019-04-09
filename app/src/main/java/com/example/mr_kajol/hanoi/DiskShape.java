package com.example.mr_kajol.hanoi;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

public class DiskShape extends ShapeDrawable {
	
	int size;
	float yRatio;
	private static float[] diskOuterRadius = new float[] { 25, 25, 25, 25, 0, 0, 0, 0 };

	public DiskShape(int _size, float xRatio, float yRatio) {
		super(new RoundRectShape(diskOuterRadius, null, null));
	
		this.yRatio = yRatio;
		this.size = (int) (_size * 24 * xRatio);
		this.unSelect();
		
		setBound();
	}

	public void setBound() {
		this.setBounds(0, 0, this.size, (int) (24 * yRatio));
	}

	public void select() {
		this.getPaint().setColor(0x88FF8844);
	}

	public void unSelect() {
		this.getPaint().setColor(0xFFFF8844);
	}

	protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
		canvas.save();

		// the disk on the center of the rod
		canvas.translate(-size / 2, 0);
		shape.draw(canvas, paint);

		canvas.restore();
	}
}