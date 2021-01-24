package hu.bme.aut.android.webshophw.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity (tableName = "cart")
data class Cart (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "ProductID") val ProductID: Long?,
    @ColumnInfo(name = "ProductName") val ProductName: String,
    @ColumnInfo(name = "Pieces") var Pieces: Long,
    @ColumnInfo(name = "Price") var Price: Long,
    @ColumnInfo(name = "iconname") val iconname: String
)