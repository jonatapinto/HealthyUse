package br.com.esucri.healthyUse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import br.com.esucri.healthyUse.model.Rotina;

public class RotinaAdapter extends ArrayAdapter<Rotina> {

    public RotinaAdapter(Context context) {
        super(context, android.R.layout.simple_spinner_item);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        //Pegamos a turma...
        final Rotina item = getItem(position);
        // Adicionamos a turma a Tag da View...
        convertView.setTag(item);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }
}
