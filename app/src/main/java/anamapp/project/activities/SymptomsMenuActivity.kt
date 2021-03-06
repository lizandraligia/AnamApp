package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.Constant
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_symptoms_menu.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class SymptomsMenuActivity : AppCompatActivity() {

    private lateinit var jsonArrayResult: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms_menu)

        this.jsonArrayResult = JSONArray()

        btnFinishSymptoms.setOnClickListener {
//            val intent = Intent(applicationContext, PatientActivity::class.java)
//            startActivity(intent)

            val intent = Intent()
            intent.putExtra(Constant.JSON_RESULT, jsonArrayResult.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()


            //println(this.jsonArrayResult.toString(2))       // TODO remove this later
        }

        menu_image_view_back3.setOnClickListener {
            finish()
        }
        btnGeneralSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra(Constant.SYMPTOM, btnGeneralSymptoms.text.toString())

            try {

                // The system now read the filename from the resource string
                // because he gets the filename with respect to the actual language

                val filename = getString(R.string.general_symptoms_filename)
                val inputStream = assets.open(filename)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                intent.putExtra(Constant.JSON_SCREEN, jsonString)


            } catch (e: Exception) {
                e.printStackTrace()
                return@setOnClickListener
            }

            startActivityForResult(intent, Constant.REQUEST_CODE_SYMPTOMS)
        }

        btnSupLocomotor.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra(Constant.SYMPTOM, btnSupLocomotor.text.toString())

            // Input method of data, may be changed to database source or remote base
            // i choice local files for tests
            try {

                // The system now read the filename from the resource string
                // because he gets the filename with respect to the actual language

                val filename = getString(R.string.suplocomotor_symptoms_filename)
                val inputStream = assets.open(filename)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                intent.putExtra(Constant.JSON_SCREEN, jsonString)

            } catch (e: Exception) {
                e.printStackTrace()
                return@setOnClickListener
            }

            startActivityForResult(intent, Constant.REQUEST_CODE_SYMPTOMS)
        }
        btnInfLocomotor.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra(Constant.SYMPTOM, btnInfLocomotor.text.toString())

            // Input method of data, may be changed to database source or remote base
            // i choice local files for tests
            try {

                // The system now read the filename from the resource string
                // because he gets the filename with respect to the actual language

                val filename = getString(R.string.inflocomotor_symptoms_filename)
                val inputStream = assets.open(filename)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                intent.putExtra(Constant.JSON_SCREEN, jsonString)

            } catch (e: Exception) {
                e.printStackTrace()
                return@setOnClickListener
            }

            startActivityForResult(intent, Constant.REQUEST_CODE_SYMPTOMS)
        }

        btnRespiratorySystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra(Constant.SYMPTOM, btnRespiratorySystemSymptoms.text.toString())

            // Input method of data, may be changed to database source or remote base
            // i choice local files for tests
            try {

                // The system now read the filename from the resource string
                // because he gets the filename with respect to the actual language

                val filename = getString(R.string.respiratory_system_symptoms_filename)
                val inputStream = assets.open(filename)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                intent.putExtra(Constant.JSON_SCREEN, jsonString)

            } catch (e: Exception) {
                e.printStackTrace()
                return@setOnClickListener
            }

            startActivityForResult(intent, Constant.REQUEST_CODE_SYMPTOMS)
        }
        btnGenitourinarySystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra(Constant.SYMPTOM, btnGenitourinarySystemSymptoms.text.toString())

            // Input method of data, may be changed to database source or remote base
            // i choice local files for tests
            try {

                // The system now read the filename from the resource string
                // because he gets the filename with respect to the actual language

                val filename = getString(R.string.genitourinary_system_symptoms_filename)
                val inputStream = assets.open(filename)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                intent.putExtra(Constant.JSON_SCREEN, jsonString)

            } catch (e: Exception) {
                e.printStackTrace()
                return@setOnClickListener
            }

            startActivityForResult(intent, Constant.REQUEST_CODE_SYMPTOMS)
        }
        btnGastrointestinalSystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra(Constant.SYMPTOM, btnGastrointestinalSystemSymptoms.text.toString())

            // Input method of data, may be changed to database source or remote base
            // i choice local files for tests
            try {

                // The system now read the filename from the resource string
                // because he gets the filename with respect to the actual language

                val filename = getString(R.string.gastrointestinal_system_symptoms_filename)
                val inputStream = assets.open(filename)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                intent.putExtra(Constant.JSON_SCREEN, jsonString)

            } catch (e: Exception) {
                e.printStackTrace()
                return@setOnClickListener
            }

            startActivityForResult(intent, Constant.REQUEST_CODE_SYMPTOMS)
        }
        btnCardiovascular.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra(Constant.SYMPTOM, btnCardiovascular.text.toString())

            // Input method of data, may be changed to database source or remote base
            // i choice local files for tests
            try {

                // The system now read the filename from the resource string
                // because he gets the filename with respect to the actual language

                val filename = getString(R.string.cardiovascular_symptoms_filename)
                val inputStream = assets.open(filename)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                intent.putExtra(Constant.JSON_SCREEN, jsonString)

            } catch (e: Exception) {
                e.printStackTrace()
                return@setOnClickListener
            }

            startActivityForResult(intent, Constant.REQUEST_CODE_SYMPTOMS)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constant.REQUEST_CODE_SYMPTOMS && resultCode == Constant.RESULT_CODE_SYMPTOMS && data != null) {
            val jsonResult = JSONObject(data!!.getStringExtra(Constant.JSON_RESULT))
            val position = this.findIndex(this.jsonArrayResult, jsonResult)
            if (position != -1) // Update object
                this.jsonArrayResult.put(position, jsonResult)
            else
                this.jsonArrayResult.put(jsonResult)
        }
    }


    // This method returns the index of object jsonObject at jsonArray
    // if there is object and -1 if there is no such object
    private fun findIndex(jsonArray: JSONArray, jsonObject: JSONObject): Int {
        for(index in 0 .. (jsonArray.length()-1)) {
            var element = jsonArray.get(index) as JSONObject
            if (jsonObject.get(Constant.TITLE_PAGE_SYMPTOM).equals(element.get(Constant.TITLE_PAGE_SYMPTOM)))
                return index
        }

        return -1
    }


}