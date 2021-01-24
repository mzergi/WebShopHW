package hu.bme.aut.android.webshophw.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.webshophw.R
import hu.bme.aut.android.webshophw.data.ProductItem

class ProductAdapter (private val listener: ProductItemClickListener, _context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val context = _context
    private val items = mutableListOf<ProductItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val productView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product_list, parent, false)
        return ProductViewHolder(productView)
    }

    interface ProductItemClickListener {
        fun onItemAdd(item: ProductItem)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]
        holder.manufacturerTextView.text = item.manufacturer
        holder.nameTextView.text = item.name
        holder.priceTextView.text = item.price.toString() +  " " + context.getString(R.string.currency)
        val imgid = context.resources.getIdentifier(item.iconname, "drawable", context.packageName)
        holder.iconImageView.setImageResource(imgid)

        holder.item = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ProductViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
        val iconImageView: ImageView
        val manufacturerTextView: TextView
        val nameTextView: TextView
        val priceTextView: TextView
        val addButton: ImageButton

        var item: ProductItem? = null

        init {
            iconImageView = itemView.findViewById(R.id.ProductItemIconImageView)
            manufacturerTextView = itemView.findViewById(R.id.ProductItemManufacturerTextView)
            nameTextView = itemView.findViewById(R.id.ProductItemNameTextView)
            priceTextView = itemView.findViewById(R.id.ProductItemPriceTextView)
            addButton = itemView.findViewById(R.id.ProductItemAddButton)

            addButton.setOnClickListener {
                listener.onItemAdd(item!!)
            }
        }
    }

    fun addItem(item: ProductItem){
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(productItems: List<ProductItem>) {
        items.clear()
        items.addAll(productItems)
        notifyDataSetChanged()
    }

}