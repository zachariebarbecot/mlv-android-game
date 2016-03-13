package com.mlvandroidgame.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.mlvandroidgame.R;



/**
 * Created by Didier BERTILLE.
 */
public class Ball {

    //image de la balle
    private BitmapDrawable img = null ;

    // coordonnees de la balle
    private int x;
    private int y;

    //taille de la balle
    private int balleLargeur;
    private int balleHauteur;

    //taille de la balle
    private int largeurEcran;
    private int hauteurEcran;

    public int tailleEcranLargeur(){
        DisplayMetrics ecran = new DisplayMetrics();
        WindowManager windowManagerW = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManagerW.getDefaultDisplay().getMetrics(ecran);
        this.largeurEcran= ecran.widthPixels;
        return largeurEcran;
    }

    public int tailleEcranHauteur(){
        DisplayMetrics ecran = new DisplayMetrics();
        WindowManager windowManagerH = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManagerH.getDefaultDisplay().getMetrics(ecran);
        this.hauteurEcran = ecran.heightPixels;
        return hauteurEcran;
    }



    //Vitesse balle
    private int increment = 5;
    private int speedX = increment;
    private int speedY = increment;

    // egale true si la balle se deplace
    private boolean move = true;
    private final Context mContext;



    public Ball(final Context c){
        x = 0;
        y = 0;
        mContext = c;
    }

    //attribution de l'obtjet balle
    public BitmapDrawable setImage(final Context c,final int resource, final int w, final int h){
        Drawable dr = c.getResources().getDrawable(resource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    public boolean isMoving(){
        return move;
    }

    public void setMove(boolean move){
        this.move = move;
    }

    public void resize(){
        balleLargeur =  tailleEcranLargeur()/15;
        balleHauteur= tailleEcranLargeur()/15;
        img  = setImage(mContext, R.drawable.ball, balleLargeur, balleHauteur);

    }

    // definir la coordonnée X de la balle
    public void setX (int x){
        this.x = x;
    }
    // definir la coordonnée Y de la balle
    public void setY(int y){
        this.y = y;
    }

    // retourne la coordonnée X de la balle
    public int getX(){
        return x;
    }
    // retourne la coordonnée Y de la balle
    public int getY() {
        return y;
    }

//dessine la balle
    public void draw (Canvas canvas){
        if (img == null){return;}
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }

    public BitmapDrawable getImg() {
        return img;
    }

    public int getBalleLargeur() {
        return balleLargeur;
    }

    public int getBalleHauteur() {
        return balleHauteur;
    }

    public int getLargeurEcran() {
        return largeurEcran;
    }

    public int getHauteurEcran() {
        return hauteurEcran;
    }

    public int getIncrement() {
        return increment;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setImg(BitmapDrawable img) {
        this.img = img;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void setBalleLargeur(int balleLargeur) {
        this.balleLargeur = balleLargeur;
    }

    public void setBalleHauteur(int balleHauteur) {
        this.balleHauteur = balleHauteur;
    }

    public void setLargeurEcran(int largeurEcran) {
        this.largeurEcran = largeurEcran;
    }

    public void setHauteurEcran(int hauteurEcran) {
        this.hauteurEcran = hauteurEcran;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }
}
