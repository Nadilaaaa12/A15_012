package com.example.zoohealth.repository

import com.example.zoohealth.model.AllPetugasResponse
import com.example.zoohealth.model.Petugas
import com.example.zoohealth.model.PetugasDetailResponse
import com.example.zoohealth.service_api.PetugasService
import java.io.IOException

interface PetugasRepository{
    suspend fun insertPetugas(petugas: Petugas)

    suspend fun getPetugas(): AllPetugasResponse

    suspend fun updatePetugas(idPetugas: String, petugas: Petugas)

    suspend fun deletePetugas(idPetugas: String)

    suspend fun getPetugasById(idPetugas: String): PetugasDetailResponse

}

class NetworkPetugasRepository(
    private val petugasApiService: PetugasService
): PetugasRepository{
    override suspend fun insertPetugas(petugas: Petugas) {
        petugasApiService.insertPetugas(petugas)
    }

    override suspend fun updatePetugas(idPetugas: String, petugas: Petugas) {
        petugasApiService.updatePetugas(idPetugas, petugas)
    }

    override suspend fun deletePetugas(idPetugas: String) {
        try {
            val response = petugasApiService.deletePetugas(idPetugas)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Petugas. HTTP Status code: " +
                        "${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getPetugas(): AllPetugasResponse=
        petugasApiService.getAllPetugas()

    override suspend fun getPetugasById(idPetugas: String): PetugasDetailResponse{
        return petugasApiService.getPetugasById(idPetugas)
    }
}