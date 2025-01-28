package com.example.zoohealth

import android.app.Application
import com.example.zoohealth.di.AppContainerHewan
import com.example.zoohealth.di.AppContainerKandang
import com.example.zoohealth.di.AppContainerMonitoring
import com.example.zoohealth.di.AppContainerPetugas
import com.example.zoohealth.di.HewanContainer
import com.example.zoohealth.di.KandangContainer
import com.example.zoohealth.di.MonitoringContainer
import com.example.zoohealth.di.PetugasContainer

   class ZooHealthApplications : Application() {
       lateinit var containerHewan: AppContainerHewan
    lateinit var containerKandang: AppContainerKandang
     lateinit var containerPetugas: AppContainerPetugas
     lateinit var containerMonitoring: AppContainerMonitoring

       override fun onCreate() {
        super.onCreate()
        containerHewan = HewanContainer()
        containerKandang = KandangContainer()
        containerPetugas = PetugasContainer()
       containerMonitoring = MonitoringContainer()
    }
}
