package anamapp.project.adapter

import anamapp.project.R
import anamapp.project.bean.Auxiliar
import anamapp.project.bean.prefs
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import anamapp.project.bean.Patient
import kotlinx.android.synthetic.main.items_list_patient.view.*


class PatientAdapter(val mContext: Context, val list:ArrayList<Patient>):
        RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    val mquery = FirebaseDatabase.getInstance().getReference("patient").child(prefs.uid)

    val teste = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PatientViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.items_list_patient, null)
        val holder = PatientViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: PatientViewHolder, p1: Int) {
        val patient = list.get(p1)
        val image: Bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.edit_icon)
        val image2: Bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.trash_icon_can)

        p0.textViewName.setText(patient.name)
        p0.textViewMedical_Record.setText(patient.medical_record)
        p0.btnEdit.setImageBitmap(image)
        p0.btnTrash.setImageBitmap(image2)

        p0.btnTrash.setOnClickListener {
            val builder = AlertDialog.Builder(mContext)

            builder.setTitle(R.string.alert_dialog)
            builder.setMessage(R.string.delete_dialog)

            builder.setPositiveButton(R.string.yes) { dialog, which ->
                mquery.orderByChild("medical_record")
                        .equalTo(patient.medical_record)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    val firstChild = dataSnapshot.children.iterator().next()
                                    firstChild.ref.removeValue()

                                }
                            }

                        })
            }
            builder.setNegativeButton(R.string.no) { dialog, which ->
                //

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
    }


    class PatientViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {


        val textViewName = itemView.textViewPatientName
        val textViewMedical_Record = itemView.textViewMedicalRecord
        val btnTrash = itemView.btnTrashIcon
        val btnEdit = itemView.btnEditIcon


    }
}
