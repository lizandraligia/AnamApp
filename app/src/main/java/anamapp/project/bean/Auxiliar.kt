package anamapp.project.bean

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class Auxiliar {

    companion object {
        fun BitMapToString(bitmap: Bitmap): String {
            val ByteStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ByteStream)
            val b = ByteStream.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }

        fun StringToBitMap(encodedString: String): Bitmap? {
            try {
                val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
                return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            } catch (e: Exception) {
                e.message
                return null
            }

        }

        fun decodeFile(f: File): Bitmap? {
            try {
                // Decodifica o tamanho da imagem
                val o = BitmapFactory.Options()
                o.inJustDecodeBounds = true
                BitmapFactory.decodeStream(FileInputStream(f), null, o)

                // O novo tamanho que queremos
                val REQUIRED_SIZE = 70

                // Achar o valor correto para a escala
                var scale = 1
                while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                    scale *= 2
                }

                // Decodifica com o inSampleSize
                val o2 = BitmapFactory.Options()
                o2.inSampleSize = scale
                return BitmapFactory.decodeStream(FileInputStream(f), null, o2)
            } catch (e: FileNotFoundException) {
            }

            return null
        }
    }


}