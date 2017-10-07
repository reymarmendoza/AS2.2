package corp.kingsea.reymar.practiceset2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.points)
    TextView points;
    @BindView(R.id.pointsB)
    TextView pointsB;

    int scoreA = 0;
    int scoreB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button3)
    public void handleButton3(){
        scoreA = capturePoints() + 3;
        updateFinalPoint();
    }

    @OnClick(R.id.button3B)
    public void handleButton3B(){
        scoreB = capturePointsB() + 3;
        updateFinalPointB();
    }

    @OnClick(R.id.button2)
    public void handleButton2(){
        scoreA = capturePoints() + 2;
        updateFinalPoint();
    }

    @OnClick(R.id.button2B)
    public void handleButton2B(){
        scoreB = capturePointsB() + 2;
        updateFinalPointB();
    }

    @OnClick(R.id.button1)
    public void handleButton1(){
        scoreA = capturePoints() + 1;
        updateFinalPoint();
    }

    @OnClick(R.id.button1B)
    public void handleButton1B(){
        scoreB = capturePointsB() + 1;
        updateFinalPointB();
    }

    private int capturePoints() {
        scoreA = Integer.parseInt(points.getText().toString());
        return scoreA;
    }

    private int capturePointsB() {
        scoreB = Integer.parseInt(pointsB.getText().toString());
        return scoreB;
    }

    private void updateFinalPoint() {
        points.setText(String.valueOf(scoreA));
    }

    private void updateFinalPointB() {
        pointsB.setText(String.valueOf(scoreB));
    }

    @OnClick(R.id.buttonReset)
    public void handleReset(){
        points.setText("0");
        pointsB.setText("0");
    }
}
