package br.com.aldemir.data.database.room.authentication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.aldemir.data.database.model.UserDTO

@Dao
interface AuthenticationDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(userDTO: UserDTO): Long

    @Update
    suspend fun update(userDTO: UserDTO): Int

    @Delete
    suspend fun delete(userDTO: UserDTO): Int

    @Transaction
    suspend fun login(userName: String, password: String): UserDTO? {
        val user = getUser(userName, password)
        if (user != null) {
            setLoggedIn(user.id, true)
        }
        return user
    }

    @Query("SELECT * FROM user WHERE user_name = :userName AND password = :password")
    suspend fun getUser(userName: String, password: String): UserDTO?

    @Query("UPDATE user SET is_logged = :isLogged WHERE id = :userId")
    suspend fun setLoggedIn(userId: Int, isLogged: Boolean): Int

    @Query("SELECT COUNT(*) > 0 FROM user WHERE user_name = :userName AND is_logged = 1")
    suspend fun isLogged(userName: String): Boolean

    @Query("UPDATE user SET is_logged = 0 WHERE user_name = :userName")
    suspend fun logout(userName: String): Int
}