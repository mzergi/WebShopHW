package hu.bme.aut.android.webshophw.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "productitem")
data class ProductItem (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "Name")  val name: String,
    @ColumnInfo(name = "Manufacturer") val manufacturer: String,
    @ColumnInfo(name = "iconName") val iconname: String,
    @ColumnInfo(name = "Price") val price: Long
    )