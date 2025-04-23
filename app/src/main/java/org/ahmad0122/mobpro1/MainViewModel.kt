package org.ahmad0122.mobpro1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.ahmad0122.mobpro1.database.CatatanDao
import org.ahmad0122.mobpro1.model.Catatan


class MainViewModel(dao: CatatanDao) : ViewModel() {
    val data: StateFlow<List<Catatan>> = dao.getCatatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}