package us.mis.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    Button[] buttons = new Button[10];
    Button btnBorrar, btnReset;
    int res, aciertos = 0, fallos = 0, nOp = 0;
    float nota;
    int[] buttonsId = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    TextView textViewOperacion, textViewResultado, textViewAciertos,
            textViewFallos, textViewNota, textViewTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBorrar = findViewById(R.id.btnBorrar);
        btnReset = findViewById(R.id.btnRestart);
        textViewResultado = findViewById(R.id.textViewResultado);
        textViewOperacion = findViewById(R.id.textViewOperacion);
        textViewAciertos = findViewById(R.id.textViewAciertos);
        textViewFallos = findViewById(R.id.textViewFallos);
        textViewNota = findViewById(R.id.textViewNota);
        textViewTimer = findViewById(R.id.textViewTimer);

        btnBorrar.setOnClickListener(view -> {
            textViewResultado.setText("");
        });

        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = findViewById(buttonsId[i]);
            buttons[i].setOnClickListener(view -> {
                textViewResultado.setText(textViewResultado.getText().toString() +
                        ((Button) view).getText().toString());
            });
        }

        textViewResultado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int resLength = String.valueOf(res).length();
                if ((charSequence.length() == resLength) && !textViewOperacion.getText().equals("Multiplication")) {
                    int result = Integer.valueOf(charSequence.toString());
                    if (result == res) {
                        MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.acierto);
                        sound.start();
                        textViewAciertos.setText("Hits: " + String.valueOf(++aciertos));
                        generaOperacion();
                        textViewResultado.setText("");
                    } else {
                        MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.fallo);
                        sound.start();
                        textViewResultado.setText("");
                        textViewFallos.setText("Failures: " + String.valueOf(++fallos));
                    }
                    ++nOp;
                    nota = ((float) aciertos / nOp) * 10;
                    textViewNota.setText("Score:" + String.valueOf((double) Math.round(nota * 100) / 100));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnReset.setOnClickListener(view -> {
            generaOperacion();
            CountDownTimer temporizador = activarContador(30, textViewTimer).start();
            btnReset.setVisibility(View.INVISIBLE);
        });
    }
    private void generaOperacion() {
        Random r = new Random();
        int op1 = r.nextInt(10);
        int op2 = r.nextInt(10);
        res = op1 * op2;
        textViewOperacion.setText(op1 + " X " + op2);
    }

    @NonNull
    private String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private CountDownTimer activarContador(int timer, TextView visor) {
        AtomicInteger time = new AtomicInteger(timer);

        return new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                visor.setText("0:"+checkDigit(time.get()));
                time.getAndDecrement();
            }

            public void onFinish() {
                visor.setText("try again");
                Intent intent = new Intent(MainActivity.this, MarcadorActivity.class);
                intent.putExtra("aciertos", String.valueOf(aciertos));
                intent.putExtra("nota", String.valueOf(nota));
                startActivity(intent);
                finish();
            }
        };
    }
}