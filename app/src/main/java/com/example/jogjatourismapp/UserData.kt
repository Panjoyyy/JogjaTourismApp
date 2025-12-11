package com.example.jogjatourismapp


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Model Data User
data class User(
    val name: String,
    val email: String,
    val password: String
)

// Object Singleton untuk Mengelola Data User (Database Sementara)
object UserManager {
    // List untuk menyimpan semua user yang sudah register
    private val registeredUsers = mutableListOf<User>()

    // Menyimpan user yang sedang login saat ini
    var currentUser: User? by mutableStateOf(null)

    // Fungsi Daftar
    fun register(user: User): Boolean {
        // Cek apakah email sudah terdaftar
        if (registeredUsers.any { it.email == user.email }) {
            return false // Gagal, email sudah ada
        }
        registeredUsers.add(user)
        return true // Berhasil
    }

    // Fungsi Login
    fun login(email: String, pass: String): Boolean {
        // Cari user yang email dan passwordnya cocok
        val foundUser = registeredUsers.find { it.email == email && it.password == pass }

        return if (foundUser != null) {
            currentUser = foundUser // Set user yang aktif
            true
        } else {
            false
        }
    }

    // Fungsi Logout
    fun logout() {
        currentUser = null
    }
}