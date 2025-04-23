package org.ahmad0122.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ahmad0122.mobpro1.database.MahasiswaDao
import org.ahmad0122.mobpro1.model.Mahasiswa

class DetailViewModel(private val dao: MahasiswaDao) : ViewModel() {

    fun insert(nim: String, nama: String, jurusan: String) {
        val mahasiswa = Mahasiswa(
            nim = nim,
            nama = nama,
            jurusan = jurusan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(mahasiswa)
        }
    }
    
    suspend fun getMahasiswa(id: Long): Mahasiswa? {
        return dao.getMahasiswaById(id)
    }
    
    fun update(id: Long, nim: String, nama: String, jurusan: String) {
        val mahasiswa = Mahasiswa(
            id = id,
            nim = nim,
            nama = nama,
            jurusan = jurusan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(mahasiswa)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}