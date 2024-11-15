package com.bignerdranch.android.navigation.screen.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bignerdranch.android.navigation.R
import com.bignerdranch.android.navigation.databinding.FragmentNoteBinding
import com.bignerdranch.android.navigation.models.AppNote
import com.bignerdranch.android.navigation.utilits.APP_ACTIVITY
import com.bignerdranch.android.navigation.utilits.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: NoteFragmentViewModel
    private lateinit var mCurrentNote: AppNote

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        mCurrentNote = arguments?.getSerializable("note") as AppNote
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.noteText.addTextChangedListener (
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    mCurrentNote.text = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })


        mBinding.noteName.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    mCurrentNote.name = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })

    }

    private fun initialization() {
        setHasOptionsMenu(true)
        mBinding.noteText.setText(mCurrentNote.text)
        mBinding.noteName.setText(mCurrentNote.name)
        mViewModel = ViewModelProvider(this).get(NoteFragmentViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete ->
                mViewModel.delete(mCurrentNote) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        APP_ACTIVITY.navController.navigate(R.id.action_noteFragment_to_mainFragment)
                    }
                }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        mViewModel.update(mCurrentNote) {
            lifecycleScope.launch(Dispatchers.Main) {
                showToast("update")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

}