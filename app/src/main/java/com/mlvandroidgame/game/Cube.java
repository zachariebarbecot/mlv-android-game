package com.mlvandroidgame.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;


import com.mlvandroidgame.R;

import java.util.Random;

/**
 * Created by Didier BERTILLE.
 */
public class Cube {
    //image du cube
    private BitmapDrawable img =null ;
    // coordonnees du cube
    private int x;
    private int y;
    private Random rand = new Random();

    private DisplayMetrics ecran = new DisplayMetrics();
    //taille du cube
    private int cubeLargeur;
    private int cubeHauteur;

    //taille de la balle
    private int largeurEcran;
    private int hauteurEcran;



    private final Context mContext;

    public Cube(final Context c){
        mContext = c;
        x = rand.nextInt(tailleEcranLargeur() - cubeLargeur);
        y = rand.nextInt(tailleEcranHauteur() - cubeHauteur);

    }

    public int tailleEcranLargeur(){
        DisplayMetrics ecran = new DisplayMetrics();
        WindowManager windowManagerW = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManagerW.getDefaultDisplay().getMetrics(ecran);
        this.largeurEcran = ecran.widthPixels;
        return largeurEcran;
    }

    public int tailleEcranHauteur(){
        DisplayMetrics ecran = new DisplayMetrics();
        WindowManager windowManagerH = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManagerH.getDefaultDisplay().getMetrics(ecran);
        this.hauteurEcran = ecran.heightPixels;
        return hauteurEcran;
    }


    //attribution de l'obtjet cube
    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h){
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(),Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    public void resize() {
        this.cubeLargeur = tailleEcranLargeur()/15;
        this.cubeHauteur = tailleEcranLargeur()/15;
        img = setImage(mContext, R.drawable.cube, cubeLargeur, cubeHauteur);

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

    public int getCubeLargeur() {
        return cubeLargeur;
    }

    public int getCubeHauteur() {
        return cubeHauteur;
    }

    public int getLargeurEcran() {
        return largeurEcran;
    }

    public int getHauteurEcran() {
        return hauteurEcran;
    }

    //dessine la cube
    public void draw (Canvas canvas){
        if (img == null){return;}
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }


    public BitmapDrawable getImg() {
        return img;
    }

    public Random getRand() {
        return rand;
    }

    public DisplayMetrics getEcran() {
        return ecran;
    }

    public void setCubeLargeur(int cubeLargeur) {
        this.cubeLargeur = cubeLargeur;
    }

    public void setCubeHauteur(int cubeHauteur) {
        this.cubeHauteur = cubeHauteur;
    }

    public void setLargeurEcran(int largeurEcran) {
        this.largeurEcran = largeurEcran;
    }

    public void setHauteurEcran(int hauteurEcran) {
        this.hauteurEcran = hauteurEcran;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setImg(BitmapDrawable img) {
        this.img = img;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public void setEcran(DisplayMetrics ecran) {
        this.ecran = ecran;
    }

}
