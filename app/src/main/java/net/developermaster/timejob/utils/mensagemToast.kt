package net.developermaster.timejob.utils

import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import net.developermaster.timejob.R
import net.developermaster.timejob.view.MainActivity

fun MainActivity.mensagemToast(mensagem: String ) {

    window.decorView.rootView?.let { view ->

        //todo exibe a mensagem
        val toast = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT)

        //todo variavel view
        val toastView = toast.view

        // todo cor de fundo
        if (toastView != null) {
            toastView.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }

        // todo estilo do texto
        val toastText = toastView?.findViewById<TextView>(android.R.id.message)
        if (toastText != null) {

            //todo color do texto
            toastText.setTextColor( (ContextCompat.getColor(this, R.color.white) ))

            //todo tamanho do texto
            toastText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        }

        //todo exibe variavel mensagem
//        toast.setView(toastView)

        //todo posicao
        toast.setGravity(Gravity.CENTER, 0, 0)

        //todo Mostra o mensagem
        toast.show()
    }
}

