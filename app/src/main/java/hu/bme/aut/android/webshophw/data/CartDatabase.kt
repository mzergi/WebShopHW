package hu.bme.aut.android.webshophw.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 5)
abstract class CartDatabase : RoomDatabase(){
    abstract fun CartDao(): CartDao
}