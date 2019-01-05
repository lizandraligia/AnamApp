package anamapp.project

import anamapp.project.bean.Constant
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_check_list.*
import kotlinx.android.synthetic.main.list_view_item_checklist_checkbox.*
import org.json.JSONObject


class CheckList : AppCompatActivity() {
    private lateinit var jsonScreen: JSONObject
    private lateinit var jsonResult: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_list)

        var txtView = findViewById<View>(R.id.textView3) as TextView
        val nome: String = intent.getStringExtra("sintoma")
        txtView.text = nome

//        this.addCheckbox("Diarréia")
//        this.addCheckbox("Febre")
        this.drawScreen(intent)

        activity_checklist_button_submit.setOnClickListener {
            this.jsonResult = JSONObject()
            val checkList = activity_checklist_linear_layout_checklist

            for(index in 0 .. (checkList.childCount-1)) {
                val itemView = checkList.getChildAt(index)

                when(this.viewType(itemView!!)) {
                    Constant.CHECKLIST_CHECKBOX -> {
                        val checkbox = itemView.findViewById<CheckBox>(R.id.checklist_item_checkbox_checkbox)
                        val label = itemView.findViewById<TextView>(R.id.checklist_item_edit_text_label)
                        this.jsonResult.put(label.text.toString(), checkbox.isChecked)
                    }
                    // TODO make to other views types as EditTexts
                }
            }

            // Return jsonResult as string
            val it = Intent()
            it.putExtra(Constant.JSON_RESULT, this.jsonResult.toString())
            val resultCode = when(intent.)  // TODO stopped here
            setResult(Constant.RESULT_CODE_GENERAL_SYMPTOMS, it)
            finish()
//            println(this.jsonResult.toString())
        }
    }


    /* This function returns the type of view in question if was CheckBox, EditText, etc */
    private fun viewType(view: View): String? {
        val checkbox = view.findViewById<CheckBox>(R.id.checklist_item_checkbox_checkbox)
        if(checkbox != null)
            return Constant.CHECKLIST_CHECKBOX

        return null
    }


    private fun drawScreen(intent: Intent) {
        var jsonString = intent.getStringExtra(Constant.JSON_SCREEN)
        this.jsonScreen = JSONObject(jsonString)

        val iterator: Iterator<String> = this.jsonScreen.keys()

        while(iterator.hasNext()) {
            var key = iterator.next()

            when(this.jsonScreen.getString(key)) {
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
