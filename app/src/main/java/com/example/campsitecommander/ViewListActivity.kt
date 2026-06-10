package com.example.campsitecommander

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ViewListActivity : AppCompatActivity() {

    private lateinit var tvResults: TextView
    private lateinit var ivPlaceholder: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)

        tvResults = findViewById(R.id.tvResults)
        ivPlaceholder = findViewById(R.id.ivResultsPlaceholder)

        findViewById<Button>(R.id.btnDisplayFull).setOnClickListener {
            displayItems(false)
        }

        findViewById<Button>(R.id.btnItems2Plus).setOnClickListener {
            displayItems(true)
        }

        findViewById<Button>(R.id.btnReturnHome).setOnClickListener {
            finish()
        }
    }

    private fun displayItems(filter2Plus: Boolean) {
        val items = DataManager.getItems(this)
        val filteredItems = if (filter2Plus) {
            items.filter { it.quantity >= 2 }
        } else {
            items
        }

        if (filteredItems.isEmpty()) {
            tvResults.text = getString(R.string.msg_no_items_found)
            ivPlaceholder.visibility = android.view.View.VISIBLE
        } else {
            ivPlaceholder.visibility = android.view.View.GONE
            val resultText = StringBuilder()
            filteredItems.forEach { item ->
                resultText.append("ITEM: ${item.name}\n")
                resultText.append("CATEGORY: ${item.category}\n")
                resultText.append("QUANTITY: ${item.quantity}\n")
                if (item.comment.isNotEmpty()) {
                    resultText.append("COMMENT: ${item.comment}\n")
                }
                resultText.append("----------------------------\n")
            }
            tvResults.text = resultText.toString()
            tvResults.gravity = android.view.Gravity.START
        }
    }}
