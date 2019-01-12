package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.Constant
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_check_list.*
import kotlinx.android.synthetic.main.scroll_view_item_checklist_checkbox.*
import org.json.JSONObject


class CheckList : AppCompatActivity() {
    private lateinit var jsonScreen: JSONObject
    private lateinit var jsonResult: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_list)

        var txtView = activity_checklist_text_field_symptom_title
        val nome: String = intent.getStringExtra(Constant.SYMPTOM)
        txtView.text = nome

        this.drawScreen(intent)

        activity_checklist_button_submit.setOnClickListener {
            this.jsonResult = JSONObject()
            val checkList = activity_checklist_linear_layout_checklist

            this.jsonResult.put(Constant.TITLE_PAGE_SYMPTOM, activity_checklist_text_field_symptom_title.text.toString()) // Add title page to jsonResult for identification
            for(index in 0 .. (checkList.childCount-1)) {
                val itemView = checkList.getChildAt(index)

                when(this.viewType(itemView!!)) {
                    Constant.CHECKLIST_CHECKBOX -> {
                        val checkbox = itemView.findViewById<CheckBox>(R.id.checklist_item_checkbox_checkbox)
                        val label = itemView.findViewById<TextView>(R.id.checklist_item_checkbox_text_view_label)
                        this.jsonResult.put(label.text.toString(), checkbox.isChecked)
                    }
                    Constant.CHECKLIST_PLAIN_TEXT -> {
                        val editText = itemView.findViewById<EditText>(R.id.checklist_item_plain_text_edit_text_info)
                        val label = itemView.findViewById<TextView>(R.id.checklist_item_plain_text_text_view_label)
                        this.jsonResult.put(label.text.toString(), editText.text.toString())
                    }
                    Constant.CHECKLIST_RADIO_GROUP -> {
                        val radioGroup = itemView.findViewById<RadioGroup>(R.id.checklist_item_radio_group_radio_group)
                        val label = itemView.findViewById<TextView>(R.id.checklist_item_radio_group_text_view_label)

                        val jsonRadioGroupResult = JSONObject()
                        for(index in 0 .. (radioGroup.childCount - 1)) {
                            val radioButton = radioGroup.getChildAt(index) as RadioButton
                            jsonRadioGroupResult.put(radioButton.text.toString(), radioButton.isChecked)
                        }
                        this.jsonResult.put(label.text.toString(), jsonRadioGroupResult)
                    }
                    // TODO make to other views types
                }
            }

            // Return jsonResult as string
            val it = Intent()
            it.putExtra(Constant.JSON_RESULT, this.jsonResult.toString())
            setResult(Constant.RESULT_CODE_SYMPTOMS, it)
            finish()
        }
    }


    /* This function returns the type of view in question if was CheckBox, EditText, etc */
    private fun viewType(view: View): String? {
        val checkbox = view.findViewById<CheckBox>(R.id.checklist_item_checkbox_checkbox)
        val editText = view.findViewById<EditText>(R.id.checklist_item_plain_text_edit_text_info)
        val divisor = view.findViewById<View>(R.id.checklist_item_radio_group_divisor)

        if(checkbox != null)
            return Constant.CHECKLIST_CHECKBOX
        if(editText != null)
            return Constant.CHECKLIST_PLAIN_TEXT
        if(divisor != null)
            return Constant.CHECKLIST_RADIO_GROUP

        return null
    }

    /* Draw the screen dynamically */
    private fun drawScreen(intent: Intent) {
        var jsonString = intent.getStringExtra(Constant.JSON_SCREEN)
        this.jsonScreen = JSONObject(jsonString)

        val iterator: Iterator<String> = this.jsonScreen.keys()

        while(iterator.hasNext()) {
            val key = iterator.next()

            when(this.jsonScreen.getString(key)) {
                Constant.CHECKLIST_CHECKBOX -> {
                    this.addCheckbox(key)
                }
                Constant.CHECKLIST_PLAIN_TEXT -> {
                    this.addPlainText(key)
                }
                Constant.CHECKLIST_RADIO_GROUP -> {
                    val elementsOfRadioGroup = jsonScreen.getJSONObject(Constant.CHECKLIST_RADIO_GROUP + key)
                    this.addRadioGroup(key, elementsOfRadioGroup)
                }
                // ... Others
            }
        }
    }


    private fun addPlainText(label: String) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.scroll_view_item_checklist_plain_text, checklist_item_linear_layout, false)

        val viewLabel = view.findViewById<TextView>(R.id.checklist_item_plain_text_text_view_label)
        val editText = view.findViewById<EditText>(R.id.checklist_item_plain_text_edit_text_info)

        viewLabel.text = label

        activity_checklist_linear_layout_checklist.addView(view)
    }

    private fun addCheckbox(label: String) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.scroll_view_item_checklist_checkbox, checklist_item_linear_layout, false)

        val viewLabel = view.findViewById<TextView>(R.id.checklist_item_checkbox_text_view_label)
        val checkbox = view.findViewById<CheckBox>(R.id.checklist_item_checkbox_checkbox)

        viewLabel.text = label

        activity_checklist_linear_layout_checklist.addView(view)
    }

    private fun addRadioGroup(label: String, elements: JSONObject) {
        val view = LayoutInflater.from(this).inflate(R.layout.scroll_view_item_checklist_radio_button, checklist_item_linear_layout, false)

        val viewLabel = view.findViewById<TextView>(R.id.checklist_item_radio_group_text_view_label)
        val radioGroup = view.findViewById<RadioGroup>(R.id.checklist_item_radio_group_radio_group)

        viewLabel.text = label

        // Routine to add radio button's
        val iterator: Iterator<String> = elements.keys()
        while(iterator.hasNext()) {
            val key = iterator.next()

            val radioButtonToAdd = RadioButton(this)
            radioButtonToAdd.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            radioButtonToAdd.text = key
            radioButtonToAdd.setTextColor(getColorStateList(R.color.colorText))
            radioButtonToAdd.maxLines = 1
            radioButtonToAdd.ellipsize = TextUtils.TruncateAt.END
            radioButtonToAdd.layoutDirection = View.LAYOUT_DIRECTION_RTL
            radioGroup.addView(radioButtonToAdd)
        }

        activity_checklist_linear_layout_checklist.addView(view)
    }

}
