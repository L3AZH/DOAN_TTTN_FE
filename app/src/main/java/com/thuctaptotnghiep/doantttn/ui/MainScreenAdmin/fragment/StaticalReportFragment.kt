package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment;

import android.graphics.Color
import android.os.Build
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

import com.thuctaptotnghiep.doantttn.R;
import com.thuctaptotnghiep.doantttn.adapter.BillAdapter
import com.thuctaptotnghiep.doantttn.api.response.Bill
import com.thuctaptotnghiep.doantttn.databinding.FragmentStaticalReportBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import com.thuctaptotnghiep.doantttn.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StaticalReportFragment : Fragment() {

    lateinit var binding: FragmentStaticalReportBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var billAdapter: BillAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStaticalReportBinding.inflate(layoutInflater, container, false)
        viewModel = (activity as MainAdminActivity).viewModel
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intialViewModel()
        setUpAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun intialViewModel(){
        viewModel.listBillToday.observe(viewLifecycleOwner,{
            billAdapter.diff.submitList(it)
            if(it.isEmpty()){
                binding.tvEmptyReport.visibility = View.VISIBLE
                binding.rcvBillInToday.visibility = View.GONE
                binding.chartBillReportToday.visibility = View.GONE
            }
            else{
                binding.tvEmptyReport.visibility = View.GONE
                binding.rcvBillInToday.visibility = View.VISIBLE
                binding.chartBillReportToday.visibility = View.VISIBLE
                setUpPieChart(viewModel.getNumberBillConfirmAndPending(it))
            }
        })
        CoroutineScope(Dispatchers.Default).launch {
            viewModel.getListBillToday(Constant.getToken(requireContext())!!)
        }
    }

    fun setUpAdapter() {
        billAdapter = BillAdapter()
        binding.rcvBillInToday.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvBillInToday.adapter = billAdapter
        billAdapter.setOnItemBillOnClickCallBack {
            setOnClickBillItem(it)
        }
    }

    fun setOnClickBillItem(bill: Bill) {
        val goToDetailBill =
            StaticalReportFragmentDirections.actionStaticalReportFragmentToDetailBillAdminFragment(
                bill
            )
        findNavController().navigate(goToDetailBill)
    }

    fun setUpPieChart(data:Map<String,Int>){
        val numConfirm = data["Confirmed"]!!.toFloat()
        val numPending = data["Pending"]!!.toFloat()
        val listPie:MutableList<PieEntry> = mutableListOf()
        val listColor:MutableList<Int> = mutableListOf()
        listPie.add(PieEntry(numConfirm*100/(numConfirm+numPending),"Confirmed"))
        listColor.add(Color.GREEN)
        listPie.add(PieEntry(numPending*100/(numConfirm+numPending),"Pending"))
        listColor.add(Color.YELLOW)

        val pieDataSet = PieDataSet(listPie,"")
        pieDataSet.colors = listColor
        pieDataSet.sliceSpace = 4f
        pieDataSet.valueLineColor = Color.GRAY
        val pieData = PieData(pieDataSet)

        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(15f)
        pieData.setValueTextColor(Color.BLACK)

        binding.chartBillReportToday.data = PieData(pieDataSet)
        binding.chartBillReportToday.highlightValue(null)
        binding.chartBillReportToday.invalidate()
        binding.chartBillReportToday.setUsePercentValues(true)
        binding.chartBillReportToday.getDescription().setEnabled(false)
        binding.chartBillReportToday.centerText = "Bill Status"
        binding.chartBillReportToday.setCenterTextSize(16f)
        binding.chartBillReportToday.setCenterTextColor(Color.WHITE)

        binding.chartBillReportToday.setDragDecelerationFrictionCoef(0.95f)

        binding.chartBillReportToday.setDrawHoleEnabled(true)
        binding.chartBillReportToday.setHoleColor(Color.TRANSPARENT)

        binding.chartBillReportToday.setTransparentCircleColor(Color.WHITE)
        binding.chartBillReportToday.setTransparentCircleAlpha(110)

        binding.chartBillReportToday.setHoleRadius(58f)
        binding.chartBillReportToday.setTransparentCircleRadius(61f)

        binding.chartBillReportToday.setDrawCenterText(true)

        binding.chartBillReportToday.setRotationAngle(0f)
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        binding.chartBillReportToday.setRotationEnabled(true)
        binding.chartBillReportToday.setHighlightPerTapEnabled(true)

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);


        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);
        binding.chartBillReportToday.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        // chart.spin(2000, 0, 360);
        val l: Legend = binding.chartBillReportToday.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.textSize = 15f
        l.textColor = Color.WHITE
        l.formSize = 15f
        l.form = Legend.LegendForm.CIRCLE
        //l.xEntrySpace = 40f
        l.xOffset = 20f

        // entry label styling

        // entry label styling
        binding.chartBillReportToday.setEntryLabelColor(Color.WHITE)
        binding.chartBillReportToday.setEntryLabelTextSize(20f)
        binding.chartBillReportToday.setDrawEntryLabels(false)

    }


}