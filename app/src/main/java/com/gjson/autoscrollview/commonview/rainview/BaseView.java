package com.gjson.autoscrollview.commonview.rainview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseView extends View {

	private Thread thread;

	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public BaseView(Context context) {
		super(context);

	}

	protected abstract void drawSub(Canvas canvas);

	protected abstract void logic();

	protected abstract void init();

	@Override
	protected final void onDraw(Canvas canvas) {

		if (thread == null) {
			thread = new MyThread();
			thread.start();
		} else {
			drawSub(canvas);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		running = false;
		super.onDetachedFromWindow();
	}

	private boolean running = true;

	class MyThread extends Thread {

		@Override
		public void run() {
			init();
			while (running) {
				logic();
				postInvalidate();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
