package hu.bme.aut.android.webshophw.data

import androidx.room.*

@Dao
interface ProductItemDao {
    @Query("SELECT * FROM productitem")
    fun getAll(): List<ProductItem>

    @Insert
    fun insert(productItems: ProductItem): Long

    @Update
    fun update(productItems: ProductItem)

    @Delete
    fun deleteItem(productItems: ProductItem)
}