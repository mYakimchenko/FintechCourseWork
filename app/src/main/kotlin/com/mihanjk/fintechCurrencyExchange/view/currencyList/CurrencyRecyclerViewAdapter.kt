package com.mihanjk.fintechCurrencyExchange.view.currencyList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity
import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import kotlinx.android.synthetic.main.fragment_currency_item.view.*

class CurrencyRecyclerViewAdapter(val mValues: MutableList<CurrencyEntity>,
                                  private val mListener: OnListItemInteractionListener) :
        RecyclerView.Adapter<CurrencyRecyclerViewAdapter.ViewHolder>() {

    interface OnListItemInteractionListener {
        fun onStarImageClicked(item: CurrencyEntity)
        fun onLongClicked(item: CurrencyEntity): CurrencyEntity?
        fun onClicked(name: Currency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_currency_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mCurrencyName.text = holder.mItem.name.toString()
        holder.mStarImage.setImageResource(if (holder.mItem.isFavorite)
            R.drawable.ic_star_yellow_24dp else R.drawable.ic_star_border_black_24dp)

        RxView.clicks(holder.mStarImage).subscribe {
            holder.mItem.isFavorite = !holder.mItem.isFavorite
//            mListener.onStarImageClicked(holder.mItem)
            holder.mStarImage.setImageResource(if (holder.mItem.isFavorite)
                R.drawable.ic_star_yellow_24dp else R.drawable.ic_star_border_black_24dp)
            notifyItemChanged(position)
            mValues.removeAt(position)
            val index = mValues.indexOfLast { it.isFavorite }
                    .let { if (it == -1) 0 else it + 1 }
            mValues.add(index, holder.mItem)
            holder.mItem.position = index
            notifyItemMoved(position, holder.mItem.position)
            notifyDataSetChanged()
        }

        RxView.longClicks(holder.mView).subscribe {
            mValues.removeAt(position)
            notifyDataSetChanged()
            mListener.onLongClicked(holder.mItem)?.let { mValues.add(it.position, it) }
        }

        RxView.clicks(holder.mView).subscribe {
            mListener.onClicked(holder.mItem)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        lateinit var mItem: CurrencyEntity
        val mCurrencyName: TextView = mView.currencyName
        val mStarImage: ImageView = mView.starImage
    }
}
