package hu.bme.aut.android.webshophw.data

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): List<Cart>

    @Insert(onConflict = REPLACE)
    fun insert(cartItems: Cart): Long

    @Update
    fun update(cartItem: Cart)

    @Delete
    fun deleteItem(cartItem: Cart)

    @Query("UPDATE cart SET Price = (Price/Pieces) * (Pieces+1) ,Pieces=(Pieces+1) WHERE id = :id")
    fun addPiece(id: Long)

    @Query("UPDATE cart SET Price = (Price/Pieces) * (Pieces-1), Pieces=(Pieces-1) WHERE id = :id")
    fun removePiece(id: Long)

    @Query("DELETE  FROM cart")
    fun deleteAll()
}