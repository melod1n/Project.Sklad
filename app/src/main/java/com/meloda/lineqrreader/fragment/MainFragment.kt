package com.meloda.lineqrreader.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.meloda.lineqrreader.R
import com.meloda.lineqrreader.activity.CollectingActivity
import com.meloda.lineqrreader.activity.InventoryActivity
import com.meloda.lineqrreader.base.BaseFragment
import com.meloda.lineqrreader.databinding.FragmentMainBinding
import com.meloda.lineqrreader.dialog.BreakDialog
import java.util.*
import kotlin.random.Random

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener {
            if (binding.switcher.nextView is CardView) {
                binding.order.text = getString(R.string.order_number, Random.nextInt(1, 10000))

                binding.ordersQueue.text =
                    getString(R.string.orders_queue_number).format(
                        Random.nextInt(2, 20),
                        getString(R.string.orders).toLowerCase(Locale.ROOT)
                    )
            }

            toggleVisibility(binding.ordersQueueCard, binding.switcher.nextView !is CardView)

            binding.switcher.showNext()
        }

        binding.collect.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    CollectingActivity::class.java
                )
            )
        }

        binding.inventory.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    InventoryActivity::class.java
                )
            )
        }

        binding.pause.setOnClickListener {
            showPauseDialog()
        }
    }

    private fun showPauseDialog() {
        BreakDialog.show(parentFragmentManager)
    }

    private fun toggleVisibility(view: View, visible: Boolean? = null) {
        val isVisible = visible ?: view.isVisible
        with(view) {

            val animator = animate()

            animator.alpha((if (isVisible) 0 else 1).toFloat())

            if (isVisible)
                animator.withEndAction { this.isVisible = false }
            else
                animator.withStartAction { this.isVisible = true }

            animator.duration = 300
            animator.start()
        }
    }
}