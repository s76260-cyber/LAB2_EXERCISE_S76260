package com.example.lab2_s76260

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class activity_result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textNama = findViewById<TextView>(R.id.textNama)
        val textTarikh = findViewById<TextView>(R.id.textTarikh)

        val nama = intent.getStringExtra("nama") ?: ""
        val tarikhLahir = intent.getStringExtra("tarikhLahir") ?: ""

        val jumlahAbjad = nama.replace(" ", "").length

        val bilanganVokal = nama.filter { it.lowercaseChar() in "aiueo" }.count()

        val abjadBerulang = nama.replace(" ", "")
            .groupBy { it.lowercase() }
            .filter { it.value.size > 1 }
            .keys
        val abjadBerulangString =
            if (abjadBerulang.isEmpty()) "Tiada" else abjadBerulang.joinToString(", ")

        textNama.text = "Nama: $nama" +
                "\nJumlah abjad: $jumlahAbjad" +
                "\nBilangan vokal: $bilanganVokal" +
                "\nAbjad berulang: $abjadBerulangString"

        try {
            // Tukar String tarikh (cth: 2000-05-15) kepada objek LocalDate
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val tarikhObjek = LocalDate.parse(tarikhLahir, formatter)

            // Kira umur berdasarkan tarikh hari ini
            val tarikhHariIni = LocalDate.now()
            val umur = Period.between(tarikhObjek, tarikhHariIni).years

            // Dapatkan hari kelahiran dalam Bahasa Melayu (jika boleh) atau Inggeris
            val hariLahir = tarikhObjek.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ms", "MY"))

            // Paparkan hasil ke dalam textTarikh
            textTarikh.text = """
                Umur anda: $umur tahun
                Dilahirkan pada: $hariLahir
            """.trimIndent()

        } catch (e: Exception) {
            // Jika pengguna masukkan format tarikh yang salah
            textTarikh.text = "Format tarikh salah. Sila guna yyyy-MM-dd"
        }
    }
}