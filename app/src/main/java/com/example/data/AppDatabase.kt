package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.SavingsGoalDao
import com.example.data.dao.TransactionDao
import com.example.data.dao.UserDao
import com.example.data.entity.SavingsGoalEntity
import com.example.data.entity.TransactionEntity
import com.example.data.entity.UserProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserProfileEntity::class, TransactionEntity::class, SavingsGoalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun savingsGoalDao(): SavingsGoalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "first_salary_database"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                // Default initial profile
                                database.userDao().insertOrUpdateProfile(
                                    UserProfileEntity(
                                        id = 1,
                                        name = "Alex Mercer",
                                        email = "alex.mercer@example.com",
                                        salary = 45000.0,
                                        fixedExpenses = 15000.0,
                                        currentSavings = 10000.0,
                                        primaryGoal = "Emergency Fund",
                                        onboarded = true
                                    )
                                )
                                // Initial starter goals
                                database.savingsGoalDao().insertGoal(
                                    SavingsGoalEntity(
                                        name = "Emergency Fund",
                                        targetAmount = 50000.0,
                                        currentAmount = 24000.0,
                                        monthlyContribution = 5000.0,
                                        estimateDate = "Sep 2026",
                                        icon = "ShieldPlus"
                                    )
                                )
                                database.savingsGoalDao().insertGoal(
                                    SavingsGoalEntity(
                                        name = "New Laptop",
                                        targetAmount = 80000.0,
                                        currentAmount = 35000.0,
                                        monthlyContribution = 3000.0,
                                        estimateDate = "Dec 2026",
                                        icon = "Laptop"
                                    )
                                )
                                // Initial sample transactions
                                val now = System.currentTimeMillis()
                                database.transactionDao().insertTransaction(
                                    TransactionEntity(
                                        type = "expense",
                                        amount = 450.0,
                                        category = "Food",
                                        description = "Team Lunch",
                                        timestamp = now - 3600000
                                    )
                                )
                                database.transactionDao().insertTransaction(
                                    TransactionEntity(
                                        type = "expense",
                                        amount = 120.0,
                                        category = "Transit",
                                        description = "Metro Recharge",
                                        timestamp = now - 7200000
                                    )
                                )
                                database.transactionDao().insertTransaction(
                                    TransactionEntity(
                                        type = "expense",
                                        amount = 1200.0,
                                        category = "Shopping",
                                        description = "Work Outfit",
                                        timestamp = now - 86400000
                                    )
                                )
                            }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
