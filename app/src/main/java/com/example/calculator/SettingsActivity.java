package com.example.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;

public class SettingsActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getThemeId(AppTheme.DEFAULT.getCodeStyleId()));
        setContentView(R.layout.activity_settings);
        initReturnButton();
        initThemeChooser();
    }

    private void initReturnButton() {
        findViewById(R.id.return_button)
                .setOnClickListener(v -> {
                    Intent themeSettings = new Intent(SettingsActivity.this, MainActivity.class);
                    themeSettings.putExtra(APP_THEME, read_APP_THEME(AppTheme.DEFAULT.getNumber()));
                    setResult(RESULT_OK, themeSettings);
                    finish();
                });
    }

    private void initThemeChooser() {
        initRadioButtonTheme(findViewById(R.id.orange_theme), AppTheme.DEFAULT);
        initRadioButtonTheme(findViewById(R.id.blue_theme), AppTheme.BLUE);

        RadioGroup themeGroup = findViewById(R.id.theme_group);
        ((MaterialRadioButton) themeGroup
                .getChildAt(read_APP_THEME(AppTheme.DEFAULT.getNumber()))).setChecked(true);
    }

    private void initRadioButtonTheme(View element, AppTheme appTheme) {
        element.setOnClickListener(v -> {
            put_APP_THEME(appTheme);
            recreate();
        });
    }

    private void put_APP_THEME(AppTheme appTheme) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(APP_THEME, appTheme.getNumber());
        editor.apply();
    }

    private int getThemeId(int codeStyle) {
        return AppTheme.defineBy(read_APP_THEME(codeStyle)).getCodeStyleId();
    }

    private int read_APP_THEME(int defCodeStyleId) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        return sharedPreferences.getInt(APP_THEME, defCodeStyleId);
    }

}