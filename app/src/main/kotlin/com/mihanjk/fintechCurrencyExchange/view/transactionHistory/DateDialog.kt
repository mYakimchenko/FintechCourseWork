package com.mihanjk.fintechCurrencyExchange.view.transactionHistory

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.util.*
import java.util.Calendar.*

class DateDialog : DatePickerDialog.OnDateSetListener, DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            Calendar.getInstance().let {
                DatePickerDialog(activity, this@DateDialog, it.get(YEAR), it.get(MONTH),
                        it.get(DAY_OF_MONTH))
            }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

