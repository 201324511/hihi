package com.tobusan.selfidrone.view;

import android.os.CountDownTimer;

import com.tobusan.selfidrone.drone.BebopDrone;
import com.tobusan.selfidrone.drone.Beeper;

public class WideShot{
    private final static String CLASS_NAME = WideShot.class.getSimpleName();

    private Thread WideThread = null;

    private BebopDrone bebopDrone = null;

    private Beeper beep = null;
    private Beeper beepFinish = null;
    private CountDownTimer mCountDown = null;

    public void resume(final BebopDrone bebopDrone, final Beeper beep, final Beeper beepFinish) {
        this.bebopDrone = bebopDrone;
        this.beep = beep;
        this.beepFinish = beepFinish;

        WideThread = new CascadingThread();
        WideThread.start();
    }

    public void pause() {
        WideThread.interrupt();
        try {
            WideThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void takePicture(){
        beepFinish.play();
        bebopDrone.takePicture();
    }
    private void timerStart(int inputTime){
        if(inputTime != 0){
            mCountDown = new CountDownTimer((inputTime+1) * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    beep.play();
                }
                @Override
                public void onFinish() {
                    takePicture();
                }
            }.start();
        }
    }
    private class CascadingThread extends Thread {

        private void stop_shot() {
            bebopDrone.setPitch((byte)0);
            bebopDrone.setFlag((byte)0);
            bebopDrone.setGaz((byte)0);
        }

        private void start_shot() {
            bebopDrone.setPitch((byte)-10);
            bebopDrone.setFlag((byte)1);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                bebopDrone.setPitch((byte)0);
                bebopDrone.setFlag((byte)0);
            }
            bebopDrone.setPitch((byte)0);
            bebopDrone.setFlag((byte)0);

            bebopDrone.setGaz((byte)30);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                bebopDrone.setPitch((byte)0);
                bebopDrone.setFlag((byte)0);
                bebopDrone.setGaz((byte)0);
            }
            bebopDrone.setGaz((byte)0);


            try {
                sleep(5000);
            } catch (InterruptedException e) {
                bebopDrone.setGaz((byte)0);
            }
            timerStart(5);


            bebopDrone.setGaz((byte)-30);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                bebopDrone.setGaz((byte)0);
            }
            bebopDrone.setGaz((byte)0);

            bebopDrone.setPitch((byte)10);
            bebopDrone.setFlag((byte)1);
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                bebopDrone.setPitch((byte)0);
                bebopDrone.setFlag((byte)0);
            }
            bebopDrone.setPitch((byte)0);
            bebopDrone.setFlag((byte)0);
        }

        @Override
        public void interrupt() {
            stop_shot();
        }
        @Override
        public void run() {
            start_shot();
        }
    }
}