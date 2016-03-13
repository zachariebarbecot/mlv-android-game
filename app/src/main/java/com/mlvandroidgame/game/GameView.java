package com.mlvandroidgame.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.mlvandroidgame.SplashActivity;
import com.mlvandroidgame.databases.Score;
import com.mlvandroidgame.databases.ScoreDataSource;
import com.mlvandroidgame.utils.ScoreSingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Didier BERTILLE.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoop gameThread;
    private Ball ball;
    private List<Cube> list;
    private int score = 1;
    private boolean touched;
    private Context context;
    private ScoreDataSource scoreDataSource;
    private Boolean isDone;


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameLoop(this);
        this.ball = new Ball(context);
        this.list = Collections.synchronizedList(new ArrayList<Cube>());
        this.context = context;
        this.scoreDataSource = new ScoreDataSource(context);
        this.isDone = false;
    }

    public void createCube(){
        this.list.add(new Cube(context));

    }

    public void doDraw(Canvas canvas){
        if(canvas == null) {
            return;
        }
        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);
        // on dessine la balle et un cube
        this.ball.draw(canvas);
        synchronized (list) {
            for (Cube a : list) {
                a.resize();
                a.draw(canvas);
            }
        }
    }

    // Gerer le deplacement de la balle
    public void update(){
        detecteCollision();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if(gameThread.getState()==Thread.State.TERMINATED){
            this.gameThread=new GameLoop(this);
        }
        this.gameThread.setRun(true);
        this.gameThread.start();
    }

    @Override
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.ball.resize();
    }

    @Override
    // Fonction appelée juste avant que l'objet ne soit détruit.
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry=true;
        this.gameThread.setRun(false);
        while (retry){
            try {
                this.gameThread.join();
                retry = false;
            } catch (InterruptedException e){

            }
        }
    }

    public  void detecteCollision(){
        if (!ball.isMoving() && !isDone) {
            this.isDone = true;
            ScoreSingleton.getINSTANCE().setScore(new Score(new Date(), score - 1));
            scoreDataSource.addScore(ScoreSingleton.getINSTANCE().getScore());
            Intent intent = new Intent(context, SplashActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
            return;
        } else {
            int Xballe = ball.getX() + ball.getSpeedX();
            this.ball.setX(Xballe);

            int Yballe = ball.getY() + ball.getSpeedY();
            this.ball.setY(Yballe);

            // si x dépasse la largeur de l'écran
            if (ball.getX() + ball.getBalleLargeur() > ball.tailleEcranLargeur()) {
                int largeurX = ball.getSpeedX() - ball.getIncrement();
                this.ball.setSpeedX(largeurX);
            }
            // si x dépasse la hauteur de l'écran
            if (ball.getY() + ball.getBalleHauteur() > ball.tailleEcranHauteur()) {
                int auteurY = ball.getSpeedY() - ball.getIncrement();
                this.ball.setSpeedY(auteurY);
            }
            // si x passe à gauche ou a droite de l'écran
            if (ball.getX() < 0) {
                int auxSpeedX = ball.getIncrement();
                this.ball.setSpeedX(auxSpeedX);
            }
            // si y passe à dessus ou dessous de l'écran
            if (ball.getY() < 0) {
                int auxSpeedY = ball.getIncrement();
                this.ball.setSpeedY(auxSpeedY);
            }

            for (Cube a : list) {
                if (ball.getY() <= a.getY() + a.getCubeHauteur() &&
                        this.ball.getY() + ball.getBalleHauteur() >= a.getY() &&
                        this.ball.getX() + ball.getBalleLargeur() >= a.getX() &&
                        this.ball.getX() <= a.getX() + a.getCubeLargeur()) {
                    this.ball.setMove(false);
                }
            }
        }
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public boolean onTouchEvent (MotionEvent event){
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();
        ArrayList toRemove = new ArrayList();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                synchronized (list) {
                    for (Cube a : list) {
                        if (currentY <= a.getY() + a.getCubeHauteur() &&
                                currentY >= a.getY() &&
                                currentX <= a.getX() + a.getLargeurEcran() &&
                                currentX >= a.getX()
                                ) {
                            this.score = score + 1;
                            if ((score % 5) == 0) {
                                int x = ball.getIncrement() + 10;
                                ball.setIncrement(x);
                            }
                            toRemove.add(a);
                            Toast toast = Toast.makeText(context, "Score: " + (score - 1), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    list.removeAll(toRemove);
                }
                break;
        }
        return true;
    }

    public int getScore() {
        return score;
    }

    public GameLoop getGameThread() {
        return gameThread;
    }

    public Ball getBall() {
        return ball;
    }


    public List<Cube> getList() {
        return list;
    }

    public void setGameThread(GameLoop gameThread) {
        this.gameThread = gameThread;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setList(ArrayList<Cube> list) {
        this.list = list;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
