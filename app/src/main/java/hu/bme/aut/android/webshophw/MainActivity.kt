package hu.bme.aut.android.webshophw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import hu.bme.aut.android.webshophw.adapter.CartAdapter
import hu.bme.aut.android.webshophw.adapter.ProductAdapter
import hu.bme.aut.android.webshophw.data.Cart
import hu.bme.aut.android.webshophw.data.CartDatabase
import hu.bme.aut.android.webshophw.data.ProductItem
import hu.bme.aut.android.webshophw.data.ProductItemDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ProductAdapter.ProductItemClickListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var database: ProductItemDatabase
    private lateinit var cartdatabase : CartDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        database = Room.databaseBuilder(
            applicationContext,
            ProductItemDatabase::class.java,
            "webshop-products"
        ).fallbackToDestructiveMigration()
            .build()
        cartdatabase = Room.databaseBuilder(
                applicationContext,
                CartDatabase::class.java,
                "cart"
        ).fallbackToDestructiveMigration()
                .build()


        initRecyclerView()

        proceedToCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)

            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        recyclerView = MainRecyclerView
        adapter = ProductAdapter(this, this.applicationContext)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.productItemDao().getAll()

            if(items.isEmpty()) {
                fillDataBase();

            }

            val finalitems = database.productItemDao().getAll()
            runOnUiThread {
                adapter.update(finalitems)
            }
        }
    }

    override fun onItemAdd(item: ProductItem) {
        var added = false;
        thread {
            val cart = cartdatabase.CartDao().getAll()
            for (cartitem in cart) {
                if (cartitem.ProductID == item.id) {
                    cartdatabase.CartDao().addPiece(cartitem.id!!)
                    added = true;
                }
            }
            if (!added) {
                val newItem = Cart(
                    null,
                    item.id,
                    (item.manufacturer + " " + item.name),
                    1,
                    item.price,
                    item.iconname
                )
                val newId = cartdatabase.CartDao().insert(newItem)
            }
        }
    }

    private fun fillDataBase(){
        //Nagyon csúnya, felvétel előtt írt adatbázis feltöltés, hogy ne csak a saját gépemen fusson adatokkal, lehetne szebben is
        val Item1 = ProductItem(
            null,
            "iPhone X",
            "Apple",
            "iphone_x_icon",
            199990
        )
        val newId1 = database.productItemDao().insert(Item1)
        val newItem1 = Item1.copy(
            id = newId1
        )
        runOnUiThread {
            adapter.addItem(newItem1)
        }
        val newItem2 = ProductItem(
            null,
            "43PUS8505 Full HD TV",
            "Philips",
            "philips_43pus8505_icon",
            149990
        )
        val newId2 = database.productItemDao().insert(newItem2)
        val newProductItem2 = newItem2.copy(
            id = newId2
        )
        runOnUiThread {
            adapter.addItem(newProductItem2)
        }

        val newItem3 = ProductItem(
            null,
            "Cyber Shot",
            "Sony",
            "sony_cyber_shot_icon",
            46990
        )
        val newId3 = database.productItemDao().insert(newItem3)
        val newProductItem3 = newItem3.copy(
            id = newId3
        )
        runOnUiThread {
            adapter.addItem(newProductItem3)
        }

        val newItem4 = ProductItem(
            null,
            "Cafissimo Pure eszpresszo",
            "Tchibo",
            "tchibo_coffee_icon",
            36700
        )
        val newId4 = database.productItemDao().insert(newItem4)
        val newProductItem4 = newItem4.copy(
            id = newId4
        )
        runOnUiThread {
            adapter.addItem(newProductItem4)
        }

        val newItem5 = ProductItem(
            null,
            "Creative Box",
            "Lego",
            "lego_box_icon",
            16300
        )
        val newId5 = database.productItemDao().insert(newItem5)
        val newProductItem5 = newItem5.copy(
            id = newId5
        )
        runOnUiThread {
            adapter.addItem(newProductItem5)
        }

        val newItem6 = ProductItem(
            null,
            "Y5P mobiltelefon",
            "Huawei",
            "huawei_y5p_icon",
            33990
        )
        val newId6 = database.productItemDao().insert(newItem6)
        val newProductItem6 = newItem6.copy(
            id = newId6
        )
        runOnUiThread {
            adapter.addItem(newProductItem6)
        }

        val newItem7 = ProductItem(
            null,
            "Nano Cell Smart LED TV",
            "LG",
            "lg_nanocell_icon",
            167990
        )
        val newId7 = database.productItemDao().insert(newItem7)
        val newProductItem7 = newItem7.copy(
            id = newId7
        )
        runOnUiThread {
            adapter.addItem(newProductItem7)
        }

        val newItem8 = ProductItem(
            null,
            "Nari Essential Gaming Headset",
            "Razer",
            "razer_nari_icon",
            167990
        )
        val newId8 = database.productItemDao().insert(newItem8)
        val newProductItem8 = newItem8.copy(
            id = newId8
        )
        runOnUiThread {
            adapter.addItem(newProductItem8)
        }

        val newItem9 = ProductItem(
            null,
            "Espresseria kávéfőző",
            "Krups",
            "krups_espresseria_icon",
            167990
        )
        val newId9 = database.productItemDao().insert(newItem9)
        val newProductItem9 = newItem9.copy(
            id = newId9
        )
        runOnUiThread {
            adapter.addItem(newProductItem9)
        }
    }
}