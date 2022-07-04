package elfak.mosis.myplaces.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {
    private val _longitude = MutableLiveData<String>("")
    val longitude: LiveData<String>
        get() = _longitude

    private val _latitude = MutableLiveData<String>("")
    val latitude: LiveData<String>
        get() = _latitude

    var setLocation:Boolean = false
    fun setLocation(longitude: String, latitude:String){
        _longitude.value = longitude
        _latitude.value = latitude
        setLocation = false
    }
}