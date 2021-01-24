package hu.bme.aut.android.webshophw.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.webshophw.R
import hu.bme.aut.android.webshophw.data.Cart
import hu.bme.aut.android.webshophw.data.ProductItem

class CartAdapter(private val listener: CartItemClickListener, _context: Context) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val context = _context
    private val items = mutableListOf<Cart>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val cartView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_cart_list, parent, false)
        return CartViewHolder(cartView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int){
        val item = items[position]
        holder.nameTextView.text = item.ProductName
        holder.piecesTextView.text = item.Pieces.toString() + " " + context.getString(R.string.cart_pieces)
        holder.priceTextView.text = item.Price.toString() + " " + context.getString(R.string.currency)
        val imgid = context.resources.getIdentifier(item.iconname, "drawable", context.packageName)
        holder.iconImageView.setImageResource(imgid)

        holder.item = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface CartItemClickListener {
        fun onItemRemoved(item: Cart)
        fun onItemAdd(item: Cart)
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val nameTextView: TextView
        val piecesTextView: TextView
        val priceTextView: TextView
        val iconImageView: ImageView
        val removeButton: ImageButton
        val addButton : ImageButton

        var item: Cart? = null

        init {
            nameTextView = itemView.findViewById(R.id.CartItemNameTextView)
            piecesTextView = itemView.findViewById(R.id.CartItemPiecesTextView)
            priceTextView = itemView.findViewById(R.id.CartItemPriceTextView)
            removeButton = itemView.findViewById(R.id.cartItemRemoveButton)
            iconImageView = itemView.findViewById(R.id.CartIconImageView)
            addButton = itemView.findViewById(R.id.cartItemAddButton)

            removeButton.setOnClickListener {
                listener.onItemRemoved(item!!)
            }

            addButton.setOnClickListener {
                listener.onItemAdd(item!!)
            }
        }

    }

    fun update(cartItems: List<Cart>) {
        items.clear()
        items.addAll(cartItems)
        notifyDataSetChanged()
    }

}