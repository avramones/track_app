package com.test.traklapp.vm

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.traklapp.api.DataManager
import com.test.traklapp.model.Response
import com.test.traklapp.model.Track
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

@Suppress("UNCHECKED_CAST")
class ListViewModel(application: Application) : AndroidViewModel(application) {

    var liveData : MutableLiveData<List<Track>> = MutableLiveData()
    var compositeDisposable : CompositeDisposable = CompositeDisposable()
    var dataManager : DataManager = DataManager()


    @SuppressLint("CheckResult")
    fun fetchTracks(term: String?, entity: String?) {
        dataManager.getSearchResults(term, entity)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { response -> liveData.postValue(response?.results)};
    }

}