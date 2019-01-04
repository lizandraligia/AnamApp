package anamapp.project

import anamapp.project.bean.Constant
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_check_list.*
import kotlinx.android.synthetic.main.list_view_item_checklist_checkbox.*
import org.json.JSONObject


class CheckList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_list)

        var txtView = findViewById<View>(R.id.textView3) as TextView
        val nome: String = intent.getStringExtra("sintoma")
        txtView.text = nome

//        this.addCheckbox("Diarréia")
//        this.addCheckbox("Febre")
        this.drawScreen(intent)
    }


    private fun drawScreen(intent: Intent) {
        var jsonString = intent.getStringExtra(Constant.JSON_STRING)
        var jsonObject = JSONObject(jsonString)
//        var jsonObject = JSONObject()

//        jsonObject.put("Febre", Constant.CHECKLIST_CHECKBOX)
//        jsonObject.put("Diarréia", Constant.CHECKLIST_CHECKBOX)
//        jsonObject.put("Catarro", Constant.CHECKLIST_CHECKBOX)
//        jsonObject.put("Tosse", Constant.CHECKLIST_CHECKBOX)

        val iterator: Iterator<String> = jsonObject.keys()

        while(iterator.hasNext()) {
            var key = iterator.next()

            when(jsonObject.getString(key)) {
                Constant.CHECKLIST_CHECKBOX -> this.addCheckbox(key)
                // ... Others
            }

        }
    }


    private fun addCheckbox(label: String) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.list_view_item_checklist_checkbox, checklist_item_linear_layout, false)

        val viewLabel = view.findViewById<TextView>(R.id.checklist_item_edit_text_label)
        val checkbox = view.findViewById<TextView>(R.id.checklist_item_checkbox_checkbox)

        viewLabel.text = label

        activity_checklist_linear_layout_checklist.addView(view)
    }
}
