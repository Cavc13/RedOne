package com.kostas.redone.ui.chart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.kostas.redone.databinding.FragmentChartBinding
import com.kostas.redone.di.DollarApp
import com.kostas.redone.di.ViewModelFactory
import com.kostas.redone.domain.model.Dollar
import com.kostas.redone.ui.dollar.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class СhartFragment : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as DollarApp).component
    }

    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        viewModel.getMonthDollarPrice()
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.dollarFlow.collect {
                if(it.isNotEmpty()) setLineChart(it.reversed())
            }
        }
        _binding = FragmentChartBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun setLineChart(list: List<Dollar>) {
        val cartesian = AnyChart.line()
        val data = mutableListOf<DataEntry>()
        val set = Set.instantiate()

        list.forEach {
            data.add(ValueDataEntry(it.date, it.value.replace(",", ".").substring(0, 5).toDouble()))
        }

        with(cartesian) {
            animation(true)
            padding(10, 20, 5, 20)
            crosshair().enabled(true)
                .xLabel(true)
                .yStroke()

            tooltip().positionMode(TooltipPositionMode.POINT)
            title("Тренд доллара к рублю за последний месяц")
            yAxis(0).title("Стоимость доллара")
            xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
        }

        set.data(data)

        val series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val series1 = cartesian.line(series1Mapping)

        series1.name("Dollar").color("#008000")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13)
        cartesian.legend().padding(0, 0, 10, 0)
        binding.anyChart.setChart(cartesian)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}