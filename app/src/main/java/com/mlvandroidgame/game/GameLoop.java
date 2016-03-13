package com.mlvandroidgame.game;

import android.graphics.Canvas;

/**
 * Created by Didier BERTILLE.
 */
public class GameLoop extends Thread {

    //nombre d'images par secondes
    private final static int FRAMES_BY_SECOND = 30;
    private final static int Ticks = 1000/FRAMES_BY_SECOND;


    private final GameView view;
    // état du thread
    private boolean runnnig = false;

    //Constructeur
    public GameLoop (GameView view){
        this.view = view;
    }

    //defini l'etat du thread
    public void setRun(Boolean run){
        runnnig = run;
    }

    //demarrage du thread
    public void run(){

        //temps de départ et de pause
        long sleepTime;
        long startTime;

        while (runnnig){

            //heure actuel
            startTime=System.currentTimeMillis();
            //MAJ des objets dans GameView
            synchronized (view.getHolder()){view.update();}
            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.doDraw(c);
                }
            }finally {
                if(c!=null){
                    view.getHolder().unlockCanvasAndPost(c);
                    sleepTime= Ticks-(System.currentTimeMillis()-startTime);
                    try {
                        if (sleepTime>=0){
                            sleep(sleepTime);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

}
