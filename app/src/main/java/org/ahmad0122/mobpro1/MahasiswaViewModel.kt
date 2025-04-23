package org.ahmad0122.mobpro1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.ahmad0122.mobpro1.database.MahasiswaDao
import org.ahmad0122.mobpro1.model.Mahasiswa

class MahasiswaViewModel(dao: MahasiswaDao) : ViewModel() {
    val data: StateFlow<List<Mahasiswa>> = dao.getMahasiswa().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
} 