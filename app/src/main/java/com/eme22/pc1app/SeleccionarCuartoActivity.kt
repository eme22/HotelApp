package com.eme22.pc1app

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.eme22.pc1app.data.model.Sucursal
import com.eme22.pc1app.databinding.FragmentReservaDetailBinding
import com.eme22.pc1app.ui.reservadetail.ReservaDetailViewModel
import java.time.OffsetDateTime

class SeleccionarCuartoActivity : AppCompatActivity() {

    private lateinit var viewModel: ReservaDetailViewModel

    private var _binding: FragmentReservaDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentReservaDetailBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[ReservaDetailViewModel::class.java]

        val numeroHabitacion = intent?.getIntExtra("NUMERO_HABITACION", 1)

        val fecha = intent?.getSerializableExtra("INICIO") as OffsetDateTime

        println("Fecha: $fecha")

        val fechaFin = intent?.getSerializableExtra("FIN") as OffsetDateTime

        println("Fecha Fin: $fechaFin")

        val sucursal = intent?.getParcelableExtra<Sucursal>("SUCURSAL")!!

        val usuario = if (intent?.getStringExtra("USUARIO") != null) {
            intent?.getParcelableExtra("USUARIO")
        } else {
            viewModel.getDefaultUser()
        }

        println("Usuario: $usuario")

        val precio = intent?.getIntExtra("PRECIO", 10)

        println("Precio: $precio")

        print(fechaFin.toEpochSecond() - fecha.toEpochSecond())

        val horas = (fechaFin.toEpochSecond() - fecha.toEpochSecond()) / 3600

        println("Horas: $horas")

        val precio2 = horas * precio!!

        binding.precio.setText("$ $precio2")

        binding.habitacion.setText("$numeroHabitacion")

        //binding.sucursal.text = "$sucursal"

        //binding.usuario.text = "${usuario.nombre}"

        binding.startTime.setText("${fecha.toLocalDate()} ${fecha.toLocalTime()}")

        binding.editTextNumberSigned.setText("$horas")

        binding.button.setOnClickListener {
            viewModel.crearReserva(
                numeroHabitacion,
                fecha,
                fechaFin,
                sucursal,
                usuario!!,
                precio2
            ) {
                generateAlert { _, _ ->
                    finish()
                }
            }

        }

        setContentView(binding.root)
    }

    fun generateAlert(onClickListener: OnClickListener){
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Reserva creada")
        alert.setMessage("La reserva ha sido creada con éxito")
        alert.setPositiveButton("Aceptar", onClickListener)
        alert.show()
    }
}