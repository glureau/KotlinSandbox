package com.glureau.kotlin.sandbox.adapter_when;

import com.glureau.kotlin.sandbox.adapter_when.java.MouseAdapter;
import com.glureau.kotlin.sandbox.adapter_when.java.MouseEvent;
import com.glureau.kotlin.sandbox.adapter_when.java.MouseListener;
import com.glureau.kotlin.sandbox.adapter_when.java.MouseMonkey;

/**
 * Created by Greg on 05/12/2015.
 * Inspired by https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/events/MouseEventDemoProject/src/events/MouseEventDemo.java
 */
public class JavaAdapterDemo {

    public static void main(String[] args) throws InterruptedException {
        MouseListener listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Released");
            }
        };

        MouseMonkey monkey = new MouseMonkey(listener);
        Thread.sleep(1000);
        monkey.stop();
    }
}
