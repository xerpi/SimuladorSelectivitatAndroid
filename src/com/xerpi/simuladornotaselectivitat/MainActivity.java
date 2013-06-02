package com.xerpi.simuladornotaselectivitat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener {

	EditText etBatx, etCatala, etCastella, etAngles, etHistoFilo, etOptativa, etEspec1, etEspec2;
	TextView tvNotaComuna, tvNotaFinalPAU;
	Button buttonCalcular;
	Spinner spinnerEspec1, spinnerEspec2;
	
	final int nNotesComuna = 5;
	float notaBatx, notaCatala, notaCastella, notaAngles, notaHistoFilo, notaOptativa;
	float notaComuna, notaFinalPAU;
	float notaEspec1, notaEspec2;
	float ponderacio1, ponderacio2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        notaBatx = notaCatala = notaCastella = notaAngles = notaHistoFilo = notaOptativa = 0.0f;
        notaComuna    = 0.0f;
        notaFinalPAU  = 0.0f;
        ponderacio1   = 0.1f;
        ponderacio2   = 0.1f;
        
        tvNotaComuna     = (TextView)findViewById(R.id.tvNotaComuna);
        tvNotaFinalPAU   = (TextView)findViewById(R.id.tvNotaFinalPAU);
        buttonCalcular   = (Button)findViewById(R.id.buttonCalcular);
        spinnerEspec1    = (Spinner)findViewById(R.id.spinnerEspec1);
        spinnerEspec2    = (Spinner)findViewById(R.id.spinnerEspec2);
        
        etBatx      = (EditText)findViewById(R.id.etBatx);
        etCatala    = (EditText)findViewById(R.id.etCatala);
        etCastella  = (EditText)findViewById(R.id.etCastella);
        etAngles    = (EditText)findViewById(R.id.etAngles);
        etHistoFilo = (EditText)findViewById(R.id.etHistoFilo);
        etOptativa  = (EditText)findViewById(R.id.etOptativa);
        etEspec1    = (EditText)findViewById(R.id.etEspec1);
        etEspec2    = (EditText)findViewById(R.id.etEspec2);
        
        buttonCalcular.setOnClickListener(this);
        
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		R.array.ponderacions_array, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		R.array.ponderacions_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
		spinnerEspec1.setAdapter(adapter1);
		spinnerEspec2.setAdapter(adapter2);
		
		spinnerEspec1.setOnItemSelectedListener(this);
		spinnerEspec2.setOnItemSelectedListener(this);
		
        
    }
    
    public void llegirNotes()
    {
    	try{
    		notaBatx = Float.valueOf(etBatx.getText().toString());
    	}catch(NumberFormatException ex){
    		notaBatx = 0.0f;
    	}
    	try{
    		notaCatala = Float.valueOf(etCatala.getText().toString());
    	}catch(NumberFormatException ex){
    		notaCatala = 0.0f;
    	}
    	try{
    		notaCastella  = Float.valueOf(etCastella.getText().toString());
    	}catch(NumberFormatException ex){
    		notaCastella = 0.0f;
    	}
    	try{
    		notaAngles    = Float.valueOf(etAngles.getText().toString());
    	}catch(NumberFormatException ex){
    		notaAngles = 0.0f;
    	}
    	try{
    		notaHistoFilo = Float.valueOf(etHistoFilo.getText().toString());
    	}catch(NumberFormatException ex){
    		notaHistoFilo = 0.0f;
    	}
    	try{
    		notaOptativa  = Float.valueOf(etOptativa.getText().toString());
    	}catch(NumberFormatException ex){
    		notaOptativa = 0.0f;
    	}
    	try{
    		notaEspec1  = Float.valueOf(etEspec1.getText().toString());
    	}catch(NumberFormatException ex){
    		notaEspec1 = 0.0f;
    	}    	
    	try{
    		notaEspec2  = Float.valueOf(etEspec2.getText().toString());
    	}catch(NumberFormatException ex){
    		notaEspec2 = 0.0f;
    	}       	
    }
    
    public void calcularNotes()
    {
    	float sumaComuna = notaCatala + notaCastella + notaAngles + notaHistoFilo + notaOptativa;
    	notaComuna = 0.6f * notaBatx + 0.4f * (sumaComuna/(float)nNotesComuna);
    	notaFinalPAU = notaComuna + notaEspec1 * ponderacio1 + notaEspec2 * ponderacio2;
    }
    
    public void escriureNotes()
    {
    	tvNotaComuna.setText(getResources().getString(R.string.stringNotaComuna)+": " +notaComuna);
    	tvNotaFinalPAU.setText(getResources().getString(R.string.stringNotaFinalPAU)+": " +notaFinalPAU);
    }

	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
		case R.id.buttonCalcular:
			llegirNotes();
			calcularNotes();
			escriureNotes();
			break;
		default:
			break;
		}
	}
	
	@Override
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
		
		switch(parent.getId())
		{
		case R.id.spinnerEspec1:
	    	try{
	    		ponderacio1 = Float.valueOf(parent.getItemAtPosition(pos).toString());
	    	}catch(NumberFormatException ex){
	    		ponderacio1 = 0.1f;
	    	}    
			break;
		case R.id.spinnerEspec2:
	    	try{
	    		ponderacio2 = Float.valueOf(parent.getItemAtPosition(pos).toString());
	    	}catch(NumberFormatException ex){
	    		ponderacio2 = 0.1f;
	    	}   			
			break;
		default:
			break;	
		}
    }
	
	@Override
    public void onNothingSelected(AdapterView<?> parent)
	{
		
    }
	
}
