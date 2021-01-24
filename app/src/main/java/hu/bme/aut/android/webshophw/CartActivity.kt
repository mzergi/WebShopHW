package hu.bme.aut.android.webshophw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.webshophw.adapter.CartAdapter
import hu.bme.aut.android.webshophw.adapter.ProductAdapter
import hu.bme.aut.android.webshophw.data.Cart
import hu.bme.aut.android.webshophw.data.CartDatabase
import hu.bme.aut.android.webshophw.data.ProductItem
import hu.bme.aut.android.webshophw.fragments.CheckoutDialogFragment
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.content_cart.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread

class CartActivity : AppCompatActivity(), CartAdapter.CartItemClickListener, CheckoutDialogFragment.CheckoutDialogListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var cartdatabase : CartDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setSupportActionBar(findViewById(R.id.toolbar))

        cartdatabase = Room.databaseBuilder(
            applicationContext,
            CartDatabase::class.java,
            "cart"
        ).fallbackToDestructiveMigration()
            .build()


        initRecyclerView()

        backToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        proceedToCheckout.setOnClickListener {
            CheckoutDialogFragment().show(
                    supportFragmentManager,
                    CheckoutDialogFragment.TAG
            )
        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_cart, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.deleteCart -> {
                thread {
                    cartdatabase.CartDao().deleteAll()
                    loadItemsInBackground()
                    totalPriceCounter()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun totalPriceCounter() {
            var totalTextView = this.findViewById<TextView>(R.id.totalPrice)
            var sum : Long = 0;
            val items = cartdatabase.CartDao().getAll()

            for (cartItem in items){
                sum += cartItem.Price
            }
            runOnUiThread {
                totalTextView.text = getString(R.string.total_price) + " " + sum.toString()
            }
    }

    private fun initRecyclerView() {
        recyclerView = CartRecyclerView
        adapter = CartAdapter(this, this.applicationContext)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        thread{
            totalPriceCounter()
        }
    }

    override fun onItemRemoved(item: Cart) {
        thread {
            if (item.Pieces > 1) {
                cartdatabase.CartDao().removePiece(item.id!!)
            }
            else {
                cartdatabase.CartDao().deleteItem(item)
            }

            loadItemsInBackground()
            totalPriceCounter()
        }
    }
    private fun loadItemsInBackground() {
        thread {
            val items = cartdatabase.CartDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }

        }
    }

    override fun onCheckout() {
        val contextView = findViewById<FloatingActionButton>(R.id.proceedToCheckout)
        Snackbar.make(contextView, "Order recieved!", Snackbar.LENGTH_LONG).show()
    }
    override fun onCheckoutFail() {
        val contextView = findViewById<FloatingActionButton>(R.id.proceedToCheckout)
        Snackbar.make(contextView, "Checkout failed: You must fill your credentials!", Snackbar.LENGTH_LONG).show()
    }

    override fun onItemAdd(item: Cart) {
        thread {
            if (item.Pieces > 0) {
                cartdatabase.CartDao().addPiece(item.id!!)
            } else {
                cartdatabase.CartDao().insert(item)
            }

            loadItemsInBackground()
            totalPriceCounter()
        }
    }
}