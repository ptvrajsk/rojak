package com.example.rojak.activities.wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.rojak.R
import com.example.rojak.database.DatabaseHelper
import java.text.SimpleDateFormat

class WalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        val dbHelper : DatabaseHelper = DatabaseHelper(this)
        findViewById<TextView>(R.id.currentWalletAmount).text = dbHelper.getCurrentWalletAmount().toString()
        val dataSource = ArrayList<Pair<String, String>>()
        val transactions = dbHelper.getAllWalletTransactions()
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val sortedTransactions = transactions.sortedWith(compareByDescending { formatter.parse(it.timestamp) })
        sortedTransactions.forEach {
            dataSource.add(Pair(it.getHistoryTitle(), it.getHistoryDescription()))
        }

        findViewById<ListView>(R.id.walletTransactions).adapter = WalletTransactionAdapter(this, dataSource)
        findViewById<Button>(R.id.walletActivity_button_topup).setOnClickListener {
            TopUpDialog().show(supportFragmentManager, "")
        }

    }
}
