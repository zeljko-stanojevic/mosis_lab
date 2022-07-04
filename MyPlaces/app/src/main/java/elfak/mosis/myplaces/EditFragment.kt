package elfak.mosis.myplaces

import android.app.ActionBar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.model.LocationViewModel
import elfak.mosis.myplaces.model.MyPlacesViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {

    private val myPlacesViewModel: MyPlacesViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton : Button = requireView().findViewById<Button>(R.id.editmyplace_finished_button)
        val editName: EditText = requireView().findViewById<EditText>(R.id.editmyplace_name_edit)
        val editDescription : EditText = requireView().findViewById<EditText>(R.id.editmyplace_desc_edit)
        val editLongitude : EditText = requireView().findViewById<EditText>(R.id.editmyplace_longitude_edit)
        val lonObserver = Observer<String> { newValue ->
            editLongitude.setText(newValue.toString())
        }
        locationViewModel.longitude.observe(viewLifecycleOwner, lonObserver)
        val editLatitude: EditText = requireView().findViewById(R.id.editmyplace_latitude_edit)
        val latObserver = Observer<String> { newValue ->
            editLatitude.setText(newValue.toString())
        }
        locationViewModel.latitude.observe(viewLifecycleOwner, latObserver)
        if (myPlacesViewModel.selected != null) {
            editName.setText(myPlacesViewModel.selected?.name)
            editDescription.setText(myPlacesViewModel.selected?.description)
            addButton.setText(R.string.editmyplace_save_label)
        }
        addButton.isEnabled = false
        editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addButton.isEnabled = (editName.text.length > 0)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        addButton.setOnClickListener {
            val name: String = editName.text.toString()
            val desc: String = editDescription.text.toString()
            val longitude : String = editLongitude.text.toString()
            val latitude : String = editLatitude.text.toString()
            if (myPlacesViewModel.selected != null) {
                myPlacesViewModel.selected?.name = name
                myPlacesViewModel.selected?.description = desc
                myPlacesViewModel.selected?.longitude = longitude
                myPlacesViewModel.selected?.latitude = latitude
            } else {
                myPlacesViewModel.addPlace(MyPlace(name, desc, longitude, latitude))
            }
            myPlacesViewModel.selected = null
            locationViewModel.setLocation("", "")
            findNavController().popBackStack()
        }
        val cancelButton: Button = requireView().findViewById<Button>(R.id.editmyplace_cancel_button)
        cancelButton.setOnClickListener {
            myPlacesViewModel.selected = null
            locationViewModel.setLocation("", "")
            findNavController().popBackStack()
        }
        val setButton: Button = requireView().findViewById<Button>(R.id.editmyplace_location_button)
        setButton.setOnClickListener {
            locationViewModel.setLocation = true
            findNavController().navigate(R.id.action_EditFragment_to_MapFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myPlacesViewModel.selected = null
        locationViewModel.setLocation("", "")
    }
}