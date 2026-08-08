package com.example.data

import com.example.data.dao.SavingsGoalDao
import com.example.data.dao.TransactionDao
import com.example.data.dao.UserDao
import com.example.data.entity.SavingsGoalEntity
import com.example.data.entity.TransactionEntity
import com.example.data.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class FinanceRepository(
    private val userDao: UserDao,
    private val transactionDao: TransactionDao,
    private val savingsGoalDao: SavingsGoalDao
) {
    val userProfile: Flow<UserProfileEntity?> = userDao.getUserProfile()
    val allTransactions: Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()
    val allGoals: Flow<List<SavingsGoalEntity>> = savingsGoalDao.getAllGoals()

    suspend fun saveUserProfile(profile: UserProfileEntity) {
        userDao.insertOrUpdateProfile(profile)
    }

    suspend fun addTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: TransactionEntity) {
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun addGoal(goal: SavingsGoalEntity) {
        savingsGoalDao.insertGoal(goal)
    }

    suspend fun deleteGoal(goal: SavingsGoalEntity) {
        savingsGoalDao.deleteGoal(goal)
    }

    suspend fun resetAllData() {
        userDao.clearUserProfile()
        transactionDao.clearTransactions()
        savingsGoalDao.clearGoals()

        // Re-insert standard fresh start defaults
        userDao.insertOrUpdateProfile(
            UserProfileEntity(
                id = 1,
                name = "Alex Mercer",
                email = "alex.mercer@example.com",
                salary = 45000.0,
                fixedExpenses = 15000.0,
                currentSavings = 10000.0,
                primaryGoal = "Emergency Fund",
                onboarded = false
            )
        )
        savingsGoalDao.insertGoal(
            SavingsGoalEntity(
                name = "Emergency Fund",
                targetAmount = 50000.0,
                currentAmount = 24000.0,
                monthlyContribution = 5000.0,
                estimateDate = "Sep 2026",
                icon = "ShieldPlus"
            )
        )
        savingsGoalDao.insertGoal(
            SavingsGoalEntity(
                name = "New Laptop",
                targetAmount = 80000.0,
                currentAmount = 35000.0,
                monthlyContribution = 3000.0,
                estimateDate = "Dec 2026",
                icon = "Laptop"
            )
        )
    }
}
