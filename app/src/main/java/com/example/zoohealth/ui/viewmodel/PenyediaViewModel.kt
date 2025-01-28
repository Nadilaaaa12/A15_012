package com.example.zoohealth.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.zoohealth.ZooHealthApplications
import com.example.zoohealth.ui.viewmodel.petugas.DetailPetugasViewModel
import com.example.zoohealth.ui.viewmodel.petugas.InsertPetugasViewModel
import com.example.zoohealth.ui.viewmodel.petugas.PetugasViewModel
import com.example.zoohealth.ui.viewmodel.petugas.UpdatePetugasViewModel
import com.example.zoohealth.ui.viewmodelhewan.DetailViewModel
import com.example.zoohealth.ui.viewmodelhewan.HomeViewModel
import com.example.zoohealth.ui.viewmodelhewan.InsertViewModel
import com.example.zoohealth.ui.viewmodelhewan.UpdateViewModel
import com.example.zoohealth.ui.viewmodelkandang.DetailKandangViewModel
import com.example.zoohealth.ui.viewmodelkandang.InsertKandangViewModel
import com.example.zoohealth.ui.viewmodelkandang.KandangViewModel
import com.example.zoohealth.ui.viewmodelmonitoring.DetailMonitoringViewModel
import com.example.zoohealth.ui.viewmodelmonitoring.InsertMonitoringViewModel
import com.example.zoohealth.ui.viewmodelmonitoring.MonitoringViewModel
import com.example.zoohealth.ui.viewmodelmonitoring.UpdateMonitoringViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiZoo().containerHewan.hewanRepository) }
        initializer { InsertViewModel(aplikasiZoo().containerHewan.hewanRepository) }
        initializer { DetailViewModel(createSavedStateHandle(), aplikasiZoo().containerHewan.hewanRepository) }
        initializer { UpdateViewModel(createSavedStateHandle(), aplikasiZoo().containerHewan.hewanRepository) }


        initializer { KandangViewModel(aplikasiZoo().containerKandang.kandangRepository) }
        initializer { InsertKandangViewModel(aplikasiZoo().containerKandang.kandangRepository) }
        initializer { DetailKandangViewModel(createSavedStateHandle(), aplikasiZoo().containerKandang.kandangRepository) }
        initializer { UpdateKandangViewModel(createSavedStateHandle() ,aplikasiZoo().containerKandang.kandangRepository) }

        initializer { PetugasViewModel(aplikasiZoo().containerPetugas.petugasRepository) }
        initializer { InsertPetugasViewModel(aplikasiZoo().containerPetugas.petugasRepository) }
        initializer { DetailPetugasViewModel(createSavedStateHandle(), aplikasiZoo().containerPetugas.petugasRepository) }
        initializer { UpdatePetugasViewModel(createSavedStateHandle(), aplikasiZoo().containerPetugas.petugasRepository) }

        initializer { MonitoringViewModel(aplikasiZoo().containerMonitoring.monitoringRepository) }
        initializer { InsertMonitoringViewModel(aplikasiZoo().containerMonitoring.monitoringRepository) }
        initializer { DetailMonitoringViewModel(createSavedStateHandle(), aplikasiZoo().containerMonitoring.monitoringRepository) }
        initializer { UpdateMonitoringViewModel(createSavedStateHandle() ,aplikasiZoo().containerMonitoring.monitoringRepository) }
    }
}

fun CreationExtras.aplikasiZoo(): ZooHealthApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ZooHealthApplications)
