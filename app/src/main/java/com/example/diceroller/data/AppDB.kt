package com.example.diceroller.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.diceroller.diceroller.Dice
import com.example.diceroller.diceroller.RollTypeState
import kotlinx.coroutines.flow.Flow

@Database(entities = [CustomDiceEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun customDiceDao(): CustomDiceDao
}

@Entity(tableName = "custom_dice")
data class CustomDiceEntity(
    val customDiceName : String = "Custom Dice # 1",
    val dice : Dice = Dice.D6,
    val rollType : RollTypeState = RollTypeState.Standard,
    val quantity : Int = 2,
    val diceModifier : Int = 5
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
