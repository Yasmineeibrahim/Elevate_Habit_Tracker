package com.elevate

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class completeProfile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_complete_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


                // ربط العناصر بـ XML
                val genderSpinner: Spinner = findViewById(R.id.spinnerGender)
                val birthDateEditText: EditText = findViewById(R.id.editTextBirthDate)
                val countryEditText: EditText = findViewById(R.id.editTextCountry)
                val nextButton: MaterialButton = findViewById(R.id.nextButton)

                // إعداد الـ Spinner لعرض الاختيارات (مثل "Male" و "Female")
                val genderOptions = listOf("Male", "Female", "Other")
                val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
                genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                genderSpinner.adapter = genderAdapter

                // تعيين حدث للـ Button (زر "Next")
                nextButton.setOnClickListener {
                    val gender = genderSpinner.selectedItem.toString()
                    val birthDate = birthDateEditText.text.toString()
                    val country = countryEditText.text.toString()

                    // تحقق من صحة البيانات المدخلة
                    if (gender.isEmpty() || birthDate.isEmpty() || country.isEmpty()) {
                        // عرض رسالة إذا كانت البيانات غير مكتملة
                        Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        // إذا كانت البيانات صحيحة، إظهار رسالة أو الانتقال للـ Activity التالية
                        Toast.makeText(this, "Profile completed", Toast.LENGTH_SHORT).show()

                        // هنا يمكن أن تضيف المنطق للانتقال إلى Activity أخرى
                        // مثلاً: startActivity(Intent(this, NextActivity::class.java))
                    }
                }
            }
        }
