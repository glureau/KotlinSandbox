package com.glureau.kotlin.sandbox.adapter_when.java;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Random inputs
 */
public class MouseMonkey {

    private final Timer timer;

    public MouseMonkey(final MouseListener listener) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int rand = new Random().nextInt(5);
                switch (rand) {
                    case 0:
                        listener.mouseClicked(new MouseEvent());
                        break;
                    case 1:
                        listener.mouseEntered(new MouseEvent());
                        break;
                    case 2:
                        listener.mouseExited(new MouseEvent());
                        break;
                    case 3:
                        listener.mousePressed(new MouseEvent());
                        break;
                    case 4:
                        listener.mouseReleased(new MouseEvent());
                        break;
                }
            }
        }, 0, 100);
    }

    public void stop() {
        timer.cancel();
    }
}
