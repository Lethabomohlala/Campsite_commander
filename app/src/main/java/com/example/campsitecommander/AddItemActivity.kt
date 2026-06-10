package com.example.campsitecommander

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_add_item)

        window.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        )

        val etItemName = findViewById<EditText>(R.id.etItemName)
        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val etQuantity = findViewById<EditText>(R.id.etQuantity)
        val etComment = findViewById<EditText>(R.id.etComment)

        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.categories_array),
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val v = super.getView(position, convertView, parent)
                (v as android.widget.TextView).setTextColor(if (position == 0) android.graphics.Color.GRAY else android.graphics.Color.BLACK)
                return v
            }
            override fun getDropDownView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val v = super.getDropDownView(position, convertView, parent)
                (v as android.widget.TextView).setTextColor(if (position == 0) android.graphics.Color.GRAY else android.graphics.Color.BLACK)
                return v
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = adapter

        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val name = etItemName.text.toString().trim()
            val category = spCategory.selectedItem.toString()
            val quantityStr = etQuantity.text.toString().trim()
            val comment = etComment.text.toString().trim()

            if (name.isEmpty()) {
                etItemName.error = getString(R.string.err_enter_name)
                return@setOnClickListener
            }

            if (spCategory.selectedItemPosition == 0) {
                Toast.makeText(this, getString(R.string.err_select_category), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (quantityStr.isEmpty()) {
                etQuantity.error = getString(R.string.err_enter_quantity)
                return@setOnClickListener
            }

            val quantity = quantityStr.toIntOrNull() ?: 0
            if (quantity <= 0) {
                etQuantity.error = getString(R.string.err_invalid_quantity)
                return@setOnClickListener
            }

            val item = Item(name, category, quantity, comment)
            DataManager.saveItem(this, item)

            Toast.makeText(this, getString(R.string.msg_item_saved), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
