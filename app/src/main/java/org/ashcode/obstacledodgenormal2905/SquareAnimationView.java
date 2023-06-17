package org.ashcode.obstacledodgenormal2905;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SquareAnimationView extends SurfaceView implements SurfaceHolder.Callback {

    private SquareThread squareThread;
    private Square[] squares;

    public SquareAnimationView(Context context) {
        super(context);
        getHolder().addCallback(this);
        squareThread = new SquareThread(getHolder(), this);
        setFocusable(true);
        squares = new Square[5]; // Number of squares to be displayed
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square(0, (i + 1) * 100); // Starting position of each square
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        squareThread.setRunning(true);
        squareThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        squareThread.setRunning(false);
        while (retry) {
            try {
                squareThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        for (Square square : squares) {
            canvas.drawRect(square.getX(), square.getY(), square.getX() + square.getSize(), square.getY() + square.getSize(), paint);
        }
    }

    public void resume() {
        squareThread.setRunning(true);
    }

    public void pause() {
        squareThread.setRunning(false);
    }

    private void updateSquares() {
        for (Square square : squares) {
            square.update();
        }
    }

    private class SquareThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private SquareAnimationView squareAnimationView;
        private boolean isRunning;

        public SquareThread(SurfaceHolder holder, SquareAnimationView view) {
            surfaceHolder = holder;
            squareAnimationView = view;
            isRunning = false;
        }

        public void setRunning(boolean running) {
            isRunning = running;
        }

        @Override
        public void run() {
            while (isRunning) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        squareAnimationView.updateSquares();
                        squareAnimationView.draw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    private class Square {
        private int x;
        private int y;
        private int size;
        private int speed;

        public Square(int x, int y) {
            this.x = x;
            this.y = y;
            size = 100; // Size of each square
            speed = 5; // Speed of each square
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getSize() {
            return size;
        }

        public void update() {
            x += speed;
            if (x > getWidth()) {
                x = -size; // Reset the square position to the left
            }}}}















































































