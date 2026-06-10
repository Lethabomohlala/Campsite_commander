package com.example.campsitecommander

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_item)

        tvCount = findViewById(R.id.tvCount)

        findViewById<android.view.View>(R.id.main)?.let { v ->
            ViewCompat.setOnApplyWindowInsetsListener(v) { _, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            val options = androidx.core.app.ActivityOptionsCompat.makeCustomAnimation(this, R.anim.pop_in, android.R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        findViewById<Button>(R.id.btnView).setOnClickListener {
            startActivity(Intent(this, ViewListActivity::class.java))
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            DataManager.clearItems(this)
            updateItemCount()
            android.widget.Toast.makeText(this, getString(R.string.msg_list_cleared), android.widget.Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnExit).setOnClickListener {
            finishAffinity()
        }
    }

    override fun onResume() {
        super.onResume()
        updateItemCount()
    }

    private fun updateItemCount() {
        val count = DataManager.getItems(this).size
        tvCount.text = getString(R.string.items_in_list, count)
    }}
