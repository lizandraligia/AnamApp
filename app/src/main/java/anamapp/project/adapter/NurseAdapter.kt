package anamapp.project.adapter

import anamapp.project.R
import anamapp.project.bean.Auxiliar
import anamapp.project.bean.Nurse
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.items_list_nurse.view.*

class NurseAdapter(val mContext: Context, val list:ArrayList<Nurse>):
    RecyclerView.Adapter<NurseAdapter.NurseViewHolder>() {



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NurseViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.items_list_nurse, null)
        val holder = NurseViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: NurseViewHolder, p1: Int) {
        val nurse = list.get(p1)
        p0.textViewName.setText(nurse.nurseName)
        p0.textViewCoren.setText(nurse.nurseCoren)
        p0.textViewEmail.setText(nurse.email)
        p0.imageViewNurse.setImageBitmap(Auxiliar.StringToBitMap(nurse.image))
    }


    class NurseViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {


        val imageViewNurse = itemView.imageViewNurse
        val textViewName = itemView.textViewNurseName
        val textViewCoren = itemView.textViewCoren
        val textViewEmail = itemView.textViewEmail

    }
}



