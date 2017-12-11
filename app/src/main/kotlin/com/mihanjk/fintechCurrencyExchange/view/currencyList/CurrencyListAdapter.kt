package com.mihanjk.fintechCurrencyExchange.view.currencyList

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.PublishRelay
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import kotlinx.android.synthetic.main.fragment_currency_item.view.*

class CurrencyListAdapter(var mValues: List<CurrencyEntity>) :
        RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    companion object {
        const val KEY_FAVORITE = "Favorite"
    }

    val starClickSubject = PublishRelay.create<CurrencyEntity>()
    val clickSubject = PublishRelay.create<CurrencyEntity>()
    val longClickSubject = PublishRelay.create<CurrencyEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_currency_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mValues[position])
    }

    fun updateValues(values: List<CurrencyEntity>) {
        mValues = values
        notifyDataSetChanged()
//        // todo do i need to reassign mValues?
//        val sorted = values.sortedWith(compareByDescending<CurrencyEntity> { it.isFavorite }
//                    .thenBy { it.lastUsed }
//                    .thenBy { it.name })
//        DiffUtil.calculateDiff(CurrencyDiffCallback(mValues, sorted)).also {
//            mValues = sorted
//            it.dispatchUpdatesTo(this)
//        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mCurrencyName: TextView = view.currencyName
        val mStarImage: ImageView = view.starImage

        init {
            RxView.clicks(mStarImage).map { mValues[layoutPosition] }.subscribe(starClickSubject)
            RxView.clicks(view).map { mValues[layoutPosition] }.subscribe(clickSubject)
            RxView.longClicks(view).map { mValues[layoutPosition] }.subscribe(longClickSubject)
        }

        fun bind(item: CurrencyEntity) {
            mCurrencyName.text = item.name
            mStarImage.setImageResource(if (item.isFavorite)
                R.drawable.ic_star_yellow_24dp else R.drawable.ic_star_border_black_24dp)
        }
    }
}

class CurrencyDiffCallback(val old: List<CurrencyEntity>, val new: List<CurrencyEntity>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old[oldItemPosition].name == new[newItemPosition].name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old[oldItemPosition] == new[newItemPosition]

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size
    // todo do i need use payloads???
}
