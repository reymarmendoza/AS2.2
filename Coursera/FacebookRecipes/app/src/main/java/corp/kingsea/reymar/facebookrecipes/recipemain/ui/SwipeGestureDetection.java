package corp.kingsea.reymar.facebookrecipes.recipemain.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * se va a controlar los gestos de swipe
 **/
public class SwipeGestureDetection extends GestureDetector.SimpleOnGestureListener{

    private static final int SWIPE_THRESHOLD = 100;//DISTANCIA de arrastre
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;//VELOCIDAD

    private SwipeGestureListener listener;

    public SwipeGestureDetection(SwipeGestureListener listener) {
        this.listener = listener;
    }

    @Override//cuando mueve hacia abajo
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();
        if(Math.abs(diffX) > Math.abs(diffY) && Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
            if(diffX > 0){
                listener.onKeep();//hubo un swipe a la derecha
            }else{
                listener.onDismiss();//a la izq
            }
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
