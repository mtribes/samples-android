package com.deltatre.samples.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.deltatre.samples.databinding.RowItemBannerBinding
import com.deltatre.samples.databinding.RowItemHeroBinding

class FakeRowListAdapter(
    private val itemClickCallback: ((FakeRow) -> Unit)?
) : ListAdapter<FakeRow, BaseViewHolder>(FakeRowDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (FakeRow.Type.BANNER.ordinal == viewType) {
            FakeBannerVh(
                RowItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                itemClickCallback
            )
        } else {
            FakeHeroVh(
                RowItemHeroBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                itemClickCallback
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.ordinal
    }
}


abstract class BaseViewHolder(
    binding: ViewBinding,
    private val itemClickCallback: ((FakeRow) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    open fun bind(fakeRow: FakeRow) {
        with(itemView) {
            visibility = if (fakeRow.isVisible) View.VISIBLE else View.GONE
            setOnClickListener {
                itemClickCallback?.invoke(fakeRow)
            }
        }
    }
}

class FakeHeroVh(
    private val binding: RowItemHeroBinding,
    itemClickCallback: ((FakeRow) -> Unit)?
) : BaseViewHolder(binding, itemClickCallback) {
    override fun bind(fakeRow: FakeRow) {
        super.bind(fakeRow)
        binding.apply {
            Glide.with(root.context)
                .load(fakeRow.hero?.imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgHero)
        }
    }
}

class FakeBannerVh(
    private val binding: RowItemBannerBinding,
    itemClickCallback: ((FakeRow) -> Unit)?
) : BaseViewHolder(binding, itemClickCallback) {
    override fun bind(fakeRow: FakeRow) {
        super.bind(fakeRow)
        binding.apply {
            btnBanner.text = fakeRow.banner?.text
        }
    }
}

private class FakeRowDiffCallback : DiffUtil.ItemCallback<FakeRow>() {

    override fun areItemsTheSame(oldItem: FakeRow, newItem: FakeRow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FakeRow, newItem: FakeRow): Boolean {
        return oldItem == newItem
    }
}