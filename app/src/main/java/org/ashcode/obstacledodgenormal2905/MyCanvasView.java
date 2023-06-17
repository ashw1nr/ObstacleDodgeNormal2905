package org.ashcode.obstacledodgenormal2905;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyCanvasView extends View {

    //For 1 moving rectangle
/*    private Paint rectanglePaint;
    private int rectangleX;
    private int rectangleY;
    private int rectangleVelocity;
    private int rectangleWidth ;
    private int rectangleHeight ;*/

    private int currScore;
    private MovingRectangle Rectangle1;
    private MovingRectangle Rectangle2;
    private MovingRectangle nxtApprRectangleChaserChaser;
    private MovingRectangle nxtApprRectangleChaserBall;
    private int canvasWidth;
    private int canvasHeight;

    //For ball
    private Paint ballPaint;
    private float ballX;
    private float ballY;
    private float ballRadius;
    private boolean ballIsJumping;
    private float ballJumpHeight;
    private float ballJumpVelocity;
    private float gravity;

    private float ballInitialJumpVelocity ;

    private Paint chaserPaint;
    private float chaserX;
    private float chaserY;
    private float chaserRadius;
    private boolean chaserIsJumping;
    private float chaserJumpHeight;
    private float chaserJumpVelocity;

    private float chaserInitialJumpVelocity ;
    private float chaserJumpRange;


    private int gameCollisions;



    public MyCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        
        gameCollisions = 0;
        currScore = 0;

        Paint rectangle1Paint = new Paint();
        rectangle1Paint.setColor(Color.BLUE);

        Rectangle1 = new MovingRectangle();
        Rectangle1.setRectangleProps(rectangle1Paint,20,300,100);

        Paint rectangle2Paint = new Paint();
        rectangle2Paint.setColor(Color.MAGENTA);

        Rectangle2 = new MovingRectangle();
        Rectangle2.setRectangleProps(rectangle1Paint,20,150,100);

        nxtApprRectangleChaserChaser = Rectangle2; //Assign it to Rectangle1 after rectifying the error of Chaser jumping as soon as game starts
        nxtApprRectangleChaserBall = Rectangle1;

        // Initial position of the rectangle set under onSizeChanged
        //rectangleX =  0;
        //rectangleY = 0;

        ballPaint = new Paint();
        ballPaint.setColor(Color.RED);
        ballRadius = 50;
        gravity = 2.0f;

        ballIsJumping = false;
        ballJumpHeight = 0;
        ballJumpVelocity = 0;

        ballInitialJumpVelocity =-40;

        chaserPaint = new Paint();
        chaserPaint.setColor(Color.GREEN);
        chaserRadius = 80;

        chaserIsJumping = false;
        chaserJumpHeight = 0;
        chaserJumpVelocity = 0;

        chaserInitialJumpVelocity =-40;

        chaserJumpRange = java.lang.Math.abs(Rectangle1.getRectangleVelocity()*2*chaserInitialJumpVelocity/gravity);

        // Start the animation loop
        startAnimationLoop();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasWidth = w;
        canvasHeight = h;
        Rectangle1.setRectangleY(canvasHeight-Rectangle1.getRectangleHeight());
        Rectangle1.setRectangleX(canvasWidth);
        Rectangle2.setRectangleY(canvasHeight-Rectangle2.getRectangleHeight());
        Rectangle2.setRectangleX(Rectangle1.getRectangleX()+800);
        ballX = ballRadius+500;
        ballY = getHeight() - ballRadius;
        chaserX = chaserRadius+200;
        chaserY = getHeight() - chaserRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Clear the canvas
        canvas.drawColor(Color.WHITE);

        // Draw the rectangle
        canvas.drawRect(Rectangle1.getRectangleX(), Rectangle1.getRectangleY(), Rectangle1.getRectangleX() + Rectangle1.getRectangleWidth(), Rectangle1.getRectangleY() + Rectangle1.getRectangleHeight(), Rectangle1.getRectanglePaint());
        canvas.drawRect(Rectangle2.getRectangleX(), Rectangle2.getRectangleY(), Rectangle2.getRectangleX() + Rectangle2.getRectangleWidth(), Rectangle2.getRectangleY() + Rectangle2.getRectangleHeight(), Rectangle2.getRectanglePaint());
        canvas.drawCircle(ballX, ballY, ballRadius, ballPaint);
        canvas.drawCircle(chaserX, chaserY, chaserRadius, chaserPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!(ballIsJumping)){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Start the jump
            ballIsJumping = true;
            ballJumpVelocity = ballInitialJumpVelocity;  // Adjust the initial jump velocity as desired
        }}
        return true;
    }

    private void updateRectanglePosition() {
        currScore+=(2-gameCollisions)*10;
        // Move the rectangle
        Rectangle1.setRectangleX(Rectangle1.getRectangleX() -Rectangle1.getRectangleVelocity());
        Rectangle2.setRectangleX(Rectangle2.getRectangleX() -Rectangle2.getRectangleVelocity());
        //rectangleY += 0;

        // Wrap the rectangle around the canvas
        if (Rectangle1.getRectangleX() <0 ) {
            Rectangle1.setRectangleX(canvasWidth- Rectangle1.getRectangleWidth());
        }

        if (Rectangle2.getRectangleX() <0 ) {
            Rectangle2.setRectangleX(canvasWidth- Rectangle2.getRectangleWidth());
        }

        if (chaserIsJumping){
            chaserJumpHeight += chaserJumpVelocity;
            chaserJumpVelocity += gravity;

            if (chaserJumpHeight >= 0) {
                // Reached the ground, stop jumping
                chaserJumpHeight = 0;
                chaserJumpVelocity = 0;
                chaserIsJumping = false;
            }

            // Update the ball's position with the jump height
            chaserY = getHeight() -chaserRadius + chaserJumpHeight;
        }
        if (!(chaserIsJumping) && ((nxtApprRectangleChaserChaser.getRectangleX()-chaserX)<chaserJumpRange/2)){
            chaserIsJumping = true;
            chaserJumpVelocity = chaserInitialJumpVelocity;
            chaserJumpHeight += chaserJumpVelocity;
            chaserJumpVelocity += gravity;
            chaserY = getHeight() -chaserRadius + chaserJumpHeight;
            if (nxtApprRectangleChaserChaser == Rectangle1){
                nxtApprRectangleChaserChaser = Rectangle2;
            }
            else if (nxtApprRectangleChaserChaser == Rectangle2){
                nxtApprRectangleChaserChaser = Rectangle1;
            }

        }

        if (ballIsJumping) {
            ballJumpHeight += ballJumpVelocity;
            ballJumpVelocity += gravity;

            if (ballJumpHeight >= 0) {
                // Reached the ground, stop jumping
                ballJumpHeight = 0;
                ballJumpVelocity = 0;
                ballIsJumping = false;
            }

            // Update the ball's position with the jump height
            ballY = getHeight() - ballRadius + ballJumpHeight;
        }


        MainActivity.setCurrScoreTVMA1Text(String.valueOf(currScore));
        if (!(checkForCollision())) {
            // Redraw the canvas
            invalidate();
        }
    }

    private boolean checkForCollision() {
        if (ballX+ballRadius < nxtApprRectangleChaserBall.getRectangleX())
            return false;
        else if ((ballX+ballRadius >= nxtApprRectangleChaserBall.getRectangleX()) && (ballX-ballRadius <= nxtApprRectangleChaserBall.getRectangleX()+nxtApprRectangleChaserBall.getRectangleWidth())){
            if (ballY-ballRadius>nxtApprRectangleChaserBall.getRectangleY()) //Why is (ballY+ballRadius>rectangleY) stating GameCollision before it starts?
            {
            Toast.makeText(getContext().getApplicationContext(), "Collided once",Toast. LENGTH_SHORT).show();

            if (nxtApprRectangleChaserBall == Rectangle1){
                gameCollisions += 2;
                nxtApprRectangleChaserBall = Rectangle2;
            }
            else if (nxtApprRectangleChaserBall == Rectangle2){
                gameCollisions += 1;
                chaserX += (nxtApprRectangleChaserBall.getRectangleX()-chaserX)/2;
                nxtApprRectangleChaserBall = Rectangle1;
            }

            return true;}
            else return false;
        }
        else {
            if (nxtApprRectangleChaserBall == Rectangle1){
                nxtApprRectangleChaserBall = Rectangle2;
            }
            else if (nxtApprRectangleChaserBall == Rectangle2){
                nxtApprRectangleChaserBall = Rectangle1;}
            return false;
        }
        }

    private void startAnimationLoop() {
        final android.os.Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (gameCollisions<2){
                updateRectanglePosition();
                handler.postDelayed(this, 30); // 30 milliseconds interval for animation speed
            }}
        };
        handler.post(runnable);
    }
}