package hu.bme.aut.android.webshophw.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductItem::class], version = 4)
abstract class ProductItemDatabase : RoomDatabase() {
    abstract fun productItemDao(): ProductItemDao
}