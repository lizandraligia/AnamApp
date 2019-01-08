package anamapp.project.activities

import java.io.Serializable

class Symptoms(private var bodyPart: String): Serializable {


    override fun toString(): String {
        return bodyPart
    }



}