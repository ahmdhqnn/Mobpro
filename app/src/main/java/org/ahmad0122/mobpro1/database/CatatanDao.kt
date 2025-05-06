package org.ahmad0122.mobpro1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.ahmad0122.mobpro1.model.Transaksi

@Dao
interface TransaksiDao {
    @Insert
    suspend fun insert(transaksi: Transaksi)

    @Update
    suspend fun update(transaksi: Transaksi)

    @Query("SELECT * FROM transaksi WHERE isDeleted = 0 ORDER BY tanggal DESC")
    fun getTransaksi(): Flow<List<Transaksi>>

    @Query("SELECT * FROM transaksi WHERE isDeleted = 1 ORDER BY tanggal DESC")
    fun getTransaksiTerhapus(): Flow<List<Transaksi>>

    @Query("SELECT * FROM transaksi WHERE id = :id")
    suspend fun getTransaksiById(id: Long): Transaksi?

    @Query("UPDATE transaksi SET isDeleted = 1 WHERE id = :id")
    suspend fun softDeleteById(id: Long)

    @Query("UPDATE transaksi SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreById(id: Long)

    @Query("DELETE FROM transaksi WHERE id = :id")
    suspend fun deletePermanentlyById(id: Long)

    @Query("SELECT SUM(jumlah) FROM transaksi WHERE jenis = 'PENDAPATAN' AND isDeleted = 0")
    fun getTotalPendapatan(): Flow<Double>

    @Query("SELECT SUM(jumlah) FROM transaksi WHERE jenis = 'PENGELUARAN' AND isDeleted = 0")
    fun getTotalPengeluaran(): Flow<Double>
}