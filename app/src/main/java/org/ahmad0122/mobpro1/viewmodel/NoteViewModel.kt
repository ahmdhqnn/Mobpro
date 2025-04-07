package org.ahmad0122.mobpro1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ahmad0122.mobpro1.model.Note
import java.util.Date

class NoteViewModel : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(content: String, category: String, isImportant: Boolean) {
        if (content.isNotBlank()) {
            viewModelScope.launch {
                val newNote = Note(
                    content = content,
                    timestamp = Date(),
                    category = category,
                    isImportant = isImportant
                )
                _notes.value = _notes.value + newNote
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            _notes.value = _notes.value - note
        }
    }
} 