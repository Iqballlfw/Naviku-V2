package com.example.naviku_versi_karisma.ui.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.naviku_versi_karisma.data.local.Code
import com.example.naviku_versi_karisma.databinding.ActivityAddCodeBinding
import com.example.naviku_versi_karisma.helper.ViewModelFactory
import com.example.naviku_versi_karisma.ui.detail_kode_pribadi.PersonalCodeDetailActivity
import com.example.naviku_versi_karisma.ui.kode_pribadi.PersonalCodeListActivity

class AddCodeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CODE = "extra_code"
    }

    private var code: Code? = null

    private lateinit var addCodeViewModel: AddCodeViewModel

    private var _activityAddCodeBinding: ActivityAddCodeBinding? = null
    private val binding get() = _activityAddCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityAddCodeBinding = ActivityAddCodeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        addCodeViewModel = obtainViewModel(this@AddCodeActivity)

        @Suppress("DEPRECATION")
        code = intent.getParcelableExtra(EXTRA_CODE)
        code = Code()

        binding?.btnSave?.setOnClickListener {
            val name = binding?.edtDesc?.text.toString().trim()
            when {
                name.isEmpty() -> {
                    binding?.edtDesc?.error = "Kolom Harus di Isi"
                }
                else -> {
                    code.let { code ->
                        code?.name = name
                    }
                    addCodeViewModel.insert(code as Code)
                    showToast("Code berhasil ditambahkan")

                    val intent = Intent(this@AddCodeActivity, PersonalCodeListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding?.btnBackAddCode?.setOnClickListener {
            val intent = Intent(this@AddCodeActivity, PersonalCodeListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding?.abAddCode?.ivBackAddCode?.setOnClickListener {
            val back = Intent(this@AddCodeActivity, PersonalCodeListActivity::class.java)
            startActivity(back)
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityAddCodeBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): AddCodeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AddCodeViewModel::class.java]
    }
}