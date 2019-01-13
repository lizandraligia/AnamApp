package anamapp.project.adapter

import anamapp.project.R
import anamapp.project.bean.Auxiliar
import anamapp.project.bean.Nurse
import anamapp.project.bean.prefs
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.items_list_nurse.view.*
import kotlin.coroutines.experimental.coroutineContext
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class NurseAdapter(val mContext: Context, val list:ArrayList<Nurse>):
    RecyclerView.Adapter<NurseAdapter.NurseViewHolder>() {

    val mquery = FirebaseDatabase.getInstance().getReference("nurses").child(prefs.uid)

    val teste = FirebaseAuth.getInstance()

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
        val image: Bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.edit_icon)
        val image2: Bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.trash_icon_can)

        p0.textViewName.setText(nurse.nurseName)
        p0.textViewCoren.setText(nurse.nurseCoren)
        p0.textViewEmail.setText(nurse.email)
        p0.imageViewNurse.setImageBitmap(Auxiliar.StringToBitMap(nurse.image))
        p0.btnEdit.setImageBitmap(image)
        p0.btnTrash.setImageBitmap(image2)

        p0.btnTrash.setOnClickListener {
            mquery.orderByChild("id")
                .equalTo(nurse.id)
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
    }


    class NurseViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {


        val imageViewNurse = itemView.imageViewNurse
        val textViewName = itemView.textViewNurseName
        val textViewCoren = itemView.textViewCoren
        val textViewEmail = itemView.textViewEmail
        val btnTrash = itemView.btnTrashIcon
        val btnEdit = itemView.btnEditIcon

    }
}



