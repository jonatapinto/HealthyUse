package br.com.esucri.helthyuse;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.sql.Time;
import java.text.ParseException;

import br.com.esucri.helthyuse.controller.RotinaController;
import br.com.esucri.helthyuse.model.Rotina;
import br.com.esucri.helthyuse.utils.Parsers;

public class RotinaActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    EditText editNome, editTipo, editHoraInicio, editHoraFinal,editDataFinal;
    RadioButton radioDomingo, radioSegunda, radioTerca, radioQuarta, radioQuinta, radioSexta,
                radioSabado, radioWhatsApp, radioInstagram, radioFacebook;
    Button btn_poliform;
    Rotina editarRotina, rotina = new Rotina();
    Parsers parsers = new Parsers();
    RotinaController rotinaController;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotina);

        //Intent intent = getIntent();
        //editarRotina = (Rotina) intent.getSerializableExtra("rotina_escolhida");

        //editNome = (EditText) findViewById(R.id.editNome);
        //editTipo = (EditText) findViewById(R.id.editTipo);
        //editHoraInicio = (EditText) findViewById(R.id.editHoraInicio);
        //editHoraFinal = (EditText) findViewById(R.id.editHoraFinal);
        //editDataFinal = (EditText) findViewById(R.id.editDataFinal);
        //radioDomingo = (RadioButton) findViewById(R.id.radioDomingo);
        //radioSegunda = (RadioButton) findViewById(R.id.radioSegunda);
        //radioTerca = (RadioButton) findViewById(R.id.radioTerca);
        //radioQuarta = (RadioButton) findViewById(R.id.radioQuarta);
        //radioQuinta = (RadioButton) findViewById(R.id.radioQuinta);
        //radioSexta = (RadioButton) findViewById(R.id.radioSexta);
        //radioSabado = (RadioButton) findViewById(R.id.radioSabado);
        //radioWhatsApp = (RadioButton) findViewById(R.id.radioWhatsApp);
        //radioInstagram = (RadioButton) findViewById(R.id.radioInstagram);
        //radioFacebook = (RadioButton) findViewById(R.id.radioFacebook);
        //btn_poliform = (Button) findViewById(R.id.btn_poliform);

        //if(editarRotina !=null){
        //    btn_poliform.setText("Modificar");
        //} else{
        //    btn_poliform.setText("Gravar");
        //}

        //btn_poliform.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        try {
        //            rotina.setNome(editNome.getText().toString());
        //            rotina.setTipo(editTipo.getText().toString());
        //            rotina.setHoraInicio(parsers.parserStringToTime(editHoraInicio.getText().toString()));
        //            rotina.setHoraFinal(parsers.parserStringToTime(editHoraFinal.getText().toString()));
        //            rotina.setDataFinal(parsers.parserStringToDate(editDataFinal.getText().toString()));
        //            rotina.setDom(radioDomingo.isChecked()?1:0);
        //            rotina.setSeg(radioSegunda.isChecked()?1:0);
        //            rotina.setTer(radioTerca.isChecked()?1:0);
        //            rotina.setQua(radioQuarta.isChecked()?1:0);
        //            rotina.setQui(radioQuinta.isChecked()?1:0);
        //            rotina.setSex(radioSexta.isChecked()?1:0);
        //            rotina.setSab(radioSabado.isChecked()?1:0);
        //            rotina.setWhatsapp(radioWhatsApp.isChecked()?1:0);
        //            rotina.setFacebook(radioFacebook.isChecked()?1:0);
        //            rotina.setInstagram(radioInstagram.isChecked()?1:0);
//
        //            if(btn_poliform.getText().equals("Gravar")){
        //                 rotinaController.create(rotina);
        //            }
//
        //        } catch (ParseException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //});

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rotina, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class RotinaFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public RotinaFragment() {
        }

        public static RotinaFragment newInstance(int sectionNumber) {
            RotinaFragment fragment = new RotinaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_rotina, container, false);
            return rootView;
        }
    }

    public static class AplicativosFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public AplicativosFragment() {
        }

        public static AplicativosFragment newInstance(int sectionNumber) {
            AplicativosFragment fragment = new AplicativosFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_aplicativos, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment;

            switch (position) {
                case 1:
                    fragment = AplicativosFragment.newInstance(position);
                    break;
                default:
                    fragment = RotinaFragment.newInstance(position);
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
