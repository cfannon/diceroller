package com.example.diceroller.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.diceroller.diceroller.Dice
import com.example.diceroller.diceroller.RollTypeState

@Database(entities = [CustomDiceEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun customDiceDao(): CustomDiceDao

    companion object {
        private var INSTANCE: AppDB? = null

        fun getInstance(applicationContext: Context) : AppDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        applicationContext,
                        AppDB::class.java, "dice-roller"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}

@Entity(tableName = "custom_dice", primaryKeys = ["id"])
data class CustomDiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val customDiceName : String,
    val dice : Dice,
    val rollType : RollTypeState,
    val quantity : Int,
    val diceModifier : Int
)

@Dao
interface CustomDiceDao {
    @Insert
    fun insert(customDiceEntity: CustomDiceEntity)

    @Delete
    fun delete(customDiceEntity: CustomDiceEntity)

    @Query("SELECT * FROM custom_dice")
    fun getAll() : List<CustomDiceEntity>
}
