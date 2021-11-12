package com.kevinchrist.memer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.billkainkoom.ogya.quickdialog.QuickDialog
import com.billkainkoom.ogya.quickdialog.QuickDialogType
import com.billkainkoom.ogya.quicklist.LayoutManager
import com.billkainkoom.ogya.quicklist.ListableHelper
import com.bumptech.glide.Glide
import com.kevinchrist.memer.MemesViewModel
import com.kevinchrist.memer.R
import com.kevinchrist.memer.databinding.FragmentMemesBinding
import com.kevinchrist.memer.databinding.LayoutMemeBinding
import com.kevinchrist.memer.model.Meme
import com.kevinchrist.memer.utils.ListableTypes
import com.kevinchrist.memer.utils.State

class MemesFragment : Fragment(R.layout.fragment_memes) {

    private var _binding: FragmentMemesBinding? = null
    private var navController: NavController? = null

    private val memesViewModel by viewModels<MemesViewModel>()
    private val binding: FragmentMemesBinding get() = _binding!!
    private val memeList = ArrayList<Meme>()

    private lateinit var progressDialog: QuickDialog
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = QuickDialog(
            requireContext(),
            QuickDialogType.Progress,
            "Loading...",
            "Fetching Memes"
        )
        navController = Navigation.findNavController(view)

        recyclerView = binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()

        memesViewModel.memeResponse.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Loading -> {
                    progressDialog.show()
                }
                is State.Success -> {
                    progressDialog.dismiss()
                    memeList.addAll(state.result.results.memes)
                    initListable(memeList)
                }
                is State.Error -> {
                    progressDialog.dismiss()
                    state.msg?.let {
                        QuickDialog(
                            context = requireContext(),
                            style = QuickDialogType.Alert,
                            title = "Error",
                            message = it
                        )
                            .overrideButtonNames(negative = "Ok")
                            .show()
                    }
                }
            }
        })
    }

    private fun initListable(list: MutableList<Meme>) {
        ListableHelper.loadList(
            context = requireContext(),
            recyclerView = recyclerView,
            listableType = ListableTypes.meme,
            listables = list,
            listableBindingListener = { listable, listableBinding, _ ->
                if (listableBinding is LayoutMemeBinding) {
                    println("Binding: ${listable.name}")

                    listableBinding.memeName.text = listable.name
//                    Glide.with(requireContext()).load(listable.url)
//                        .into(listableBinding.memeIv)
                }
            },
            listableClickedListener = { _, _, position ->
                val meme = list[position]
                val action = MemesFragmentDirections.actionMemesFragmentToMemeFragment(meme)
                navController?.navigate(action)
            },
            layoutManagerType = LayoutManager.Vertical
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}