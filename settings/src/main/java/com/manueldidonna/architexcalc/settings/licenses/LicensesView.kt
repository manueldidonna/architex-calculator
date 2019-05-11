package com.manueldidonna.architexcalc.settings.licenses

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.epoxy.EpoxyRecyclerView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.createSubject
import com.manueldidonna.architexcalc.core.extensions.iterateAndBuildModels
import com.manueldidonna.architexcalc.core.ui.FixedDividerItemDecoration
import com.manueldidonna.architexcalc.settings.R2
import com.manueldidonna.architexcalc.settings.data.epoxy.epoxyLicenseItem
import io.reactivex.Observable

internal class LicensesView(view: View, licenses: List<LicenseItem>) : ArxInteractiveView<LicensesEvent> {
    private val subject = createSubject()

    @BindString(R2.string.licenses_toolbar_title)
    lateinit var licensesTitleValue: String
    @BindView(R2.id.toolbar_navigation_icon)
    lateinit var navigationIcon: AppCompatImageView
    @BindView(R2.id.toolbar_title)
    lateinit var toolbarTitle: AppCompatTextView
    @BindView(R2.id.licenses_recycler)
    lateinit var recycler: EpoxyRecyclerView

    init {
        ButterKnife.bind(this, view)
        toolbarTitle.text = licensesTitleValue
        navigationIcon.setOnClickListener { emit(LicensesEvent.GoBack) }
        recycler.apply {
            addItemDecoration(FixedDividerItemDecoration(recycler.context, LinearLayout.VERTICAL))
            iterateAndBuildModels(licenses) { index, item ->
                epoxyLicenseItem {
                    id("license$index")
                    name(item.name)
                    type(item.type)
                    listener { _ ->
                        emit(LicensesEvent.OpenLicenseUrl(item.url))
                    }
                }
            }
        }
    }


    override fun getUserInteractionEvents(): Observable<LicensesEvent> {
        return subject
    }

    private fun emit(event: LicensesEvent) {
        subject.onNext(event)
    }
}