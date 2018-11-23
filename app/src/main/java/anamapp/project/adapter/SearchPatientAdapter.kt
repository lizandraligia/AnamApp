package anamapp.project.adapter

import anamapp.project.R
import anamapp.project.bean.Patient
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SearchPatientAdapter(context: Context, resource: Int, objects: ArrayList<Patient>) :
                            ArrayAdapter<Patient>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup) : View {

        var view = convertView
        val patient = getItem(position)

        if(convertView == null)
            view = LayoutInflater.from(context).inflate(R.layout.search_patient_item, parent, false)

        val textViewName : TextView = view!!.findViewById(R.id.search_patient_item_text_view_name)
        val textViewMedicalRecord : TextView = view!!.findViewById(R.id.search_patient_item_text_view_medical_record)


        textViewName.setText(patient.name)
        textViewMedicalRecord.setText(patient.medical_record)

        return view
    }
}