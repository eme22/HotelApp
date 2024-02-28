package com.eme22.pc1app

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eme22.pc1app.data.adapter.CustomTimePickerDialog
import com.eme22.pc1app.data.adapter.HabitacionClickListener
import com.eme22.pc1app.data.adapter.PisoRecyclerViewAdapter
import com.eme22.pc1app.data.model.Habitacion
import com.eme22.pc1app.databinding.FormularioReservarHabitacionBinding
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar


class ReservarCitaActivity : FragmentActivity(),HabitacionClickListener {
    private lateinit var binding: FormularioReservarHabitacionBinding
    private lateinit var seatAdapter: PisoRecyclerViewAdapter
    private lateinit var reservaCitaViewModel: ReservaCitaViewModel

    private val timeFormatter = DateTimeFormatter.ofPattern("H:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("d-M-yyyy")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FormularioReservarHabitacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reservaCitaViewModel =
            ViewModelProvider(this)[ReservaCitaViewModel::class.java]

        reservaCitaViewModel.setSucursal(intent.getParcelableExtra("sucursal")!!)


        binding.desdeHora.setOnClickListener {
            val c = Calendar.getInstance()
            val mHour = c[Calendar.HOUR_OF_DAY]

            val timePickerDialog =
                CustomTimePickerDialog(this@ReservarCitaActivity, object : CustomTimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        binding.desdeHora.setText("%02d:%02d".format(hourOfDay, minute))
                        println("desdeHora: %02d:%02d".format(hourOfDay, minute))
                    }
                    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        if (minute != 0) view?.minute = 0
                    }
                }, mHour, 0, false)

            timePickerDialog.show()
        }

        binding.hastaHora.setOnClickListener {
            val c = Calendar.getInstance()
            val mHour = c[Calendar.HOUR_OF_DAY]
            val mMinute = 0
            val timePickerDialog = CustomTimePickerDialog(this@ReservarCitaActivity,
                object : CustomTimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        binding.hastaHora.setText("%02d:%02d".format(hourOfDay, minute))
                        println("hastaHora: %02d:%02d".format(hourOfDay, minute))
                    }
                    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        if (minute != 0) view?.minute = 0
                    }
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }

        binding.desdeFecha.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->
                    binding.desdeFecha.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    println("desdeFecha: " + dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.show()
        }

        binding.hastaFecha.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->
                    binding.hastaFecha.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    println("hastaFecha: " + dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.show()
        }

        reservaCitaViewModel.pisos.observe(this) { pisos ->

            println("GSONPISOS: "+GsonBuilder().setPrettyPrinting().create().toJson(pisos))


            if (pisos == null || pisos.isEmpty()) {
                return@observe
            }

            binding.loadingProgressBar.visibility = View.GONE
            binding.rvPisoView.visibility = View.VISIBLE

            seatAdapter = PisoRecyclerViewAdapter(pisos) { habitacion ->
                val intent = Intent(baseContext, SeleccionarCuartoActivity::class.java)
                intent.putExtra("NUMERO_HABITACION", habitacion.numeroHabitacion)
                intent.putExtra("PRECIO", habitacion.tipoHabitacion?.precioPorHora)
                intent.putExtra("INICIO", reservaCitaViewModel.fechaInicio.value)
                println("INICIO: ${reservaCitaViewModel.fechaInicio.value}")
                intent.putExtra("FIN", reservaCitaViewModel.fechaFin.value)
                println("FIN: ${reservaCitaViewModel.fechaFin.value}")
                intent.putExtra("SUCURSAL", reservaCitaViewModel.sucursal.value)
                if (getIntent().getSerializableExtra("USUARIO") != null) {
                    val usuario = getIntent().getSerializableExtra("USUARIO")
                    intent.putExtra("USUARIO", usuario)
                }
                startActivity(intent)
            }

            binding.rvPiso.apply {
                layoutManager = LinearLayoutManager(this@ReservarCitaActivity, LinearLayoutManager.VERTICAL, false)
                adapter = seatAdapter
            }
        }

        reservaCitaViewModel.validacion.observe(this) { validacion ->
            binding.searchPiso.isEnabled = validacion
        }

        binding.searchPiso.setOnClickListener {

            binding.loadingProgressBar.visibility = View.VISIBLE

            val desdeFecha = LocalDate.parse(binding.desdeFecha.text.toString(), dateFormatter)

            val hastaFecha = LocalDate.parse(binding.hastaFecha.text.toString(), dateFormatter)

            val desdeHora = LocalTime.parse(binding.desdeHora.text.toString(), timeFormatter)

            val hastaHora = LocalTime.parse(binding.hastaHora.text.toString(), timeFormatter)

            val fechaInicio = OffsetDateTime.of(desdeFecha, desdeHora, ZoneOffset.ofHours(-5))
            val fechaFin = OffsetDateTime.of(hastaFecha, hastaHora, ZoneOffset.ofHours(-5))

            reservaCitaViewModel.setFechaInicio(fechaInicio)
            reservaCitaViewModel.setFechaFin(fechaFin)
            reservaCitaViewModel.generateSeatLists()
        }

    }



    override fun onHabitacionClick(habitacion: Habitacion) {
        val intent = Intent(this,SeleccionarCuartoActivity::class.java)
        intent.putExtra("habitacion_numero",habitacion.numeroHabitacion)
        startActivity(intent)
    }
}

class SimpleDividerItemDecoration(context: Context, dividerResId: Int) : RecyclerView.ItemDecoration() {
    private val divider: Drawable? = ContextCompat.getDrawable(context, dividerResId)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) { // Iterate through all child views
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + (divider?.intrinsicHeight ?: 0)
            divider?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}
