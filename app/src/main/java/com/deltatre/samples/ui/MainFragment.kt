package com.deltatre.samples.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deltatre.samples.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var listAdapter: FakeRowListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = FakeRowListAdapter(viewModel::onItemClick).also {
            rcv_home_page_list.adapter = it
        }
        viewModel.mainListLiveData.observe(viewLifecycleOwner, this@MainFragment::renderUi)
    }

    private fun renderUi(uiModel: UiModel) {
        if (uiModel.isSignedIn) {
            btn_user_auth.text = getString(R.string.btn_text_sign_out)
        } else {
            btn_user_auth.text = getString(R.string.btn_text_sign_in)
        }

        with(uiModel.header) {
            txt_user_name.text = title
            btn_user_auth.setOnClickListener {
                viewModel.onBtnClick()
            }
            header_layout.visibility = View.VISIBLE
        }

        listAdapter.submitList(uiModel.fakeRows)
    }
}