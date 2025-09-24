package com.example.diceroller.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.diceroller.diceroller.Dice
import com.example.diceroller.diceroller.RollTypeState
import kotlinx.coroutines.flow.Flow

@Database(entities = [CustomDiceEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun customDiceDao(): CustomDiceDao
}

@Entity(tableName = "custom_dice", primaryKeys = ["id"])
data class CustomDiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val customDiceName : String,
    val dice : Dice,
    val rollType : RollTypeState,
    val quantity : Int,
    val diceModifier : Int
)

@Dao
interface CustomDiceDao {
    @Insert
    suspend fun insert(customDiceEntity: CustomDiceEntity)

    @Delete
    suspend fun delete(customDiceEntity: CustomDiceEntity)

    @Query("SELECT * FROM custom_dice")
    suspend fun getAll() : Flow<List<CustomDiceEntity>>
}
