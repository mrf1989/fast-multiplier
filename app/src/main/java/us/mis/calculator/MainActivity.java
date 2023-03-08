package us.mis.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button[] buttons = new Button[10];
    Button btnBorrar;
    int res, aciertos = 0, fallos = 0, nOp = 0;
    float nota;
    int[] buttonsId = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    TextView textViewOperacion, textViewResultado, textViewAciertos, textViewFallos, textViewNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBorrar = findViewById(R.id.btnBorrar);
        textViewResultado = findViewById(R.id.textViewResultado);
        textViewOperacion = findViewById(R.id.textViewOperacion);
        textViewAciertos = findViewById(R.id.textViewAciertos);
        textViewFallos = findViewById(R.id.textViewFallos);
        textViewNota = findViewById(R.id.textViewNota);

        generaOperacion();

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
                if (charSequence.length() == resLength) {
                    int result = Integer.valueOf(charSequence.toString());
                    if (result == res) {
                        textViewAciertos.setText("Aciertos: " + String.valueOf(++aciertos));
                        generaOperacion();
                        textViewResultado.setText("");
                    } else {
                        textViewResultado.setText("");
                        textViewFallos.setText("Fallos: " + String.valueOf(++fallos));
                    }
                    ++nOp;
                    nota = ((float) aciertos / nOp) * 10;
                    textViewNota.setText("Nota:" + String.valueOf((double) Math.round(nota * 100) / 100));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void generaOperacion() {
        Random r = new Random();
        int op1 = r.nextInt(10);
        int op2 = r.nextInt(10);
        res = op1 * op2;
        textViewOperacion.setText(op1 + " X " + op2);
    }
}