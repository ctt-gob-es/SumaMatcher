package es.suma.matmatcher;

/*
 * 2022 Suma Gestión Tributaria. Unidad Proyectos Especiales.
 *
 * This file is part of es.suma.matmarcher App
 *
 *
 * SumaMatcher App is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License
 * version 3 as published by the Free Software Foundation
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


public class SettingsActivity extends AppCompatActivity {

    private static final String K_CLAVE_MODO_COLOR = "color-mode";
    private static final String K_CLAVE_DEBUG_MODE = "debug-mode";
    private static final String K_CLAVE_OPCIONES_DESARROLLO = "habilitar-opciones-desarrollo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Intent intent = getIntent();
        String [] previewSizes = intent.getStringArrayExtra("previewSizes");
        Bundle bundle = new Bundle();
        bundle.putStringArray("previewSizes", previewSizes);
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setArguments(bundle);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, settingsFragment)
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    public static class SettingsFragment extends PreferenceFragmentCompat implements PreferenceManager.OnDisplayPreferenceDialogListener {

        private boolean esPrimeraSelecDesarrollo;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            ListPreference preference = findPreference("preview-size");

            String[] previewSizes = getArguments() != null ?
                    getArguments().getStringArray("previewSizes") : new String[]{"720x720"};
            if (preference != null ) {
                preference.setEntries(previewSizes);
                preference.setEntryValues(previewSizes);

            }

            esPrimeraSelecDesarrollo = true;


            if (getPreferenceScreen().findPreference(K_CLAVE_OPCIONES_DESARROLLO) != null) {
                if (getPreferenceScreen().findPreference(K_CLAVE_OPCIONES_DESARROLLO).getSharedPreferences().
                        getBoolean(K_CLAVE_OPCIONES_DESARROLLO,false)) {
                    cambiosHabilitadoOpcionesDesarrollo();
                }else {
                    cambiosDeshabilitadoOpcionesDesarrollo();
                }
            }

        }


        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            String key = preference.getKey();
            switch (key) {
                case K_CLAVE_OPCIONES_DESARROLLO:
                    if (preference.getSharedPreferences().
                    getBoolean(K_CLAVE_OPCIONES_DESARROLLO,false) == true) {

                        if (esPrimeraSelecDesarrollo == true) {
                            if (getContext() != null) {
                                new AlertDialog.Builder(getContext())
                                        .setTitle(getString(R.string.tit_dialogo_preferencias_opciones_desarrollo))
                                        .setMessage(getString(R.string.msg_dialogo_preferencias_opciones_desarrollo))
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Dialogo solo informativo. No realiza ninguna acción.
                                            }
                                        })
                                        .setIcon(R.drawable.ic_launcher_foreground)
                                        .show();
                            }
                            esPrimeraSelecDesarrollo = false;
                        }
                        cambiosHabilitadoOpcionesDesarrollo();
                    }else {
                        cambiosDeshabilitadoOpcionesDesarrollo();
                    }
                    return true;
                default:
                    return false;
            }
        }

        private void cambiosHabilitadoOpcionesDesarrollo() {

            if (getPreferenceScreen().findPreference(K_CLAVE_DEBUG_MODE) != null) {
                getPreferenceScreen().findPreference(K_CLAVE_DEBUG_MODE).setEnabled(true);
            }
            if (getPreferenceScreen().findPreference(K_CLAVE_MODO_COLOR) != null) {
                getPreferenceScreen().findPreference(K_CLAVE_MODO_COLOR).setEnabled(true);
            }
        }

        private void cambiosDeshabilitadoOpcionesDesarrollo() {

            if (getPreferenceScreen().findPreference(K_CLAVE_DEBUG_MODE) != null) {
                getPreferenceScreen().findPreference(K_CLAVE_DEBUG_MODE).setEnabled(false);
            }
            if (getPreferenceScreen().findPreference(K_CLAVE_MODO_COLOR) != null) {
                getPreferenceScreen().findPreference(K_CLAVE_MODO_COLOR).setEnabled(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}