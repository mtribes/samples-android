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
        with(uiModel.header) {
            btn_user_auth.text = btnTitle
            txt_user_name.text = title

            btn_user_auth.setOnClickListener {
                viewModel.onBtnClick()
            }

            header_layout.visibility = if (this.isVisible) View.VISIBLE else View.GONE
        }

        listAdapter.submitList(uiModel.body)
    }
}