package com.manueldidonna.architexcalc.settings.licenses

import android.content.Intent
import android.view.View
import androidx.core.net.toUri
import com.manueldidonna.architexcalc.core.base.BaseController
import com.manueldidonna.architexcalc.settings.R

internal class LicensesController : BaseController<LicensesView>() {
    override val layoutRes: Int = R.layout.controller_licenses

    override fun onCreateControllerView(layoutView: View) = LicensesView(layoutView, licenses)

    override fun onControllerViewCreated(layoutView: View, controllerView: LicensesView) {
        controllerView.subscribeUserInteractionEvents { event ->
            when (event) {
                is LicensesEvent.GoBack -> router.popController(this)
                is LicensesEvent.OpenLicenseUrl -> startActivity(
                    Intent(Intent.ACTION_VIEW).setData(event.url.toUri())
                )
            }
        }
    }

    companion object {
        private const val APACHE_V2 = "Apache License v2"

        private val licenses: List<LicenseItem> = listOf(
            LicenseItem("Android Open Source Project", "$APACHE_V2 (c) Google, Inc. and Open Handset Alliance"),
            LicenseItem("AOSP AndroidX", "$APACHE_V2, January 2004"),
            LicenseItem("Material Components for Android", "$APACHE_V2, January 2004"),
            LicenseItem("Kotlin", "$APACHE_V2 (c) JetBrains"),
            LicenseItem("Kotlin coroutines", "$APACHE_V2 (c) JetBrains"),
            LicenseItem("Conductor", "$APACHE_V2 (c) BlueLine Labs, Inc."),
            LicenseItem("Epoxy", "$APACHE_V2 (c) 2016 Airbnb"),
            LicenseItem("ThreeTenABP", "$APACHE_V2 (c) 2015 Jake Wharton"),
            LicenseItem("Butter Knife", "$APACHE_V2 (c) 2013 Jake Wharton"),
            LicenseItem("Timber", "$APACHE_V2 (c) 2013 Jake Wharton"),
            LicenseItem("RxKotlin", "$APACHE_V2 (c) 2012 Netflix"),
            LicenseItem("RxAndroid", "$APACHE_V2 (c) 2015 The RxAndroid authors"),
            LicenseItem("Koin", "$APACHE_V2 (c) 2017 Arnaud Giuliani, Laurent Baresse"),
            LicenseItem("Material View Pager Dots Indicator", "$APACHE_V2 (c) 2016 Tommy Buonomo"),
            LicenseItem("Material Dialogs", "$APACHE_V2 (c) 2018 Aidan Follestad"),
            LicenseItem("RxkPrefs", "$APACHE_V2 (c) 2016 Aidan Follestad")
        )
    }

}