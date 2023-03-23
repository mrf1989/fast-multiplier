package us.mis.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MarcadorActivity extends AppCompatActivity {
    TextView intentos, nota;
    Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcador);

        intentos = findViewById(R.id.textViewIntentos);
        nota = findViewById(R.id.textViewNotaFinal);
        btnRetry = findViewById(R.id.buttonRetry);

        MediaPlayer sound = MediaPlayer.create(MarcadorActivity.this, R.raw.endgame);
        sound.start();

        intentos.setText(intentos.getText().toString() + getIntent().getStringExtra("aciertos"));
        nota.setText(nota.getText().toString() + getIntent().getStringExtra("nota"));

        btnRetry.setOnClickListener(view -> {
            Intent intent = new Intent(MarcadorActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}