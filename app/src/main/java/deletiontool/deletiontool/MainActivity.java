package deletiontool.deletiontool;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import deletiontool.deletiontool.premium.R;

public class MainActivity extends AppCompatActivity {
    public EditText textInputET, outputET;
    public MaterialButton generateBtn, copyBtn;
    public SeekBar seekBar;
    int value;
    public List<String> data;
    public MyRecyclerViewAdapter adapter;
    public TextView textprogress, addExcluded;
    public CheckBox preserveCb;
    public ImageButton tutorialsButton;
    public int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        tutorialsButton = findViewById(R.id.tutorialsButton);
        TutorialDialog tutorialDialog = new TutorialDialog(MainActivity.this);

        tutorialsButton.setOnClickListener(view -> {
            tutorialDialog.show();
        });
        textInputET = findViewById(R.id.textInputET);
        outputET = findViewById(R.id.textOutputET);
        generateBtn = findViewById(R.id.generateBtn);
        preserveCb = findViewById(R.id.preserveCb);
        textprogress = findViewById(R.id.textprogress);
        copyBtn = findViewById(R.id.copyBtn);
        seekBar = findViewById(R.id.seekbar);

        data = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(this, data);
        ExcludeDialog dialog = new ExcludeDialog(this, adapter);
        addExcluded = findViewById(R.id.addExcluded);

        SpannableString ss = new SpannableString("Excluded Word List(Tap to add)");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                dialog.show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(ContextCompat.getColor(MainActivity.this, R.color.black));
            }
        };
        ss.setSpan(clickableSpan, 0, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        addExcluded.setText(ss);
        addExcluded.setMovementMethod(LinkMovementMethod.getInstance());
        addExcluded.setHighlightColor(Color.TRANSPARENT);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
                textprogress.setText(i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        preserveCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Toast.makeText(MainActivity.this, "Line Preserved.", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

        generateBtn.setOnClickListener(v -> {
            if(clickCount<=5){
                clickCount++;
                Log.d("linecount", String.valueOf(textInputET.getLineCount()));
                String text = textInputET.getText().toString().replaceAll("\\n", "\n");
                Log.d("textLinebreak", text);

                if (!text.equals("")) {
                    if (value != 0) {
                        RandomWordDeletion randomWordDeletionTool = new RandomWordDeletion(text, value, data, textInputET.getLineCount());
                        String modifiedtext;
                        if (preserveCb.isChecked()) {
                            modifiedtext = randomWordDeletionTool.deleteLinePreserved();
                        } else {
                            modifiedtext = randomWordDeletionTool.deleteRandomWord();
                        }

                        outputET.setText(modifiedtext);

                        Log.d("bylinearrays", randomWordDeletionTool.deleteLinePreserved());
                    }
                } else Toast.makeText(MainActivity.this, "Please fill in input text.", Toast.LENGTH_SHORT).show();
            }else{
                clickCount=0;

            }


        });

        copyBtn.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("output", outputET.getText().toString().trim());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(MainActivity.this, "Copied to clipboard.", Toast.LENGTH_SHORT).show();

        });
    }
}
