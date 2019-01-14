package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.Constant
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.android.synthetic.main.fragment_check_list.*


class CheckListFrag : Fragment() {


    companion object {

        fun newInstance(): CheckListFrag {
            return CheckListFrag()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                             savedInstanceState: Bundle?): View? {

       var rootView : View = inflater.inflate(R.layout.activity_check_list, container, false)


       return rootView

       //return inflater?.inflate(R.layout.activity_check_list, container, false)
   }

}
